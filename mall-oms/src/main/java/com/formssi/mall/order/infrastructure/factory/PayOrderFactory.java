package com.formssi.mall.order.infrastructure.factory;

import com.formssi.mall.order.application.AbstractOrderService;
import com.formssi.mall.order.application.impl.TaoBaoServiceImpl;
import com.formssi.mall.order.application.impl.TianMaoServiceImpl;

/**
 * 订单支付工厂
 */
public class PayOrderFactory {

    public static AbstractOrderService getPayOrderFactory(Integer type){
        AbstractOrderService abstractOrderService = null;
        switch (type){
            case 1:
                abstractOrderService = new TianMaoServiceImpl();
                break;
            case 2:
                abstractOrderService = new TaoBaoServiceImpl();
                break;
            default:
                return null;
        }
        return abstractOrderService;
    }
}
