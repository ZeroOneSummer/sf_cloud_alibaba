package com.formssi.mall.order.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.order.domain.entity.OmsOrderDO;

import java.util.List;
import java.util.function.Supplier;

/**
 * <p>
 * 訂單Mapper接口
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/11 11:45
 */
public interface IOmsOrderRepository extends IService<OmsOrderDO>
{
    List<OmsOrderDO>  queryListOmsOrderDO();
}
