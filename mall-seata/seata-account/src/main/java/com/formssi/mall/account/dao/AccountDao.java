package com.formssi.mall.account.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.formssi.mall.account.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author chison
 * @email chison@gmail.com
 * @date 2022-05-16 14:00:13
 */
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {

    void deductAccount(@Param("amount") Integer amount, @Param(Constants.WRAPPER) QueryWrapper<AccountEntity> queryWrapper);
}
