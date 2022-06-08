package com.formssi.mall.order.domain.service.impl;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.order.domain.entity.OmsOrderDO;
import com.formssi.mall.order.domain.entity.OmsOrderItemDO;
import com.formssi.mall.order.domain.repository.IOmsOrderItemRepository;
import com.formssi.mall.order.domain.repository.IOmsOrderRepository;
import com.formssi.mall.order.domain.service.IOmsOrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/12 9:09
 */
@Service
public class OmsOrderDomainServiceImpl implements IOmsOrderDomainService {
    @Resource
    IOmsOrderRepository iOmsOrderRepository;
    @Resource
    IOmsOrderItemRepository iOmsOrderItemRepository;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResp saveOmsOrder(OmsOrderCmd omsOrderCmd) {
        OmsOrderDO omsOrderDO=new OmsOrderDO();
        BeanCopyUtils.copyProperties(omsOrderCmd,omsOrderDO);
        iOmsOrderRepository.save(omsOrderDO);
        OmsOrderItemDO omsOrderItemDO=new OmsOrderItemDO();
        BeanCopyUtils.copyProperties(omsOrderCmd,omsOrderItemDO);
        iOmsOrderItemRepository.save(omsOrderItemDO);
        return CommonResp.ok();
    }
}
