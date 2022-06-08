package com.formssi.mall.goods.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuPageByCateQry;
import com.formssi.mall.goods.domain.entity.GmsSpuDO;
import com.formssi.mall.goods.domain.repository.IGmsSpuRepository;
import com.formssi.mall.goods.infrastructure.repository.mapper.GmsSpuMapper;
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
public class GmsSpuRepositoryImpl extends ServiceImpl<GmsSpuMapper, GmsSpuDO> implements IGmsSpuRepository {

    /**
     * 根据商品分类查询列表
     *
     * @param qry
     * @return
     */
    @Override
    public CommonPage<GmsSpuDO> gmsListSpuByCateId(GmsSpuPageByCateQry qry) {

        IPage<GmsSpuDO> page = new Page<>(qry.getCurrent(), qry.getSize());
        LambdaQueryWrapper<GmsSpuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(true, item -> item.eq(GmsSpuDO::getApprovalStatus, 1))
                .and(true, item -> item.eq(GmsSpuDO::getOptionStatus, 1))
                .and(true, item -> item.eq(GmsSpuDO::getSpuStatus, 1))
                .and(qry.getCateId() != null, item -> item.eq(GmsSpuDO::getCatalogId, qry.getCateId()))
                .orderBy(true, false, GmsSpuDO::getPriority);
        this.page(page, wrapper);
        return new CommonPage(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 查询商品spu详情
     *
     * @param qry
     * @return
     */
    @Override
    public GmsSpuDO getSpuDetail(GmsSpuDetailQry qry) {
        LambdaQueryWrapper<GmsSpuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(true, item -> item.eq(GmsSpuDO::getId, qry.getSpuId()))
                .and(true, item -> item.eq(GmsSpuDO::getSpuSn, qry.getSpuSn()));
        return this.getOne(wrapper);
    }
}
