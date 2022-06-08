package com.formssi.mall.goods.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.goods.domain.entity.GmsSkuDO;
import com.formssi.mall.goods.domain.repository.IGmsSkuRepository;
import com.formssi.mall.goods.infrastructure.repository.mapper.GmsSkuMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 后台商品 仓库实现类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
@Repository
public class GmsSkuRepositoryImpl extends ServiceImpl<GmsSkuMapper, GmsSkuDO> implements IGmsSkuRepository {


    @Override
    public List<GmsSkuDO> getSkuDetail(GmsSpuDetailQry qry) {
        LambdaQueryWrapper<GmsSkuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(true, item -> item.eq(GmsSkuDO::getSpuId, qry.getSpuId()));
        return this.list(wrapper);
    }
}
