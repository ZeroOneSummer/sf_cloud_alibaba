package com.formssi.mall.order.interfaces;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.exception.util.ValidatorUtils;
import com.formssi.mall.oms.cmd.OmsCartItemCmd;
import com.formssi.mall.order.application.IOmsCartItemApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  购物车商品 前端控制器
 * </p>
 *
 * @author 王亮
 * @since 2022-04-13 23:41:53
 */
@RestController
@RequestMapping("/oms/cartItem")
public class OmsCartItemController {

    @Autowired
    private IOmsCartItemApplicationService omsCartItemApplicationService;

    @PostMapping("/list")
    public CommonResp listItem(@RequestBody Long cartId) {
        ValidatorUtils.isNull(cartId, "购物车id不能为空");
        return CommonResp.ok(omsCartItemApplicationService.getListOfOmsCartItem(cartId));
    }

    @PostMapping("/save")
    public CommonResp saveCartItem(@RequestBody @Valid OmsCartItemCmd omsCartItemCmd) {
        omsCartItemApplicationService.addGood(omsCartItemCmd);
        return CommonResp.ok();
    }

    @PostMapping("/update")
    public CommonResp updateCartItem(@RequestBody @Valid OmsCartItemCmd omsCartItemCmd) {
        omsCartItemApplicationService.updateGood(omsCartItemCmd);
        return CommonResp.ok();
    }

    @PostMapping("/delete")
    public CommonResp deleteCartItem(@RequestBody Long id) {
        ValidatorUtils.isNull(id, "商品id不能为空");
        omsCartItemApplicationService.deleteGood(id);
        return CommonResp.ok();
    }

}
