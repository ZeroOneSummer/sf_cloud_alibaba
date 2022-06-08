package com.formssi.mall.flash.application;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.flash.cmd.FlashActCmd;
import com.formssi.mall.flash.dto.FlashActDTO;
import com.formssi.mall.flash.query.FlashActQry;

import java.util.List;

/**
 * <p>
 * 后台商品 应用服务类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
public interface IFlashActService {
    CommonPage<FlashActDTO> listActs(FlashActQry flashActQry);
    void addAct(FlashActCmd flashActCmd);

    void updateAct(FlashActCmd flashActCmd);

    void operateAct(Integer operateType, List<FlashActCmd> flashActCmdList);

    void flashOrder();
}
