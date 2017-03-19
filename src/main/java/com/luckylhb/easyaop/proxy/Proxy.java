package com.luckylhb.easyaop.proxy;

/**
 * 代理接口
 * Created by lucky on 2017/3/16.
 */
public interface Proxy {

    /**
     * 执行链式代理
     * @param proxyChain
     * @return
     * @throws Exception
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
