package com.formssi.mall.order.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.formssi.mall.common.cmd.MessageCmd;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.oms.dto.OmsOrderDTO;
import com.formssi.mall.oms.dto.OmsOrderItemDTO;
import com.formssi.mall.oms.dto.OmsOrderStatusCountDTO;
import com.formssi.mall.oms.query.OmsOrderItemQuery;
import com.formssi.mall.oms.query.OmsOrderQuery;
import com.formssi.mall.order.application.IOmsOrderItemService;
import com.formssi.mall.order.application.IOmsOrderService;
import com.formssi.mall.order.application.client.CommonClient;
import com.formssi.mall.order.domain.entity.OmsOrderDO;
import com.formssi.mall.order.domain.repository.IOmsOrderRepository;
import com.formssi.mall.order.domain.service.ElasticsearchOrderService;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderQueryVo;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderVo;
import com.formssi.mall.order.infrastructure.config.ExecutorConfig;
import com.formssi.mall.order.infrastructure.mq.OrderProducer;
import com.formssi.mall.order.infrastructure.util.PageComponent;
import com.google.common.util.concurrent.ListeningExecutorService;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/13 14:50
 */
@Slf4j
@Service
public class OmsOrderItemServiceImpl implements IOmsOrderItemService {
    @Resource
    IOmsOrderRepository iOmsOrderRepository;

    @Autowired
    private CommonClient commonClient;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private ElasticsearchOrderService elasticsearchOrderService;


    @Override
    public OmsOrderItemDTO queryByOmsOrderSn(String orderSn) {
        QueryWrapper<OmsOrderDO> queryWrapper=new QueryWrapper<OmsOrderDO>();
        queryWrapper.lambda().eq(OmsOrderDO::getStatus,orderSn);
        OmsOrderDO omsOrderDO=iOmsOrderRepository.getOne(queryWrapper);
        return BeanCopyUtils.copyProperties(omsOrderDO,new OmsOrderItemDTO());
    }

    @Override
    public CommonResp sendSms() {
        //准备基础数据，一个订单集合，多少个订单就发送多个条短信
        List<OmsOrderDO> omsOrderDOList = iOmsOrderRepository.queryListOmsOrderDO();
        //批量发送
        List<Pair<Long,String>> resultList = new CopyOnWriteArrayList<>();
        CompletableFuture[] completableFutures = omsOrderDOList.stream().map(omsOrderDO ->
                CompletableFuture.supplyAsync(() -> test(omsOrderDO, this::send), ExecutorConfig.executorService).whenCompleteAsync((val, e) -> {
                    if (val != null) {
                        resultList.add(val);
                    }
                }).exceptionally(e -> {
                    log.error("com.formssi.mall.order.application.impl.OmsOrderItemServiceImpl.sendSms:e{}", e);
                    return null;
                })
        ).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        System.out.println(Thread.currentThread().getName()+"主线程执行完成");
        return CommonResp.ok(resultList);
    }

    @Override
    public CommonResp sendSms2() {
        //基础数据
        List<OmsOrderDO> omsOrderDOList = iOmsOrderRepository.queryListOmsOrderDO();
        //遍历基础数据的同事去调用send方法发送短信，将返回的结果放到集合
        List<Supplier<Pair<Long,String>>> omsOrderDOSupplierList = new ArrayList<>();
        for (OmsOrderDO omsOrderDO:omsOrderDOList){
            omsOrderDOSupplierList.add(()->send(omsOrderDO));
        }
        //遍历集合，将结果放入到CompletableFuture，这里我就有一个疑问了，这里放入的是一个发送结果，
        // 那是不是代码走到这里的时候就sen方法就已经同步的执行完了，没有走线程池呢？
        List<Pair<Long,String>> resultList = new CopyOnWriteArrayList<>();
        CompletableFuture[] completableFutures = omsOrderDOSupplierList.stream().map(sup ->
                CompletableFuture.supplyAsync(sup, ExecutorConfig.executorService).whenCompleteAsync((r, e) -> {
                    if (r != null){
                        resultList.add(r);
                    }
                }).exceptionally(e -> {
                    log.error("com.formssi.mall.order.application.impl.OmsOrderItemServiceImpl.sendSms2:e{}", e);
                    return null;
                })
        ).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        System.out.println(Thread.currentThread().getName()+"主线程执行完成");
        return CommonResp.ok(resultList);
    }

    @Override
    public CommonResp download(String userId, String loginType) {
        orderProducer.send(userId,loginType);
        return CommonResp.ok();
    }

    @Override
    public CommonResp es() {
        List<ElasticsearchOrderVo> elasticsearchOrderVoList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            ElasticsearchOrderVo elasticsearchOrderVo = new ElasticsearchOrderVo();
            elasticsearchOrderVo.setId(UUID.randomUUID().toString());
            elasticsearchOrderVo.setOrderId(Long.valueOf(i));
            elasticsearchOrderVo.setOrderSn("Order_"+i);
            elasticsearchOrderVo.setReceiverName("小明"+i);
            elasticsearchOrderVo.setReceiverPhone("1201212122"+i);
            elasticsearchOrderVo.setReceiverAreaName("广东省深圳市南山区");
            elasticsearchOrderVo.setReceiverAddress("四方精创资讯大厦"+i+"楼");
            elasticsearchOrderVo.setBillReceiverEmail("12456789@qq.com");
            elasticsearchOrderVo.setOrderType(1);
            elasticsearchOrderVo.setCouponAmount(100L);
            elasticsearchOrderVo.setFreightAmount(8L);
            elasticsearchOrderVo.setIntegrationAmount(1L);
            elasticsearchOrderVo.setPayAmount(200L);
            elasticsearchOrderVo.setStatus(4);
            elasticsearchOrderVo.setBillHeader("深圳市 南山区 软基"+i + "层");
            //获取昨天的当前时间
            LocalDateTime localDateTime = LocalDateTime.now().minusDays(-i);
            Date createTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            elasticsearchOrderVo.setCreateTime(createTime);
            elasticsearchOrderVoList.add(elasticsearchOrderVo);
        }
        return CommonResp.ok(elasticsearchOrderService.saveAll(elasticsearchOrderVoList));
    }

    @Override
    public CommonResp save() {
        return CommonResp.ok(elasticsearchOrderService.saveAll(getElasticsearchOrderVoList(6,11)));
    }

    @Override
    public CommonResp findElasticsearchOrderVoList(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo) {
        return CommonResp.ok(elasticsearchOrderService.findElasticsearchOrderVoList(elasticsearchOrderQueryVo));
    }

    @Override
    public CommonResp findOrderVoListBySearchAfter(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo) {
        return CommonResp.ok(elasticsearchOrderService.findOrderVoListBySearchAfter(elasticsearchOrderQueryVo));
    }


    private Pair<Long,String> send(OmsOrderDO omsOrderDO){
        //调用短信服务发送短信
/*        MessageCmd messageCmd = new MessageCmd();
        messageCmd.setTos(Arrays.asList(omsOrderDO.getBillReceiverEmail()));
        messageCmd.setSubject(omsOrderDO.getOrderSn());
        messageCmd.setText(omsOrderDO.getReceiverName());
        CommonResp smsCaptcha = commonClient.sendBatchSMS(messageCmd);*/
        //打印当前线程的名称
        System.out.println("我是线程："+Thread.currentThread().getName());
        return new Pair(omsOrderDO.getId(),Thread.currentThread().getName());
    }


    private <T,R> R test(T t,Function<T,R> function){
        return function.apply(t);
    }


    /**
     *
     * @param sart
     * @param end
     * @return
     */
    private List<ElasticsearchOrderVo> getElasticsearchOrderVoList(int sart,int end){
        List<ElasticsearchOrderVo> elasticsearchOrderVoList = new ArrayList<>();
        for (int i = sart; i <end; i++) {
            ElasticsearchOrderVo elasticsearchOrderVo = new ElasticsearchOrderVo();
            elasticsearchOrderVo.setId(UUID.randomUUID().toString());
            elasticsearchOrderVo.setOrderId(Long.valueOf(i));
            elasticsearchOrderVo.setOrderSn("Order_"+i);
            elasticsearchOrderVo.setReceiverName("大白"+i+"号");
            elasticsearchOrderVo.setReceiverPhone("13856569999");
            elasticsearchOrderVo.setReceiverAreaName("广东省深圳市福田区");
            elasticsearchOrderVo.setReceiverAddress("壹方中心 -楼"+i+"楼");
            elasticsearchOrderVo.setBillReceiverEmail("366666666@qq.com");
            elasticsearchOrderVo.setOrderType(1);
            elasticsearchOrderVo.setCouponAmount(100L);
            elasticsearchOrderVo.setFreightAmount(8L);
            elasticsearchOrderVo.setIntegrationAmount(1L);
            elasticsearchOrderVo.setPayAmount(100L);
            elasticsearchOrderVo.setStatus(4);
            elasticsearchOrderVo.setBillHeader("深圳市 福田区 中国建设银行");
            //获取昨天的当前时间
            LocalDateTime localDateTime = LocalDateTime.now().minusDays(-i);
            Date createTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            elasticsearchOrderVo.setCreateTime(createTime);
            elasticsearchOrderVoList.add(elasticsearchOrderVo);
        }
        return elasticsearchOrderVoList;
    }




}
