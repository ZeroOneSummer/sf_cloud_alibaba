package com.formssi.mall.goods.domain.service.impl;

import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.goods.domain.entity.GmsSkuDO;
import com.formssi.mall.goods.domain.repository.IGmsSkuRepository;
import com.formssi.mall.goods.domain.service.GmsSkuDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author:prms
 * @create: 2022-04-20 15:25
 * @version: 1.0
 */
@Service
@Slf4j
public class GmsSkuDomainServiceImpl implements GmsSkuDomain {

    @Resource
    IGmsSkuRepository gmsSkuRepository;


    @Override
    @Async("gms-thread-pool")
    public Future<List<GmsSkuDO>> getSkuDetail(GmsSpuDetailQry qry) {
        log.info("-------------查询sku任务----------------");
        List<GmsSkuDO> list = gmsSkuRepository.getSkuDetail(qry);
        return new AsyncResult<>(list);
    }
}
