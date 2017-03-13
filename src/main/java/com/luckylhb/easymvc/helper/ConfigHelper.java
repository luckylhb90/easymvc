package com.luckylhb.easymvc.helper;

import com.luckylhb.easymvc.constants.ConfigConst;
import com.luckylhb.easymvc.utils.PropsUtil;

import java.util.Properties;

/**
 * 属性文件辅助类
 * Created by lucky on 2017/3/10.
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConst.CONFIG_PATH);

    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConst.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConst.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConst.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConst.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConst.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConst.APP_JSP_PATH, ConfigConst.DEFAULT_APP_JSP_PATH);
    }

    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConst.APP_ASSET_PATH, ConfigConst.DEFAUT_APP_ASSET_PATH);
    }

    /**
     * 获取应用文件上传限制
     *
     * @return
     */
    public static int getAppUploadLimit() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConst.APP_UPLOAD_LIMIT, 10);
    }

}
