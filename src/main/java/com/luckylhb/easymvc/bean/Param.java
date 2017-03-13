package com.luckylhb.easymvc.bean;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

/**
 * 请求参数对象
 * <p>
 * Created by lucky on 2017/3/12.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名称获取long型参数值
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return NumberUtils.toLong(String.valueOf(paramMap.get(name)));
    }

    /**
     * 获取所有字段信息
     *
     * @return
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public boolean isEmpty() {
        return MapUtils.isEmpty(this.paramMap);
    }
}
