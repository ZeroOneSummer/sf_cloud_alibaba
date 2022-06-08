package com.formssi.mall.order.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.order.domain.entity.OmsOrderItemDO;
import com.formssi.mall.order.domain.repository.IOmsOrderItemRepository;
import com.formssi.mall.order.infrastructure.repository.mapper.OmsOrderItemMapper;
import org.springframework.stereotype.Repository;

import javax.xml.ws.Service;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/11 14:33
 */
@Repository
public class OmsOrderItemRepositoryImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItemDO> implements IOmsOrderItemRepository {
}
