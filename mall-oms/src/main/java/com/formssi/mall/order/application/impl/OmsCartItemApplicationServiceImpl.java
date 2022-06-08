package com.formssi.mall.order.application.impl;

import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.oms.cmd.OmsCartItemCmd;
import com.formssi.mall.order.application.IOmsCartItemApplicationService;
import com.formssi.mall.order.domain.entity.OmsCartItemDO;
import com.formssi.mall.order.domain.service.IOmsCartItemDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * <p>
 * 购物车商品 应用实现层
 * </p>
 *
 * @author 王亮
 * @since 2022-04-13 20:01:14
 */
@Transactional
@Service
public class OmsCartItemApplicationServiceImpl implements IOmsCartItemApplicationService {


    @Autowired
    IOmsCartItemDomainService omsCartItemDomainService;




    /**
     * 根据购物车id获取用户的购物车商品清单
     */
    @Override
    public List<OmsCartItemCmd> getListOfOmsCartItem(Long cartId) {

        //查询用户的商品清单
        List<OmsCartItemDO> omsCartItemDOs = omsCartItemDomainService.getListOfOmsCartItem(cartId);
        List<OmsCartItemCmd> omsCartItemCmdList = BeanCopyUtils.copyListProperties(omsCartItemDOs, OmsCartItemCmd.class);
        return omsCartItemCmdList;
    }

    /**
     * 向购物车添加商品
     *
     * @param omsCartItemCmd
     */
    @Override
    public void addGood(OmsCartItemCmd omsCartItemCmd) {
        //添加商品
        omsCartItemDomainService.insertOmsCartItem(omsCartItemCmd);


    }

    /**
     * 根据id修改购物车商品
     *
     * @param omsCartItemCmd
     */
    @Override
    public void updateGood(OmsCartItemCmd omsCartItemCmd) {
        omsCartItemDomainService.updateOmsCartItem(omsCartItemCmd);
    }

    /**
     * 根据id删除购物车商品
     *
     * @param id
     */
    @Override
    public void deleteGood(Long id) {
        omsCartItemDomainService.deleteOmsCartItem(id);
    }
}
