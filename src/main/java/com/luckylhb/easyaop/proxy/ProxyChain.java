package com.luckylhb.easyaop.proxy;

import com.google.common.collect.Lists;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理链
 * Created by lucky on 2017/3/16.
 */
public class ProxyChain {

    //目标类
    private final Class<?> targetClass;

    //目标对象
    private final Object targetObject;

    //目标方法
    private final Method targetMethod;

    //方法代理
    private final MethodProxy methodProxy;

    //方法参数
    private final Object[] methodParams;

    //代理列表
    private List<Proxy> proxyList = Lists.newArrayList();

    //代理索引
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }
}
