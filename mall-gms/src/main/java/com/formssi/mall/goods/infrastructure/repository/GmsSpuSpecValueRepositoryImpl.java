package com.formssi.mall.goods.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecValueDO;
import com.formssi.mall.goods.domain.repository.GmsSpuSpecValueRepository;
import com.formssi.mall.goods.infrastructure.repository.mapper.GmsSpuSpecValueMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
@Transactional
public class GmsSpuSpecValueRepositoryImpl extends ServiceImpl<GmsSpuSpecValueMapper, GmsSpuSpecValueDO> implements GmsSpuSpecValueRepository {

    @Override
    public List<GmsSpuSpecValueDO> getSpecValueIds(List<Long> ids) {
        LambdaQueryWrapper<GmsSpuSpecValueDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!CollectionUtils.isEmpty(ids),GmsSpuSpecValueDO::getSpecId, ids);
        return this.list(queryWrapper);
    }
}
