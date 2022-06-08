package com.formssi.mall.order.controller;

import com.formssi.mall.common.utils.R;
import com.formssi.mall.order.entity.OrderEntity;
import com.formssi.mall.order.entity.TccOrderEntity;
import com.formssi.mall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author chison
 * @email chison@gmail.com
 * @date 2022-05-16 11:42:00
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * AT模式
     * @param orderEntity
     * @return
     */
    @PostMapping("/at/shopping")
    public R shopping(@RequestBody OrderEntity orderEntity){
        orderService.shopping(orderEntity);
        return R.ok();
    }

    /**
     * TCC模式
     * @param tccOrderEntity
     * @return
     */
    @PostMapping("/tcc/shopping")
    public R tccShopping(@RequestBody TccOrderEntity tccOrderEntity){
        orderService.tccShopping(tccOrderEntity);
        return R.ok();
    }



    @PostMapping("/createOrder")
    public R createOrder(@RequestBody OrderEntity orderEntity){
        try {
            orderService.createOrder(orderEntity);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return R.ok();
    }

}
