package com.formssi.mall.order.application.impl;

import com.alibaba.fastjson.JSONObject;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.order.application.OrderService;
import com.formssi.mall.order.domain.entity.OmsOrderDO;
import com.formssi.mall.order.domain.repository.IOmsOrderRepository;
import com.formssi.mall.order.infrastructure.annotation.OmsOrderType;
import com.formssi.mall.order.infrastructure.enumeration.OmsOrderTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.function.Supplier;

@OmsOrderType(value = OmsOrderTypeEnum.JD_ORDER)
@Slf4j
@Service
public class JDOrderServiceImpl implements OrderService {

    @Autowired
    private IOmsOrderRepository iOmsOrderRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveOrder(Supplier<OmsOrderCmd> omsOrderCmd) {
        OmsOrderCmd omsOrderCmd1 = omsOrderCmd.get();
        OmsOrderDO omsOrderDO = new OmsOrderDO();
        BeanUtils.copyProperties(omsOrderCmd1,omsOrderDO);
        omsOrderDO.setCreateTime(new Date());
        omsOrderDO.setExpireTime(new Date());
        omsOrderDO.setStatus(1);
        iOmsOrderRepository.save(omsOrderDO);
        log.info("新增京东订单：{}", JSONObject.toJSONString(omsOrderCmd1));
        return "新增京东订单成功";
    }
}
