package com.formssi.mall.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.account.dao.AccountDao;
import com.formssi.mall.account.entity.AccountEntity;
import com.formssi.mall.account.service.AccountService;
import com.formssi.mall.account.service.TccAccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TccAccountService tccAccountService;

    /**
     * AT模式更新账户余额业务
     * @param map
     */
    @Transactional
    @Override
    public void deductAccount(HashMap<String, Object> map){
        accountDao.deductAccount((Integer) map.get("amount"),
                new QueryWrapper<AccountEntity>().eq("user_id",map.get("userId")));
        //模拟出错
        if(Integer.parseInt(map.get("errorPhase").toString())==2){
            log.info("账户业务出错",1/0);
        }
    }

    /**
     * TCC模式下的更新账户余额业务
     * @param map
     */
    @Override
    public void tccDeductAccount(HashMap<String, Object> map) {
        tccAccountService.prepare(new BusinessActionContext(),map);
    }

}