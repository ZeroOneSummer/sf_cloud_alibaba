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
        //?????????????????????????????????????????????????????????????????????????????????
        List<OmsOrderDO> omsOrderDOList = iOmsOrderRepository.queryListOmsOrderDO();
        //????????????
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
        System.out.println(Thread.currentThread().getName()+"?????????????????????");
        return CommonResp.ok(resultList);
    }

    @Override
    public CommonResp sendSms2() {
        //????????????
        List<OmsOrderDO> omsOrderDOList = iOmsOrderRepository.queryListOmsOrderDO();
        //????????????????????????????????????send???????????????????????????????????????????????????
        List<Supplier<Pair<Long,String>>> omsOrderDOSupplierList = new ArrayList<>();
        for (OmsOrderDO omsOrderDO:omsOrderDOList){
            omsOrderDOSupplierList.add(()->send(omsOrderDO));
        }
        //?????????????????????????????????CompletableFuture???????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????sen???????????????????????????????????????????????????????????????
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
        System.out.println(Thread.currentThread().getName()+"?????????????????????");
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
            elasticsearchOrderVo.setReceiverName("??????"+i);
            elasticsearchOrderVo.setReceiverPhone("1201212122"+i);
            elasticsearchOrderVo.setReceiverAreaName("???????????????????????????");
            elasticsearchOrderVo.setReceiverAddress("????????????????????????"+i+"???");
            elasticsearchOrderVo.setBillReceiverEmail("12456789@qq.com");
            elasticsearchOrderVo.setOrderType(1);
            elasticsearchOrderVo.setCouponAmount(100L);
            elasticsearchOrderVo.setFreightAmount(8L);
            elasticsearchOrderVo.setIntegrationAmount(1L);
            elasticsearchOrderVo.setPayAmount(200L);
            elasticsearchOrderVo.setStatus(4);
            elasticsearchOrderVo.setBillHeader("????????? ????????? ??????"+i + "???");
            //???????????????????????????
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
        //??????????????????????????????
/*        MessageCmd messageCmd = new MessageCmd();
        messageCmd.setTos(Arrays.asList(omsOrderDO.getBillReceiverEmail()));
        messageCmd.setSubject(omsOrderDO.getOrderSn());
        messageCmd.setText(omsOrderDO.getReceiverName());
        CommonResp smsCaptcha = commonClient.sendBatchSMS(messageCmd);*/
        //???????????????????????????
        System.out.println("???????????????"+Thread.currentThread().getName());
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
            elasticsearchOrderVo.setReceiverName("??????"+i+"???");
            elasticsearchOrderVo.setReceiverPhone("13856569999");
            elasticsearchOrderVo.setReceiverAreaName("???????????????????????????");
            elasticsearchOrderVo.setReceiverAddress("???????????? -???"+i+"???");
            elasticsearchOrderVo.setBillReceiverEmail("366666666@qq.com");
            elasticsearchOrderVo.setOrderType(1);
            elasticsearchOrderVo.setCouponAmount(100L);
            elasticsearchOrderVo.setFreightAmount(8L);
            elasticsearchOrderVo.setIntegrationAmount(1L);
            elasticsearchOrderVo.setPayAmount(100L);
            elasticsearchOrderVo.setStatus(4);
            elasticsearchOrderVo.setBillHeader("????????? ????????? ??????????????????");
            //???????????????????????????
            LocalDateTime localDateTime = LocalDateTime.now().minusDays(-i);
            Date createTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            elasticsearchOrderVo.setCreateTime(createTime);
            elasticsearchOrderVoList.add(elasticsearchOrderVo);
        }
        return elasticsearchOrderVoList;
    }




}
