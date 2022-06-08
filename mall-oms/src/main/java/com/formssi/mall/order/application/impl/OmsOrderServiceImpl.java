package com.formssi.mall.order.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.oms.dto.OmsOrderDTO;
import com.formssi.mall.oms.dto.OmsOrderStatusCountDTO;
import com.formssi.mall.oms.query.OmsOrderQuery;
import com.formssi.mall.order.application.IOmsOrderService;
import com.formssi.mall.order.domain.entity.OmsOrderDO;
import com.formssi.mall.order.domain.repository.IOmsOrderRepository;
import com.formssi.mall.order.domain.service.IOmsOrderDomainService;
import com.formssi.mall.order.infrastructure.util.PageComponent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 訂單服務
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/12 14:25
 */
@Service
public class OmsOrderServiceImpl implements IOmsOrderService {
    @Resource
    IOmsOrderRepository  iOmsOrderRepository;
    @Resource
    PageComponent<OmsOrderDO> pageComponent;
    @Autowired
    IOmsOrderDomainService  iOmsOrderDomainService;
    @Override
    public CommonPage<OmsOrderDTO> queryByOrderStatus(OmsOrderQuery omsOrderQuery) {
        QueryWrapper<OmsOrderDO> queryWrapper=new QueryWrapper<OmsOrderDO>();
        queryWrapper.lambda().eq(OmsOrderDO::getStatus,omsOrderQuery.getStatus());
        CommonPage<OmsOrderDO> page = pageComponent.getPage(queryWrapper, iOmsOrderRepository.getBaseMapper(), omsOrderQuery.getCurrent(), omsOrderQuery.getSize());
        return BeanCopyUtils.copyProperties(page, new CommonPage<OmsOrderDTO>());
    }

    @Override
    public OmsOrderStatusCountDTO queryOrderStatusCount(int memberId) {
        OmsOrderStatusCountDTO omsOrderStatusCountDTO=new OmsOrderStatusCountDTO();
       QueryWrapper<OmsOrderDO> wrapper=new QueryWrapper<OmsOrderDO>();
       wrapper.lambda().eq(OmsOrderDO::getMemberId,memberId).eq(OmsOrderDO::getStatus,1);
       omsOrderStatusCountDTO.setNonPaymentOrder(iOmsOrderRepository.count(wrapper));
       wrapper.lambda().clear();
        wrapper.lambda().eq(OmsOrderDO::getMemberId,memberId).eq(OmsOrderDO::getStatus,2);
        omsOrderStatusCountDTO.setDeliveryOrder(iOmsOrderRepository.count(wrapper));
        wrapper.lambda().clear();
        wrapper.lambda().eq(OmsOrderDO::getMemberId,memberId).eq(OmsOrderDO::getStatus,3);
        omsOrderStatusCountDTO.setShippedOrder(iOmsOrderRepository.count(wrapper));
        wrapper.lambda().clear();
        wrapper.lambda().eq(OmsOrderDO::getMemberId,memberId).eq(OmsOrderDO::getStatus,4);
        omsOrderStatusCountDTO.setSuccessOrder(iOmsOrderRepository.count(wrapper));
        return omsOrderStatusCountDTO;
    }

    @Override
    public CommonResp saveOrder(OmsOrderCmd omsOrderCmd) {
        return iOmsOrderDomainService.saveOmsOrder(omsOrderCmd);
    }
}
