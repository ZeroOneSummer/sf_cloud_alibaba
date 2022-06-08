package com.formssi.mall.order.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.oms.cmd.OmsCartCmd;
import com.formssi.mall.order.domain.entity.OmsCartDO;
import com.formssi.mall.order.domain.repository.IOmsCartRepository;
import com.formssi.mall.order.domain.service.IOmsCartDomainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OmsCartDomainServiceImpl implements IOmsCartDomainService {

    @Autowired
    IOmsCartRepository omsCartRepository;
    /**
     * 通过用户id查询购物车
     *
     * @param userId
     * @return
     */
    @Override
    public OmsCartDO getOmsCart(Long userId) {
        QueryWrapper<OmsCartDO> queryWrapper=new QueryWrapper();
        queryWrapper.lambda().eq(OmsCartDO::getMemberId, userId);
        return omsCartRepository.getOne(queryWrapper);
    }

    /**
     * 添加购物车
     *
     * @param omsCartCmd
     */
    @Override
    public void insertOmsCart(OmsCartCmd omsCartCmd) {

    }

    /**
     * 根据用户id删除购物车以及购买的商品
     *
     * @param id
     */
    @Override
    public void deleteOmsCart(Long id) {

    }
}
