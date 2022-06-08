package com.formssi.mall.order.application;

import com.formssi.mall.oms.cmd.OmsOrderCmd;

import java.util.function.Supplier;

public interface OrderService {

    /**
     * 新增订单
     * @param omsOrderCmd
     * @return
     */
    String saveOrder(Supplier<OmsOrderCmd> omsOrderCmd);


}
