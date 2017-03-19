package com.luckylhb.easyaop.annotations;

import java.lang.annotation.*;

/**
 * Created by lucky on 2017/3/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     * @return
     */
    Class<? extends Annotation> value();
}
