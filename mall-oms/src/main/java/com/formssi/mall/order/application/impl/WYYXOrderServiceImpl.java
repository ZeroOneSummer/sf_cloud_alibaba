package com.formssi.mall.order.application.impl;

import com.alibaba.fastjson.JSONObject;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.order.application.OrderService;
import com.formssi.mall.order.infrastructure.annotation.OmsOrderType;
import com.formssi.mall.order.infrastructure.enumeration.OmsOrderTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@OmsOrderType(value = OmsOrderTypeEnum.WYYX_ORDER)
@Slf4j
@Service
public class WYYXOrderServiceImpl implements OrderService {
    @Override
    public String saveOrder(Supplier<OmsOrderCmd> omsOrderCmd) {
        log.info("新增网易严选订单：{}", JSONObject.toJSONString(omsOrderCmd.get()));
        return "新增网易严选订单成功";
    }
}
