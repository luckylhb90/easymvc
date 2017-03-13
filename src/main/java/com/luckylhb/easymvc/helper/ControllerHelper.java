package com.luckylhb.easymvc.helper;

import com.google.common.collect.Maps;
import com.luckylhb.easymvc.annotations.Action;
import com.luckylhb.easymvc.bean.Handler;
import com.luckylhb.easymvc.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 控制器辅助类
 * Created by lucky on 2017/3/12.
 */
public final class ControllerHelper {

    /**
     * 处理存放请求与处理器的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = Maps.newHashMap();

    static {
        // 获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            // 遍历controller类
            for (Class<?> controllerClass : controllerClassSet) {
                // 获取Controller类中定义的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    // 遍历Controller中所有的方法
                    for (Method method : methods) {
                        // 判断当前方法是否带有Action注解
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // 验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    // 获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Map<Request, Handler> getActionMap() {
        return ACTION_MAP;
    }

    /**
     * 获取Handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return getHandler(request);
    }

    /**
     * 获取Handler
     *
     * @param request
     * @return
     */
    public static Handler getHandler(Request request) {
        return getActionMap().get(request);
    }
}
