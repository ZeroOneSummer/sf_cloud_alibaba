package com.formssi.mall.flash.domain.service;

import com.formssi.mall.flash.cmd.FlashActCmd;
import com.formssi.mall.flash.cmd.FlashItemCmd;
import com.formssi.mall.flash.domain.entity.FlashActDO;

import java.util.List;

/**
 * <p>
 * 后台商品 领域服务类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
public interface IFlashActDomainService {
    FlashActDO addAct(FlashActCmd gmsSpuCmd);
    void addItem(Long RoleId, List<FlashItemCmd> roleIds);

    void addOrUpdateItem(Long RoleId, List<FlashItemCmd> roleIds);

    FlashActDO updateAct(FlashActCmd gmsSpuCmd);

    void deleteItemByActId(Long spuId);

    void operateAct(Integer operateType, List<FlashActCmd> gmsItemCmdList);
}
