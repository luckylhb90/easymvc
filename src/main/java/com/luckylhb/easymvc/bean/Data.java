package com.luckylhb.easymvc.bean;

/**
 * 返回数据对象
 * Created by lucky on 2017/3/12.
 */
public class Data {

    /**
     * 模型对象
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
