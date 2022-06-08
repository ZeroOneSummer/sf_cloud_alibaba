package com.formssi.mall.goods.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.goods.domain.entity.GmsSpuSpecValueDO;

import java.util.List;

public interface GmsSpuSpecValueRepository extends IService<GmsSpuSpecValueDO> {

    List<GmsSpuSpecValueDO> getSpecValueIds(List<Long> ids);

}
