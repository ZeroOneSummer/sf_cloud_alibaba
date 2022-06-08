package com.formssi.mall.order.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.order.domain.entity.OmsCartItemDO;
import com.formssi.mall.order.domain.repository.IOmsCartItemRepository;
import com.formssi.mall.order.domain.repository.IOmsCartRepository;
import com.formssi.mall.order.infrastructure.repository.mapper.OmsCartItemMapper;
import com.formssi.mall.order.infrastructure.repository.mapper.OmsCartMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 购物车商品 仓库实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
@Repository
public class OmsCartItemRepositoryImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItemDO> implements IOmsCartItemRepository {

}
