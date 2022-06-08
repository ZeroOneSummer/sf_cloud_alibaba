package com.formssi.mall.goods.application.impl;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.gms.dto.GmsSpuAttributeDTO;
import com.formssi.mall.gms.query.GmsSpuAttributePageQry;
import com.formssi.mall.goods.application.GmsSpuAttributeService;
import com.formssi.mall.goods.domain.entity.GmsSpuAttributeDO;
import com.formssi.mall.goods.domain.service.GmsSpuAttributeDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GmsSpuAttributeServiceImpl implements GmsSpuAttributeService {

    @Autowired
    private GmsSpuAttributeDomain gmsSpuAttributeDomain;

    @Override
    public CommonPage<GmsSpuAttributeDTO> listSpuAttributePage(GmsSpuAttributePageQry gmsSpuAttributePageQry) {
        CommonPage<GmsSpuAttributeDO>  listSpuAttributeDO = gmsSpuAttributeDomain.listSpuAttributePage(gmsSpuAttributePageQry);
        return BeanCopyUtils.copyProperties(listSpuAttributeDO, new CommonPage<>());
    }

    @Override
    public void saveSpuAttribute(GmsSpuAttributeDTO gmsSpuAttributeDTO) {
        gmsSpuAttributeDomain.saveSpuAttribute(gmsSpuAttributeDTO);
    }

    @Override
    public void deleteSpuAttribute(Long id) {
        gmsSpuAttributeDomain.deleteSpuAttribute(id);
    }

    @Override
    public void updateSpuAttribute(GmsSpuAttributeDTO gmsSpuAttributeDTO) {
        gmsSpuAttributeDomain.updateSpuAttribute(gmsSpuAttributeDTO);
    }

    @Override
    public void deleteSpuAttributeBatch(List<Long> ids) {
        gmsSpuAttributeDomain.deleteSpuAttributeBatch(ids);
    }
}
