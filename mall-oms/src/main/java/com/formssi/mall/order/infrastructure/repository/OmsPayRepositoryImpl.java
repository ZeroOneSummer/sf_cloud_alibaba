package com.formssi.mall.order.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.order.domain.entity.OmsPayDO;
import com.formssi.mall.order.domain.repository.IOmsPayRepository;
import com.formssi.mall.order.infrastructure.repository.mapper.OmsPayMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/11 14:35
 */
@Repository
public class OmsPayRepositoryImpl extends ServiceImpl<OmsPayMapper, OmsPayDO> implements IOmsPayRepository {
}
