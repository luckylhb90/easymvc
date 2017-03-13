package com.luckylhb.easymvc;

import com.luckylhb.easymvc.helper.BeanHelper;
import com.luckylhb.easymvc.helper.ClassHelper;
import com.luckylhb.easymvc.helper.ControllerHelper;
import com.luckylhb.easymvc.helper.IocHelper;
import com.luckylhb.easymvc.utils.ClassUtil;

/**
 * 加载相应helper类
 * Created by lucky on 2017/3/11.
 */
public class HelperLoader {

    public static void init() {
        Class<?>[] classes = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class cls : classes) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
