package com.formssi.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.order.dao.OrderDao;
import com.formssi.mall.order.entity.OrderEntity;
import com.formssi.mall.order.entity.TccOrderEntity;
import com.formssi.mall.order.feign.AccountFeignService;
import com.formssi.mall.order.service.OrderService;
import com.formssi.mall.order.service.TccOrderService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AccountFeignService accountFeignService;

    @Autowired
    private TccOrderService tccOrderService;


    /**
     * 主分支TM
     * @param orderEntity
     */
    @GlobalTransactional
    @Override
    public void shopping(OrderEntity orderEntity) {
        log.info("开启第一个事务");
        createOrder(orderEntity);
        log.info("开启第二个事务");
        accountFeignService.deduct(orderEntity.getUserId(),orderEntity.getAmount(),orderEntity.getErrorPhase());
        //模拟出错
        if(orderEntity.getErrorPhase()==3){
            log.info("RM都提交事务之后出错",1/0);
        }
        log.info("事务结束");
    }

    /**
     * 子分支RM
     * 创建订单
     * @param orderEntity
     */
    @Transactional
    @Override
    public void createOrder(OrderEntity orderEntity) {
        orderEntity.setOrderNo(UUID.randomUUID().toString());
        orderDao.insert(orderEntity);
        //模拟出错
        if(orderEntity.getErrorPhase()==1){
            log.info("订单业务出错",1/0);
        }
    }

    /**
     * 主分支TM
     * @param orderEntity
     */
    @GlobalTransactional
    @Override
    public void tccShopping(TccOrderEntity orderEntity) {
        log.info("开启第一个事务");
        orderEntity.setOrderNo(UUID.randomUUID().toString().replace("-",""));
        boolean prepare = tccOrderService.prepare(new BusinessActionContext(), orderEntity);
        if(prepare){
            log.info("开启第二个事务");
            accountFeignService.tccDeduct(orderEntity.getUserId(),orderEntity.getAmount(),orderEntity.getErrorPhase());
        }
    }


}