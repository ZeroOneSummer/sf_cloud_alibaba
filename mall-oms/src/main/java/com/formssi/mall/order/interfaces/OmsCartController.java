package com.formssi.mall.order.interfaces;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.oms.cmd.OmsCartItemCmd;
import com.formssi.mall.order.application.IOmsCartApplicationService;
import com.formssi.mall.order.application.IOmsCartItemApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  购物车 前端控制器
 * </p>
 *
 * @author 王亮
 * @since 2022-04-13 23:41:53
 */
@RestController
@RequestMapping("/oms/cart")
public class OmsCartController {

    @Autowired
    private IOmsCartApplicationService omsCartApplicationService;


    @PostMapping("/save")
    public CommonResp saveCart() {
        omsCartApplicationService.addCart();
        return CommonResp.ok();
    }

    @PostMapping("/delete")
    public CommonResp deleteCart() {
        omsCartApplicationService.deleteCart();
        return CommonResp.ok();
    }

}
