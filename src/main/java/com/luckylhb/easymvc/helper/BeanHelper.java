package com.luckylhb.easymvc.helper;

import com.google.common.collect.Maps;
import com.luckylhb.easymvc.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Bean 辅助类
 * Created by lucky on 2017/3/11.
 */
public final class BeanHelper {

    private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);

    /**
     * 定义Bean映射(用于存放Bean类与Bean实例映射关系)
     */
    private static final Map<Class<?>, Object> BEAN_MAP = Maps.newHashMap();

    static {
        Set<Class<?>> beanCLassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanCLassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取Bean映射
     *
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }


}
