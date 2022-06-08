package com.formssi.mall.goods.infrastructure.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecDO;
import com.formssi.mall.goods.domain.repository.GmsSpuSpecRepository;
import com.formssi.mall.goods.infrastructure.repository.mapper.GmsSpuSpecMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class GmsSpuSpecRepositoryImpl extends ServiceImpl<GmsSpuSpecMapper, GmsSpuSpecDO> implements GmsSpuSpecRepository {

    @Override
    public CommonPage<GmsSpuSpecDO> listSpecPage(GmsSpuSpecPageQry gmsSpuSpecPageQry) {
        IPage<GmsSpuSpecDO> page = new Page<>(gmsSpuSpecPageQry.getCurrent(), gmsSpuSpecPageQry.getSize());
        LambdaQueryWrapper<GmsSpuSpecDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(gmsSpuSpecPageQry.getSpecName() != null, GmsSpuSpecDO::getSpecName, gmsSpuSpecPageQry.getSpecName());
        wrapper.eq(gmsSpuSpecPageQry.getStatus() != null, GmsSpuSpecDO::getStatus, gmsSpuSpecPageQry.getStatus());
        wrapper.eq(gmsSpuSpecPageQry.getCatalogId() != null, GmsSpuSpecDO::getCatalogId, gmsSpuSpecPageQry.getCatalogId());
        this.page(page, wrapper);
        return new CommonPage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }
}
