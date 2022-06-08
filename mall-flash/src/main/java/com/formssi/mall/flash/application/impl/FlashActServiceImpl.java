package com.formssi.mall.flash.application.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.feign.GmsSpuFeignClient;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.flash.application.IFlashActService;
import com.formssi.mall.flash.cmd.FlashActCmd;
import com.formssi.mall.flash.domain.entity.FlashActDO;
import com.formssi.mall.flash.domain.repository.IFlashActRepository;
import com.formssi.mall.flash.domain.service.IFlashActDomainService;
import com.formssi.mall.flash.dto.FlashActDTO;
import com.formssi.mall.flash.infrastructure.util.PageComponent;
import com.formssi.mall.flash.query.FlashActQry;
import com.formssi.mall.gms.query.GmsSpuQry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 后台商品 应用服务实现类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
@Service
@Slf4j
public class FlashActServiceImpl implements IFlashActService {

    @Autowired
    private IFlashActRepository iFlashActRepository;

    @Autowired
    private PageComponent<FlashActDO> pageComponent;

    @Autowired
    private IFlashActDomainService iFlashActDomainService;

    @Autowired
    GmsSpuFeignClient gmsSpuFeignClient;
    @Override
    public CommonPage<FlashActDTO> listActs(FlashActQry flashActQry) {
        QueryWrapper<FlashActDO> wrapper = new QueryWrapper<>();
        //如果结束时间是空，默认是现在（查询正在进行或者没开始的活动）。
        if(Objects.isNull(flashActQry.getEndTime())){
            flashActQry.setEndTime(LocalDateTime.now());
        }
        //如果开始时间是空，默认是现在+30分钟（查询开始时间在30分钟以内）。
        if(Objects.isNull(flashActQry.getStartTime())){
            flashActQry.setStartTime(LocalDateTime.now().plusMinutes(30L));
        }
        wrapper.lambda().like(StringUtils.isNotBlank(flashActQry.getActName()), FlashActDO::getName, flashActQry.getActName())
                .gt(Objects.nonNull(flashActQry.getEndTime()),FlashActDO::getEndTime,flashActQry.getEndTime())
                .lt(Objects.nonNull(flashActQry.getEndTime()),FlashActDO::getStartTime,flashActQry.getStartTime())
                .orderByAsc(FlashActDO::getStartTime);
        CommonPage<FlashActDO> page = pageComponent.getPage(wrapper, iFlashActRepository.getBaseMapper(), flashActQry.getCurrent(), flashActQry.getSize());
        page.getRecords().forEach(x-> log.info("活动名称:"+x.getName()));
        return BeanCopyUtils.copyProperties(page, new CommonPage<>());
    }
    @Transactional
    public void addAct(FlashActCmd flashActCmd) {
        // 新增商品
        FlashActDO flashActDO = iFlashActDomainService.addAct(flashActCmd);
        // 新增sku
        iFlashActDomainService.addItem(flashActDO.getId(), flashActCmd.getFlashItemCmdList());
    }
    @Transactional
    public void updateAct(FlashActCmd flashActCmd) {
        // 修改商品
        FlashActDO flashActDO = iFlashActDomainService.updateAct(flashActCmd);
        // 新增或者修改sku，id存在就是修改，反之是修改。删除的逻辑是软删除，删除标记值有前端传入
        iFlashActDomainService.addOrUpdateItem(flashActDO.getId(), flashActCmd.getFlashItemCmdList());
    }

    public void operateAct(Integer operateType, List<FlashActCmd> flashActCmdList) {
        iFlashActDomainService.operateAct(operateType,flashActCmdList);
    }
    @SentinelResource(value = "flashOrder",blockHandler ="blockHandler",fallback="fallbackHandler"  )
    public void flashOrder() {
        //int a=1/0;
        log.info("进入秒杀");
        //调用商品服务查看库存
        CommonResp commonResp = gmsSpuFeignClient.listSpus(new GmsSpuQry());
        log.info(commonResp.getBody().toString());
    }

    /**
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     * @param ex
     */
    public void blockHandler(BlockException ex) {
        log.info("当前系统太忙，请稍后再试!"+ex);
    }

    /**
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
     */
    public void fallbackHandler() {
        log.info("当前系统异常，请稍后再试!");
    }
}