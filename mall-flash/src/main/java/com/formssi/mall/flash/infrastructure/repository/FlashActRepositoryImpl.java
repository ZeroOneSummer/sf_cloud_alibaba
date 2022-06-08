package com.formssi.mall.flash.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.flash.domain.entity.FlashActDO;
import com.formssi.mall.flash.domain.repository.IFlashActRepository;
import com.formssi.mall.flash.infrastructure.repository.mapper.FlashActMapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 后台商品 仓库实现类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
@Repository
public class FlashActRepositoryImpl extends ServiceImpl<FlashActMapper, FlashActDO> implements IFlashActRepository {

}
