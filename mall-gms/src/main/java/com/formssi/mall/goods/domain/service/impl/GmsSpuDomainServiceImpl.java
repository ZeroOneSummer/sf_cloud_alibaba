package com.formssi.mall.goods.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.gms.cmd.GmsSkuCmd;
import com.formssi.mall.gms.cmd.GmsSpuCmd;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuPageByCateQry;
import com.formssi.mall.goods.domain.entity.GmsSkuDO;
import com.formssi.mall.goods.domain.entity.GmsSpuDO;
import com.formssi.mall.goods.domain.repository.IGmsSkuRepository;
import com.formssi.mall.goods.domain.repository.IGmsSpuRepository;
import com.formssi.mall.goods.domain.service.IGmsSpuDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class GmsSpuDomainServiceImpl implements IGmsSpuDomainService {
    @Autowired
    private IGmsSpuRepository iGmsSpuRepository;

    @Autowired
    private IGmsSkuRepository iGmsSkuRepository;

    public GmsSpuDO addSpu(GmsSpuCmd gmsSpuCmd) {
        GmsSpuDO gmsSpuDO = BeanCopyUtils.copyProperties(gmsSpuCmd, GmsSpuDO.class);
        //默认下架
        gmsSpuDO.setSpuStatus(2);
        //默认审核通过
        gmsSpuDO.setApprovalStatus(1);
        //类型是新增
        gmsSpuDO.setOptionStatus(1);
        gmsSpuDO.setCreateTime(LocalDateTime.now());
        gmsSpuDO.setUpdateTime(LocalDateTime.now());
        iGmsSpuRepository.save(gmsSpuDO);
        return gmsSpuDO;
    }

    public GmsSpuDO updateSpu(GmsSpuCmd gmsSpuCmd) {
        GmsSpuDO gmsSpuDO = BeanCopyUtils.copyProperties(gmsSpuCmd, GmsSpuDO.class);
        //类型是修改
        gmsSpuDO.setOptionStatus(2);
        gmsSpuDO.setUpdateTime(LocalDateTime.now());
        iGmsSpuRepository.updateById(gmsSpuDO);
        return gmsSpuDO;
    }

    public void operateSpu(Integer operateType, List<GmsSpuCmd> gmsSpuCmdList) {
        gmsSpuCmdList.stream().forEach(spuCmd -> {
            spuCmd.setSpuStatus(operateType);
            //类型是修改
            spuCmd.setOptionStatus(2);
            spuCmd.setUpdateTime(LocalDateTime.now());
        });
        List<GmsSpuDO> menuDTOList = BeanCopyUtils.copyListProperties(gmsSpuCmdList, GmsSpuDO.class);
        iGmsSpuRepository.updateBatchById(menuDTOList);
    }

    /**
     * 根据商品类别查询商品列表
     *
     * @param qry
     * @return
     */
    @Override
    public CommonPage<GmsSpuDO> gmsListSpuByCateId(GmsSpuPageByCateQry qry) {
        return iGmsSpuRepository.gmsListSpuByCateId(qry);
    }

    @Override
    @Async("gms-thread-pool")
    public Future<GmsSpuDO> getSpuDetail(GmsSpuDetailQry qry) {
        log.info("-----------------spu详情任务---------------------");
        GmsSpuDO spuDO = iGmsSpuRepository.getSpuDetail(qry);
        return new AsyncResult<>(spuDO);
    }

    @Override
    public void addSku(Long spuId, List<GmsSkuCmd> gmsSkuCmdList) {
        gmsSkuCmdList.stream().forEach(skuDo -> {
            skuDo.setSpuId(spuId);
        });
        List<GmsSkuDO> menuDTOList = BeanCopyUtils.copyListProperties(gmsSkuCmdList, GmsSkuDO.class);
        iGmsSkuRepository.saveBatch(menuDTOList);
    }

    public void addOrUpdateSku(Long spuId, List<GmsSkuCmd> gmsSkuCmdList) {
        gmsSkuCmdList.stream().forEach(skuDo -> {
            skuDo.setSpuId(spuId);
        });
        List<GmsSkuDO> menuDTOList = BeanCopyUtils.copyListProperties(gmsSkuCmdList, GmsSkuDO.class);
        iGmsSkuRepository.saveOrUpdateBatch(menuDTOList);
    }

    public void deleteSkuBySpuId(Long spuId) {
        QueryWrapper<GmsSkuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(GmsSkuDO::getSpuId, spuId);
        iGmsSkuRepository.remove(wrapper);
    }

}
