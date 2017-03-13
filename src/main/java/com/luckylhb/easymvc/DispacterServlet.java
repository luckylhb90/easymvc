package com.luckylhb.easymvc;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.luckylhb.easymvc.bean.Data;
import com.luckylhb.easymvc.bean.Handler;
import com.luckylhb.easymvc.bean.Param;
import com.luckylhb.easymvc.bean.View;
import com.luckylhb.easymvc.constants.SystemConst;
import com.luckylhb.easymvc.helper.BeanHelper;
import com.luckylhb.easymvc.helper.ConfigHelper;
import com.luckylhb.easymvc.helper.ControllerHelper;
import com.luckylhb.easymvc.utils.CodecUtil;
import com.luckylhb.easymvc.utils.ReflectionUtil;
import com.luckylhb.easymvc.utils.StreamUtil;
import com.luckylhb.easymvc.utils.WebUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

/**
 * 请求转发器
 * Created by lucky on 2017/3/12.
 */
//@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispacterServlet extends HttpServlet {
    private static final String jsp = "jsp";

    private static final String DEFAULT = "default";

    private static final String STAR = "*";

    private static final String AND = "&";

    private static final String EQUAL = "=";

    private static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关helper类
        HelperLoader.init();
        // 获取ServletContext对象(用于注册Servlet)
        ServletContext servletContext = config.getServletContext();
        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration(jsp);
        jspServlet.addMapping(ConfigHelper.getAppJspPath().concat(STAR));
        // 注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration(DEFAULT);
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath().concat(STAR));
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求方法与请求路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = WebUtil.getRequestPath(request);
        // 获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            // 获取 Controller 类及其Bean 实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String, Object> paramMap = Maps.newHashMap();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = StringUtils.split(body, AND);
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtils.split(param, EQUAL);
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);
            // 调用Action 方法
            Method actionMethod = handler.getActionMethod();
            Object result = null;
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }
            // 处理 Action 方法返回值
            if (result instanceof View) {
                // 返回jsp页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (StringUtils.startsWith(path, "/")) {
                        response.sendRedirect(request.getContextPath().concat(path));
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath().concat(path)).forward(request, response);
                    }
                }
            } else if (result instanceof Data) {
                // 返回 json 数据
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    response.setContentType(CONTENT_TYPE_JSON);
                    response.setCharacterEncoding(SystemConst.UTF8);
                    PrintWriter writer = response.getWriter();
                    String json = JSONObject.toJSONString(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
