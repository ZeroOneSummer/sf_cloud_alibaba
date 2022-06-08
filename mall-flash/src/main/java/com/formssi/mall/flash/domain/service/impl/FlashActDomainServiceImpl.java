package com.formssi.mall.flash.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.flash.cmd.FlashActCmd;
import com.formssi.mall.flash.cmd.FlashItemCmd;
import com.formssi.mall.flash.domain.entity.FlashActDO;
import com.formssi.mall.flash.domain.entity.FlashItemDO;
import com.formssi.mall.flash.domain.repository.IFlashActRepository;
import com.formssi.mall.flash.domain.repository.IFlashItemRepository;
import com.formssi.mall.flash.domain.service.IFlashActDomainService;
import com.formssi.mall.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class FlashActDomainServiceImpl implements IFlashActDomainService {
    /**
     * 秒杀活动key前缀
     */
    private static final String PREKEY = "falshAct:";
    @Autowired
    private IFlashActRepository iFlashActRepository;

    @Autowired
    private IFlashItemRepository iFlashItemRepository;

    @Autowired
    private RedisService redisService;

    public FlashActDO addAct(FlashActCmd flashActCmd) {
        FlashActDO flashActDO = BeanCopyUtils.copyProperties(flashActCmd, FlashActDO.class);

        //默认审核通过
        flashActDO.setApprovalStatus(1);
        //类型是新增
        flashActDO.setOptionStatus(1);
        flashActDO.setCreateTime(LocalDateTime.now());
        flashActDO.setUpdateTime(LocalDateTime.now());
        iFlashActRepository.save(flashActDO);
        return flashActDO;
    }

    public FlashActDO updateAct(FlashActCmd flashActCmd) {
        FlashActDO flashActDO = BeanCopyUtils.copyProperties(flashActCmd, FlashActDO.class);
        //类型是修改
        flashActDO.setOptionStatus(2);
        flashActDO.setUpdateTime(LocalDateTime.now());
        iFlashActRepository.updateById(flashActDO);
        return flashActDO;
    }

    public void operateAct(Integer operateType, List<FlashActCmd> flashActCmdList) {
        flashActCmdList.stream().forEach(spuCmd -> {
            spuCmd.setApprovalStatus(operateType);
            //类型是修改
            spuCmd.setOptionStatus(2);
            spuCmd.setUpdateTime(LocalDateTime.now());
        });
        List<FlashActDO> menuDTOList = BeanCopyUtils.copyListProperties(flashActCmdList, FlashActDO.class);
        iFlashActRepository.updateBatchById(menuDTOList);
    }
    
    @Override
    public void addItem(Long spuId, List<FlashItemCmd> flashItemCmdList) {
        flashItemCmdList.stream().forEach(itemDo -> {
            itemDo.setActId(spuId);
            itemDo.setOptionStatus(1);
            itemDo.setCreateTime(LocalDateTime.now());
            itemDo.setUpdateTime(LocalDateTime.now());
        });
        List<FlashItemDO> menuDTOList = BeanCopyUtils.copyListProperties(flashItemCmdList, FlashItemDO.class);
        iFlashItemRepository.saveBatch(menuDTOList);
    }
    public void addOrUpdateItem(Long spuId, List<FlashItemCmd> flashItemCmdList) {
        flashItemCmdList.stream().forEach(itemDo -> {
            itemDo.setActId(spuId);
            if(Objects.isNull(itemDo.getId())){//id为空，表示新增
                itemDo.setOptionStatus(1);
                itemDo.setCreateTime(LocalDateTime.now());
            }
            itemDo.setUpdateTime(LocalDateTime.now());
        });
        List<FlashItemDO> menuDTOList = BeanCopyUtils.copyListProperties(flashItemCmdList, FlashItemDO.class);
        iFlashItemRepository.saveOrUpdateBatch(menuDTOList);
    }

    public void deleteItemByActId(Long spuId) {
        QueryWrapper<FlashItemDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(FlashItemDO::getActId, spuId);
        iFlashItemRepository.remove(wrapper);
    }
    public List<FlashItemDO> findItemsByActId(Long actId) {
        QueryWrapper<FlashItemDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(FlashItemDO::getActId, actId);
        return iFlashItemRepository.list(wrapper);
    }
    /**
     * 活动开始前,将商品信息放入redis
     * @param actId
     * @return
     */
    public void putRedis(Long actId) {
        FlashActDO actDo = iFlashActRepository.getById(actId);
        //key等于"flashAct:"+活动id,如 flashAct:2
        String key=PREKEY+actDo.getId();
        //过期时间=活动结束时间-当前时间。
        Duration duration = Duration.between(LocalDateTime.now(),actDo.getEndTime());
        List<FlashItemDO> itemDOList = findItemsByActId(actId);
        itemDOList.forEach(itemDO->{
                    redisService.hset(key,itemDO.getId(),itemDO);
                    //活动结束，缓存就过期。
                    redisService.expire(key,duration.toMillis(), TimeUnit.MILLISECONDS);
                }
        );
    }

    /**
     * 活动开始后,从redis获取商品列表信息
     * @param actId
     * @return
     */
    public List<FlashItemDO> getRedis(Long actId) {
        //key等于"flashAct:"+活动id,如 flashAct:2
        String key=PREKEY+actId;
        Map<Object, Object> hgetall = redisService.hgetall(key);
        List<FlashItemDO> itemDOList=new ArrayList<>();
        hgetall.values().forEach(x->{
            FlashItemDO vo=(FlashItemDO)x;
            itemDOList.add(vo);
        });
        return itemDOList;
    }
}
