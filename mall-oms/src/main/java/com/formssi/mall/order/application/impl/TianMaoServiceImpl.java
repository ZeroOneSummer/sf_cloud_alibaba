package com.formssi.mall.order.application.impl;

import com.formssi.mall.order.application.AbstractOrderService;
import com.formssi.mall.order.domain.entity.OmsPayDO;

public class TianMaoServiceImpl extends AbstractOrderService {
    @Override
    protected OmsPayDO fillOmsPayDOValue() {
        System.out.println("返回天猫参数");
        OmsPayDO omsPayDO = new OmsPayDO();
        omsPayDO.setId(1L);
        omsPayDO.setPayAmount(100L);
        omsPayDO.setPayType(1);
        omsPayDO.setOrderId(1L);
        omsPayDO.setOrderSn("天猫111111");
        omsPayDO.setFee(1L);
        return omsPayDO;
    }
}
