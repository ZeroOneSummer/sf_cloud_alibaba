package com.formssi.mall.order.application;

/**
 * <p>
 * 购物车 应用接口层
 * </p>
 *
 * @author 王亮
 * @since 2022-04-13 20:01:14
 */
public interface IOmsCartApplicationService {
    /**
     * 给会员分配购物车
     */
    void addCart();


    /**
     * 删除购物车
     * @param
     */
    void deleteCart();
}
