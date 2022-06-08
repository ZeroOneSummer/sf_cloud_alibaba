package com.formssi.mall.order.domain.service;

import com.formssi.mall.oms.cmd.OmsCartCmd;
import com.formssi.mall.order.domain.entity.OmsCartDO;

/**
 * <p>
 * 购物车 领域服务类
 * </p>
 *
 * @author wangliang
 * @since 2022-04-12 20:01:13
 */
public interface IOmsCartDomainService {
    /**
     * 通过用户id查询购物车
     * @param userId
     * @return
     */
    OmsCartDO getOmsCart(Long userId);

    /**
     * 添加购物车
     * @param omsCartCmd
     */
    void insertOmsCart(OmsCartCmd omsCartCmd);

    /**
     * 根据用户id删除购物车以及购买的商品
     * @param id
     */
    void deleteOmsCart(Long id);

}
