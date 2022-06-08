package com.formssi.mall.order.application;

import com.formssi.mall.oms.cmd.OmsCartItemCmd;

import java.util.List;

/**
 * <p>
 * 购物车商品 应用接口层
 * </p>
 *
 * @author 王亮
 * @since 2022-04-13 20:01:14
 */
public interface IOmsCartItemApplicationService {

    /**
     * 根据购物车id获取用户的购物车商品清单
     */
    List<OmsCartItemCmd> getListOfOmsCartItem(Long cartId);
    /**
     * 向购物车添加商品
     * @param omsCartItemCmd
     */
    void addGood(OmsCartItemCmd omsCartItemCmd);

    /**
     * 根据id修改购物车商品
     * @param omsCartItemCmd
     */
    void updateGood(OmsCartItemCmd omsCartItemCmd);

    /**
     * 根据id删除购物车商品
     * @param id
     */
    void deleteGood(Long id);


}
