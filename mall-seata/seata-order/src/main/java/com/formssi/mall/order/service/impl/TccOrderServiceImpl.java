package com.formssi.mall.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.order.dao.TccOrderDao;
import com.formssi.mall.order.entity.TccOrderEntity;
import com.formssi.mall.order.service.TccOrderService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author chison
 * @date 2022/5/23 16:18
 * @description
 */
@Transactional
@Slf4j
@Service("tccOrderService")
public class TccOrderServiceImpl extends ServiceImpl<TccOrderDao, TccOrderEntity> implements TccOrderService {

    @Autowired
    private TccOrderDao tccOrderDao;

    /**
     * 生成中间状态订单
     * @param actionContext
     * @param tccOrderEntity
     * @return
     */
    @Override
    public boolean prepare(BusinessActionContext actionContext, TccOrderEntity tccOrderEntity) {
        log.info("订单业务预备阶段{}",actionContext);
        tccOrderEntity.setOrderStatus(0);
        tccOrderDao.insert(tccOrderEntity);
        //模拟出错
        if(tccOrderEntity.getErrorPhase()==1){
            log.info("订单业务预备阶段出错",1/0);
        }
        return true;
    }

    /**
     * 更新中间状态订单
     * @param actionContext
     * @return
     */
    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("订单业务提交阶段{}",actionContext);
        TccOrderEntity entity = new TccOrderEntity();
        JSONObject order = JSONObject.parseObject(actionContext.getActionContext("mapper/order").toString());
        entity.setOrderStatus(1);
        entity.setOrderNo(order.get("orderNo").toString());
        tccOrderDao.update(entity,new QueryWrapper<TccOrderEntity>().eq("order_no",entity.getOrderNo()));
        log.info("参数{}",actionContext.getActionContext().get("mapper/order"));
        //模拟出错
        if(Integer.parseInt(order.get("errorPhase").toString())==3){
            try {
                System.out.println(1/0);
            }catch (Exception ex){
                log.error("订单业务提交阶段业务出错");
//                try {
//                    TransactionManager manager = TransactionManagerHolder.get();
//                    GlobalStatus status = manager.rollback(actionContext.getXid());
//                    log.info("回滚状态{}",status);
//                } catch (TransactionException e) {
//                    e.printStackTrace();
//                }
//                GlobalTransactionContext.reload(actionContext.getXid()).rollback();
            }
        }
        return true;
    }

    /**
     * 删除中间态订单
     * @param actionContext
     * @return
     */
    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("订单业务回滚阶段{}",actionContext);
        JSONObject order = JSONObject.parseObject(actionContext.getActionContext("mapper/order").toString());
        TccOrderEntity entity = new TccOrderEntity();
        entity.setOrderStatus(2);
        tccOrderDao.update(entity,new QueryWrapper<TccOrderEntity>().eq("order_no",order.get("orderNo")));
//        tccOrderDao.delete(new QueryWrapper<TccOrderEntity>().eq("order_no",order.get("orderNo")));
        //模拟出错
        if(Integer.parseInt(order.get("errorPhase").toString())==5){
            log.info("订单业务回滚阶段业务出错",1/0);
        }
        return true;
    }
}
