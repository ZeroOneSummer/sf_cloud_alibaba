package com.formssi.mall.log.framework.annotation;

import com.formssi.mall.log.framework.enums.BusinessType;
import com.formssi.mall.log.framework.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义日志注解
 * @author echo
 * @date 2022年04月11日 15:54
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

}
