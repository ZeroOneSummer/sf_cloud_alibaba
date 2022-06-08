package com.formssi.mall.order.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.common.constant.oms.OmsConstant;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.jwt.service.JwtTokenService;
import com.formssi.mall.oms.cmd.OmsCartItemCmd;
import com.formssi.mall.order.domain.entity.OmsCartDO;
import com.formssi.mall.order.domain.entity.OmsCartItemDO;
import com.formssi.mall.order.domain.repository.IOmsCartItemRepository;
import com.formssi.mall.order.domain.repository.IOmsCartRepository;
import com.formssi.mall.order.domain.service.IOmsCartItemDomainService;
import com.formssi.mall.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 购物车商品 领域服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2022-04-13 20:01:13
 */
@Service
public class OmsCartItemDomainServiceImpl implements IOmsCartItemDomainService {

    @Autowired
    IOmsCartItemRepository omsCartItemRepository;

    @Autowired
    IOmsCartRepository omsCartRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    RedisService redisService;

    /**
     * 根据购物车id查询商品
     */
    @Override
    public List<OmsCartItemDO> getListOfOmsCartItem(Long cartId) {

        //先从缓存中取数据
        // redisService.get()
        //获取用户的购物车
        //如果购物车id为空则从数据库取购物车id
        if (cartId== null) {
            //查询购物车是否存在
            Long userId = jwtTokenService.getUserId();
            QueryWrapper<OmsCartDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(OmsCartDO::getMemberId, userId);
            OmsCartDO omsCartDO=omsCartRepository.getOne(queryWrapper);
            //如果不存在就创建购物车
            if(omsCartDO==null) {
                omsCartDO = new OmsCartDO();
                omsCartDO.setMemberId(userId);
                omsCartDO.setCreateTime(LocalDateTime.now());
                omsCartDO.setCardKey(OmsConstant.OMS_ORDER_CART_PREFIX + jwtTokenService.getUserId());
                omsCartRepository.save(omsCartDO);
            }
            cartId= omsCartDO.getId();
        }
        QueryWrapper<OmsCartItemDO> omsCartItemDO = new QueryWrapper<>();
        omsCartItemDO.lambda().eq(OmsCartItemDO::getCardId, cartId);
        return omsCartItemRepository.list(omsCartItemDO);
    }

    /**
     * 添加商品
     *
     * @param omsCartItemCmd
     */
    @Override
    public void insertOmsCartItem(OmsCartItemCmd omsCartItemCmd) {
        //如果购物车id为空则从数据库取购物车id
        if (omsCartItemCmd.getCardId() == null) {
            //查询购物车是否存在
            Long userId = jwtTokenService.getUserId();
            QueryWrapper<OmsCartDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(OmsCartDO::getMemberId, userId);
            OmsCartDO omsCartDO=omsCartRepository.getOne(queryWrapper);
            //如果不存在就创建购物车
            if(omsCartDO==null) {
                omsCartDO = new OmsCartDO();
                omsCartDO.setMemberId(userId);
                omsCartDO.setCreateTime(LocalDateTime.now());
                omsCartDO.setCardKey(OmsConstant.OMS_ORDER_CART_PREFIX + jwtTokenService.getUserId());
                omsCartRepository.save(omsCartDO);
            }
            omsCartItemCmd.setCardId(omsCartDO.getId());
        }
        OmsCartItemDO omsCartItemDO = BeanCopyUtils.copyProperties(omsCartItemCmd, OmsCartItemDO.class);
        omsCartItemRepository.save(omsCartItemDO);
    }

    /**
     * 根据id修改商品
     *
     * @param omsCartItemCmd
     */
    @Override
    public void updateOmsCartItem(OmsCartItemCmd omsCartItemCmd) {
        OmsCartItemDO omsCartItemDO = BeanCopyUtils.copyProperties(omsCartItemCmd, OmsCartItemDO.class);
        omsCartItemRepository.updateById(omsCartItemDO);
    }

    /**
     * 根据id删除商品
     *
     * @param id
     */
    @Override
    public void deleteOmsCartItem(Long id) {
        omsCartItemRepository.removeById(id);
    }


}
