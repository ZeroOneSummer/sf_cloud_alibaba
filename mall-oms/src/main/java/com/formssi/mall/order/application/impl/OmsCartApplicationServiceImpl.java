package com.formssi.mall.order.application.impl;

import com.formssi.mall.order.application.IOmsCartApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <p>
 * 购物车 应用实现层
 * </p>
 *
 * @author 王亮
 * @since 2022-04-13 20:01:14
 */
@Transactional
@Service
public class OmsCartApplicationServiceImpl implements IOmsCartApplicationService {
    /**
     * 给会员分配购物车
     */
    @Override
    public void addCart() {

    }

    /**
     * 删除购物车
     */
    @Override
    public void deleteCart() {
    }
}
