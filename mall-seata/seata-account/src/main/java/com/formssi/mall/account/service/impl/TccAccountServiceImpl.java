package com.formssi.mall.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.account.dao.TccAccountDao;
import com.formssi.mall.account.entity.TccAccountEntity;
import com.formssi.mall.account.service.TccAccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @author chison
 * @date 2022/5/23 16:20
 * @description
 */
@Transactional
@Slf4j
@Service("tccAccountService")
public class TccAccountServiceImpl extends ServiceImpl<TccAccountDao, TccAccountEntity> implements TccAccountService {

    @Autowired
    private TccAccountDao tccAccountDao;

    /**
     * 冻结账户金额
     * @param actionContext
     * @param map
     * @return
     */
    @Override
    public boolean prepare(BusinessActionContext actionContext, HashMap<String, Object> map) {
        log.info("账户业务预备阶段{}",actionContext);
        tccAccountDao.deductAccount(Integer.parseInt(map.get("amount").toString()),
                new QueryWrapper<TccAccountEntity>()
                        .eq("user_id",Integer.parseInt(map.get("userId").toString())));
        //模拟出错
        if(Integer.parseInt(map.get("errorPhase").toString())==2){
            log.info("账户业务预备阶段业务出错",1/0);
        }
        return true;
    }

    /**
     * 解除冻结账户信息
     * @param actionContext
     * @return
     */
    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("账户业务提交阶段{}",actionContext);
        JSONObject map = JSONObject.parseObject(actionContext.getActionContext("map").toString());
        int userId = Integer.parseInt(map.get("userId").toString());
        int amount = Integer.parseInt(map.get("amount").toString());
        tccAccountDao.commitAccount(amount,new QueryWrapper<TccAccountEntity>().eq("user_id",userId));
        //模拟出错
        if(Integer.parseInt(map.get("errorPhase").toString())==4){
            log.info("账户业务提交阶段业务出错",1/0);
        }
        return true;
    }

    /**
     * 回滚冻结金额
     * @param actionContext
     * @return
     */
    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("账户业务回滚阶段{}",actionContext);
        JSONObject map = JSONObject.parseObject(actionContext.getActionContext("map").toString());
        int userId = Integer.parseInt(map.get("userId").toString());
        int amount = Integer.parseInt(map.get("amount").toString());
        tccAccountDao.rollbackAcount(amount,new QueryWrapper<TccAccountEntity>().eq("user_id",userId));
        //模拟出错
        if(Integer.parseInt(map.get("errorPhase").toString())==6){
            log.info("账户业务回滚阶段业务出错",1/0);
        }
        return true;
    }
}
