package com.formssi.mall.order.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.order.domain.entity.OmsOrderDO;
import com.formssi.mall.order.domain.repository.IOmsOrderRepository;
import com.formssi.mall.order.infrastructure.repository.mapper.OmsOrderMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/11 14:37
 */
@Repository
public class OmsOrderRepositoryImpl extends ServiceImpl<OmsOrderMapper, OmsOrderDO> implements IOmsOrderRepository {
    @Override
    public List<OmsOrderDO> queryListOmsOrderDO() {
        List<OmsOrderDO> omsOrderDOList = new ArrayList<>();
        OmsOrderDO omsOrderDO1 = new OmsOrderDO();
        omsOrderDO1.setId(1L);
        omsOrderDO1.setOrderSn("O_111111");
        omsOrderDO1.setOrderType(0);
        omsOrderDO1.setBillReceiverEmail("111111111@qq.com");
        omsOrderDO1.setReceiverPhone("13111111111");
        omsOrderDO1.setReceiverAddress("广东省深圳市南山区壹方城1楼");
        omsOrderDO1.setReceiverName("张三");
        omsOrderDO1.setCreateTime(new Date());
        omsOrderDOList.add(omsOrderDO1);
        OmsOrderDO omsOrderDO2 = new OmsOrderDO();
        omsOrderDO2.setId(2L);
        omsOrderDO2.setOrderSn("O_222222");
        omsOrderDO2.setOrderType(0);
        omsOrderDO2.setBillReceiverEmail("22222222222@qq.com");
        omsOrderDO2.setReceiverPhone("13222222222");
        omsOrderDO2.setReceiverAddress("广东省深圳市南山区壹方城2楼");
        omsOrderDO2.setReceiverName("张李四");
        omsOrderDO2.setCreateTime(new Date());
        omsOrderDOList.add(omsOrderDO2);
        OmsOrderDO omsOrderDO3 = new OmsOrderDO();
        omsOrderDO3.setId(3L);
        omsOrderDO3.setOrderSn("O_3333333");
        omsOrderDO3.setOrderType(0);
        omsOrderDO3.setBillReceiverEmail("333333333@qq.com");
        omsOrderDO3.setReceiverPhone("13333333333");
        omsOrderDO3.setReceiverAddress("广东省深圳市南山区壹方城3楼");
        omsOrderDO3.setReceiverName("王五");
        omsOrderDO3.setCreateTime(new Date());
        omsOrderDOList.add(omsOrderDO3);
        return omsOrderDOList;
    }
}
