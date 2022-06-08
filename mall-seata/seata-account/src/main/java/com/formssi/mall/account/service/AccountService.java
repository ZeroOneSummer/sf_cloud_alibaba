package com.formssi.mall.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.formssi.mall.account.entity.AccountEntity;

import java.util.HashMap;

/**
 * 
 *
 * @author chison
 * @email chison@gmail.com
 * @date 2022-05-16 14:00:13
 */
public interface AccountService extends IService<AccountEntity> {

    void deductAccount(HashMap<String, Object> map);

    void tccDeductAccount(HashMap<String, Object> map);
}

