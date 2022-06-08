package com.formssi.mall.order.application.impl;

import com.formssi.mall.order.application.AbstractOrderService;
import com.formssi.mall.order.domain.entity.OmsPayDO;

public class TaoBaoServiceImpl extends AbstractOrderService {
    @Override
    protected OmsPayDO fillOmsPayDOValue() {
        System.out.println("返回淘宝参数");
        OmsPayDO omsPayDO = new OmsPayDO();
        omsPayDO.setId(2L);
        omsPayDO.setPayAmount(200L);
        omsPayDO.setPayType(2);
        omsPayDO.setOrderId(2L);
        omsPayDO.setOrderSn("淘宝22222");
        omsPayDO.setFee(2L);
        return omsPayDO;
    }
}
