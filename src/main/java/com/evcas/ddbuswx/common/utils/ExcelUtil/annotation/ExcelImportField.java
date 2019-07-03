package com.evcas.ddbuswx.common.utils.ExcelUtil.annotation;

import com.evcas.ddbuswx.common.utils.ExcelUtil.constant.AutoCreateTypeEnum;

import java.lang.annotation.*;

/**
 * Created by noxn on 2018/9/19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Documented
public @interface ExcelImportField {

    boolean autoCreate() default false;

    AutoCreateTypeEnum autoCreateType() default AutoCreateTypeEnum.NULL;

    int sort() default -1;

    boolean validate() default false;

    String regex() default "";

    String cellValueType() default "";

    //TODO 不是很急
    //判断是否是必传字段
    //判断是否需要类型转换
}
