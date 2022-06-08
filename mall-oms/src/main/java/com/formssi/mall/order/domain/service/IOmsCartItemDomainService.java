package com.formssi.mall.order.domain.service;

import com.formssi.mall.oms.cmd.OmsCartItemCmd;
import com.formssi.mall.order.domain.entity.OmsCartItemDO;

import java.util.List;

/**
 * <p>
 * 购物车商品 领域服务类
 * </p>
 *
 * @author wangliang
 * @since 2022-04-12 20:01:13
 */
public interface IOmsCartItemDomainService {

    /**
     * 根据购物车id查询商品

     */
    List<OmsCartItemDO> getListOfOmsCartItem(Long cartId);

    /**
     * 添加商品
     * @param omsCartItemCmd
     */
    void insertOmsCartItem(OmsCartItemCmd omsCartItemCmd);

    /**
     * 根据id删除商品
     * @param Id
     */
    void deleteOmsCartItem(Long Id);

    /**
     * 根据id修改商品
     * @param omsCartItemCmd
     */
    void updateOmsCartItem(OmsCartItemCmd omsCartItemCmd);


}
