package com.formssi.mall.goods.domain.service.impl;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.gms.dto.GmsSpuAttributeDTO;
import com.formssi.mall.gms.query.GmsSpuAttributePageQry;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.goods.domain.entity.GmsSpuAttributeDO;
import com.formssi.mall.goods.domain.repository.GmsSpuAttributeRepository;
import com.formssi.mall.goods.domain.service.GmsSpuAttributeDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
public class GmsSpuAttributeDomainImpl implements GmsSpuAttributeDomain {

    @Autowired
    private GmsSpuAttributeRepository gmsSpuAttributeRepository;

    @Override
    public CommonPage<GmsSpuAttributeDO> listSpuAttributePage(GmsSpuAttributePageQry gmsSpuAttributePageQry) {
        return gmsSpuAttributeRepository.listSpecPage(gmsSpuAttributePageQry);
    }

    @Override
    public void saveSpuAttribute(GmsSpuAttributeDTO gmsSpuAttributeDTO) {
        GmsSpuAttributeDO gmsSpuAttributeDO = new GmsSpuAttributeDO();
        BeanCopyUtils.copyProperties(gmsSpuAttributeDO, gmsSpuAttributeDTO);
        gmsSpuAttributeRepository.save(gmsSpuAttributeDO);
    }

    @Override
    public void updateSpuAttribute(GmsSpuAttributeDTO gmsSpuAttributeDTO) {
        GmsSpuAttributeDO gmsSpuAttributeDO = new GmsSpuAttributeDO();
        BeanCopyUtils.copyProperties(gmsSpuAttributeDTO, gmsSpuAttributeDO);
        if (gmsSpuAttributeDTO.getAttributeKey() != null && gmsSpuAttributeDTO.getAttributeValue() != null) {
            gmsSpuAttributeRepository.updateById(gmsSpuAttributeDO);
        }
    }

    @Override
    public void deleteSpuAttribute(Long id) {
        if (!StringUtils.isEmpty(id)) {
            gmsSpuAttributeRepository.removeById(id);
        }
    }

    @Override
    public void deleteSpuAttributeBatch(List<Long> ids) {
        if (!StringUtils.isEmpty(ids)) {
            gmsSpuAttributeRepository.removeBatchByIds(ids);
        }
    }

    @Override
    @Async("gms-thread-pool")
    public Future<List<GmsSpuAttributeDO>> getAttr(GmsSpuDetailQry qry) {
        log.info("---------------商品属性任务----------------");
        List<GmsSpuAttributeDO> list = gmsSpuAttributeRepository.getAttrList(qry);
        return new AsyncResult<>(list);
    }
}
