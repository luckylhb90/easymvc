package com.luckylhb.easymvc.utils;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 属性文件操作工具类
 * Created by dearl on 2017/2/25.
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载配置文件
     *
     * @param propsPath
     * @return
     */
    public static Properties loadProps(String propsPath) {
        Properties props = new Properties();
        InputStream is = null;
        if (StringUtils.isEmpty(propsPath)) {
            throw new IllegalArgumentException("path can not null...");
        }
        String suffix = ".properties";
        if (propsPath.lastIndexOf(suffix) == -1) {
            propsPath += suffix;
        }
        try {
            is = ClassUtil.getClassLoader().getResourceAsStream(propsPath);
            if (is != null) {
                props.load(is);
            }
        } catch (Exception e) {
            logger.error("加载属性文件出错！", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("释放资源异常！", e);
            }
        }
        return props;
    }

    /**
     * 加载配置文件，并转为map
     *
     * @param propsPath
     * @return
     */
    public static Map<String, Object> loadPropsToMap(String propsPath) {
        Map<String, Object> propsMap = Maps.newHashMap();
        Properties props = loadProps(propsPath);
        for (String key : props.stringPropertyNames()) {
            propsMap.put(key, props.get(key));
        }
        return propsMap;
    }

    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    public static int getNumber(Properties props, String key) {
        return getNumber(props, key, 0);
    }

    public static int getNumber(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = NumberUtils.toInt(props.getProperty(key), defaultValue);
        }
        return value;
    }


    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = BooleanUtils.toBoolean(props.getProperty(key));
        }
        return value;
    }

    public static Map<String, Object> getMap(Properties props, String prefix) {
        Map<String, Object> kvMap = Maps.newLinkedHashMap();
        Set<String> keySet = props.stringPropertyNames();
        if (!CollectionUtils.isEmpty(keySet)) {
            for (String key : keySet) {
                if (key.startsWith(prefix)) {
                    String value = props.getProperty(key);
                    kvMap.put(key, value);
                }
            }
        }
        return kvMap;
    }

    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = NumberUtils.toInt(props.getProperty(key), defaultValue);
        }
        return value;
    }
}
