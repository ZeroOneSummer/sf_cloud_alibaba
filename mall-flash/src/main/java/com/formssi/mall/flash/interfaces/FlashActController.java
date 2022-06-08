package com.formssi.mall.flash.interfaces;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.exception.util.ValidatorUtils;
import com.formssi.mall.flash.application.IFlashActService;
import com.formssi.mall.flash.cmd.FlashActCmd;
import com.formssi.mall.flash.query.FlashActQry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 活动 前端控制器
 * </p>
 *
 * @author hudemin
 * @since 2022-04-17 23:41:53
 */
@RestController
@RequestMapping("/flash")
@Slf4j
public class FlashActController {
    @Autowired
    private IFlashActService iFlashActService;

    /**
     * 分页查询，查询正在进行中或者即将开始的活动（当前时间小于活动结束时间），且倒序排列。
     * @param flashActQry 查询参数，包含活动名称，活动结束时间（实际活动结束时间大于该参数），活动开始时间（实际活动开始时间小于该参数）
     * @return
     */
    @PostMapping("/listActs")
    public CommonResp listActs(@RequestBody FlashActQry flashActQry) {
        log.info("ini listActs："+flashActQry);
        return CommonResp.ok(iFlashActService.listActs(flashActQry));
    }


    /**
     * 新增活动
     * @param flashActCmd 前端传的活动信息，包含sku信息、秒杀库存信息
     * @return
     */
    @PostMapping("/addAct")
    public CommonResp addAct(@RequestBody @Valid FlashActCmd flashActCmd) {
        iFlashActService.addAct(flashActCmd);
        return CommonResp.ok();
    }

    /**
     * 修改活动，活动是修改。活动的产品可能是新增(id为空)、修改(id有值,option_status=2)或者删除(id有值,option_status=0)
     * @param flashActCmd 前端传的活动信息，包含sku规格、库存信息
     * @return
     */
    @PostMapping("/updateAct")
    public CommonResp updateAct(@RequestBody @Valid FlashActCmd flashActCmd) {
        ValidatorUtils.isNull(flashActCmd.getId(), "活动ID不能为空");
        iFlashActService.updateAct(flashActCmd);
        return CommonResp.ok();
    }


    /**
     * 活动审核操作，支持批量
     * @param flashActCmdList 活动列表
     * @param opType 操作类型 0未通过 1通过
     * @return
     */
    @PostMapping("/operateAct")
    @SentinelResource(value = "opAct")
    public CommonResp operateAct(@RequestParam("opType") Integer opType,@RequestBody List<FlashActCmd> flashActCmdList) {
        log.info("开始审核活动，opType="+opType);
        ValidatorUtils.isCollectionEmpty(flashActCmdList, "请选择活动");
        iFlashActService.operateAct(opType,flashActCmdList);
        return CommonResp.ok();
    }

    /**
     * 秒杀，目前主要调用了商品微服务查询库存，配合测试了限流。
     * @return
     */
    @PostMapping("/flashOrder")
    public CommonResp flashOrder() {
        iFlashActService.flashOrder();
        return CommonResp.ok();
    }

}
