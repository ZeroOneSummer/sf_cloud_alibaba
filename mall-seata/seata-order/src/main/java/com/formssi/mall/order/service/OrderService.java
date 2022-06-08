package com.formssi.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.order.entity.OrderEntity;
import com.formssi.mall.order.entity.TccOrderEntity;


/**
 * @author chison
 * @email chison@gmail.com
 * @date 2022-05-16 11:42:00
 */
public interface OrderService extends IService<OrderEntity> {

    void shopping(OrderEntity orderEntity);

    void createOrder(OrderEntity orderEntity);

    void tccShopping(TccOrderEntity tccOrderEntity);

}

