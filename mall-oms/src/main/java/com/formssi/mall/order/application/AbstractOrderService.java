package com.formssi.mall.order.application;

import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.order.domain.entity.OmsPayDO;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public abstract class AbstractOrderService {

    /**
     * 新增订单
     * @return
     */
    public OmsPayDO payOrder(){
        //组装参数
        OmsPayDO omsPayDO = fillOmsPayDOValue();
        //更新订单状态
        //调支付
        return omsPayDO;
    }

    protected OmsPayDO fillOmsPayDOValue(){
        return null;
    }


}
