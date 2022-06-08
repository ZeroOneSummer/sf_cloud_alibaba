package com.formssi.mall.goods.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.gms.query.GmsCategoryPageQry;
import com.formssi.mall.goods.domain.entity.GmsCategoryDo;
import com.formssi.mall.goods.domain.repository.GmsCategoryRepository;
import com.formssi.mall.goods.infrastructure.repository.mapper.GmsCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:prms
 * @create: 2022-04-19 17:00
 * @version: 1.0
 */
@Component
@Slf4j
public class GmsCategoryRepositoryImpl extends ServiceImpl<GmsCategoryMapper, GmsCategoryDo> implements GmsCategoryRepository {


    @Override
    public List<GmsCategoryDo> listCate() {

        LambdaQueryWrapper<GmsCategoryDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(true, item -> item.eq(GmsCategoryDo::getOptionStatus, 1))
                .orderBy(true, false, GmsCategoryDo::getPriority);
        return this.list(wrapper);
    }
}
