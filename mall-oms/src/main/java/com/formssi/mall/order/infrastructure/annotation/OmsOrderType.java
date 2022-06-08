package com.formssi.mall.order.infrastructure.annotation;


import com.formssi.mall.order.infrastructure.enumeration.OmsOrderTypeEnum;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE )
public @interface OmsOrderType {

    OmsOrderTypeEnum value();
}
