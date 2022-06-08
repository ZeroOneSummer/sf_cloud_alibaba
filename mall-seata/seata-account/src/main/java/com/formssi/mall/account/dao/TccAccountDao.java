package com.formssi.mall.account.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.formssi.mall.account.entity.TccAccountEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author chison
 * @date 2022/5/24 10:02
 * @description
 */
public interface TccAccountDao extends BaseMapper<TccAccountEntity> {

    void deductAccount(@Param("amount") Integer amount,
                       @Param(Constants.WRAPPER) QueryWrapper<TccAccountEntity> queryWrapper);

    void commitAccount(@Param("amount") int amount,
                       @Param(Constants.WRAPPER) QueryWrapper<TccAccountEntity> queryWrapper);

    void rollbackAcount(@Param("amount") int amount,
                        @Param(Constants.WRAPPER) QueryWrapper<TccAccountEntity> queryWrapper);
}
