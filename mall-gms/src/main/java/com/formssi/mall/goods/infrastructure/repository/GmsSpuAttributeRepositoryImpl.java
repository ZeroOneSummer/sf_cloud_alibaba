package com.formssi.mall.goods.infrastructure.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.query.GmsSpuAttributePageQry;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.goods.domain.entity.GmsSpuAttributeDO;
import com.formssi.mall.goods.domain.repository.GmsSpuAttributeRepository;
import com.formssi.mall.goods.infrastructure.repository.mapper.GmsSpuAttributeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class GmsSpuAttributeRepositoryImpl extends ServiceImpl<GmsSpuAttributeMapper, GmsSpuAttributeDO> implements GmsSpuAttributeRepository {

    @Override
    public CommonPage<GmsSpuAttributeDO> listSpecPage(GmsSpuAttributePageQry gmsSpuAttributePageQry) {
        IPage<GmsSpuAttributeDO> page = new Page<>(gmsSpuAttributePageQry.getCurrent(), gmsSpuAttributePageQry.getSize());
        LambdaQueryWrapper<GmsSpuAttributeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(gmsSpuAttributePageQry.getAttributeKey() != null, GmsSpuAttributeDO::getAttributeKey, gmsSpuAttributePageQry.getAttributeKey());
        wrapper.eq(gmsSpuAttributePageQry.getAttributeValue() != null, GmsSpuAttributeDO::getAttributeValue, gmsSpuAttributePageQry.getAttributeValue());
        this.page(page, wrapper);
        return new CommonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 商品属性查询
     *
     * @param qry
     * @return
     */
    @Override
    public List<GmsSpuAttributeDO> getAttrList(GmsSpuDetailQry qry) {
        LambdaQueryWrapper<GmsSpuAttributeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(true, item -> item.eq(GmsSpuAttributeDO::getSpuId, qry.getSpuId()));
        return this.list(wrapper);
    }
}
