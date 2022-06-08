package com.formssi.mall.pay.infrastructure.annotation;

import com.formssi.mall.pay.infrastructure.enumeration.PayTypeEnum;

import java.lang.annotation.*;

/**
 * 支付类型
 *
 * @author kk
 * @date 2022/05/09 16:18:16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PayType {
    PayTypeEnum value();
}