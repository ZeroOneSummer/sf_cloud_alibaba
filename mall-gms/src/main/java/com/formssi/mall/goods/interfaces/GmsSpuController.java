package com.formssi.mall.goods.interfaces;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.exception.util.ValidatorUtils;
import com.formssi.mall.gms.cmd.GmsSpuCmd;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuQry;
import com.formssi.mall.gms.query.GmsSpuSearchQry;
import com.formssi.mall.gms.vo.GmsSpuDetailVO;
import com.formssi.mall.goods.application.IGmsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author hudemin
 * @since 2022-04-17 23:41:53
 */
@RestController
@RequestMapping("/gms")
@Slf4j
public class GmsSpuController {
    /**
     * 上架
     */
    public static final int SPU_UP = 1;

    /**
     * 下架
     */
    public static final int SPU_DOWN = 2;
    @Autowired
    private IGmsSpuService iGmsSpuService;

    /**
     * 分页查询
     *
     * @param gmsSpuQry 查询参数，包含商品名称等
     * @return
     */
    @PostMapping("/listSpus")
    public CommonResp listSpus(@RequestBody GmsSpuQry gmsSpuQry) {
        log.info("find Good info--");
        int i = 1 / 0;
        return CommonResp.ok(iGmsSpuService.listSpus(gmsSpuQry));
    }

    /**
     * 新增商品
     *
     * @param gmsSpuCmd 前端传的商品信息，包含sku规格、库存信息
     * @return
     */
    @PostMapping("/addSpu")
    public CommonResp addSpu(@RequestBody @Valid GmsSpuCmd gmsSpuCmd) {
        iGmsSpuService.addSpu(gmsSpuCmd);
        return CommonResp.ok();
    }

    /**
     * 修改商品
     *
     * @param gmsSpuCmd 前端传的商品信息，包含sku规格、库存信息
     * @return
     */
    @PostMapping("/updateSpu")
    public CommonResp updateSpu(@RequestBody @Valid GmsSpuCmd gmsSpuCmd) {
        ValidatorUtils.isNull(gmsSpuCmd.getId(), "商品ID不能为空");
        iGmsSpuService.updateSpu(gmsSpuCmd);
        return CommonResp.ok();
    }


    /**
     * 商品上架、下载操作，支持批量
     *
     * @param gmsSpuCmdList 商品列表
     * @param opType        操作类型 1：上架，2：下架
     * @return
     */
    @PostMapping("/operateSpu")
    public CommonResp operateSpu(@RequestParam("opType") Integer opType, @RequestBody List<GmsSpuCmd> gmsSpuCmdList) {
        ValidatorUtils.isCollectionEmpty(gmsSpuCmdList, "请选择商品");
        iGmsSpuService.operateSpu(opType, gmsSpuCmdList);
        return CommonResp.ok();
    }

    @GetMapping("/spu/detail")
    public CommonResp<GmsSpuDetailVO> spuDetail(@RequestBody @Valid GmsSpuDetailQry req) {
        GmsSpuDetailVO vo = iGmsSpuService.spuDetail(req);
        return CommonResp.ok(vo);
    }

    @GetMapping("/spu/search")
    public CommonResp<List<GmsSpuDTO>> searchSpuByKey(@RequestBody @Valid GmsSpuSearchQry req) {
        return CommonResp.ok(iGmsSpuService.searchSpuBykey(req));
    }
}
