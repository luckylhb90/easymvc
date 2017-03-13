package com.luckylhb.easymvc.helper;

import com.luckylhb.easymvc.annotations.Inject;
import com.luckylhb.easymvc.utils.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入辅助类
 * Created by lucky on 2017/3/11.
 */
public final class IocHelper {

    private static final Logger logger = LoggerFactory.getLogger(IocHelper.class);

    static {
        //获取BEAN_MAP所有的Bean类与Bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            //遍历 BeanMap
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 从beanMap中获取bean类，与bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取Bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    //遍历成员变量 bean fields
                    for (Field beanField : beanFields) {
                        //判断当前 Bean Field 是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在BeanMap中获取beanField对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化beanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
