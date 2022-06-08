package com.formssi.mall.order.interfaces;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.entity.resp.ErrorBody;
import com.formssi.mall.common.entity.resp.RespCode;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.oms.dto.OmsOrderItemDTO;
import com.formssi.mall.order.application.AbstractOrderService;
import com.formssi.mall.order.application.IOmsOrderItemService;
import com.formssi.mall.order.application.OrderService;
import com.formssi.mall.order.domain.entity.OmsPayDO;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderQueryVo;
import com.formssi.mall.order.infrastructure.config.OrderTypeServiceMap;
import com.formssi.mall.order.infrastructure.enumeration.OmsOrderTypeEnum;
import com.formssi.mall.order.infrastructure.factory.PayOrderFactory;
import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/14 9:29
 */
@RestController
@RequestMapping("/oms")
public class OmsOrderItemController {
    @Autowired
    IOmsOrderItemService iomsOrderItemService;

    @RequestMapping("queryByOmsOrderSn")
    public CommonResp<OmsOrderItemDTO> queryByOmsOrderSn(String orderSn){
        return CommonResp.ok(iomsOrderItemService.queryByOmsOrderSn(orderSn));
    }


    @PostMapping("/save/order")
    public CommonResp saveOrder(@RequestParam("code") String code, @RequestBody OmsOrderCmd omsOrderCmd){
        OrderService orderService = OrderTypeServiceMap.SERVICE_HAND_MAP.get(OmsOrderTypeEnum.queryOmsOrderTypeEnumByCode(code));
        if (orderService != null){
            return CommonResp.ok(orderService.saveOrder(()->omsOrderCmd));
        }
        return CommonResp.error(RespCode.BAD_REQUEST,ErrorBody.build(new Throwable("非法订单类型")));
    }


    @PostMapping("/order/pay")
    public CommonResp saveOrder(@RequestParam("type") Integer type){
        AbstractOrderService orderService = PayOrderFactory.getPayOrderFactory(type);
        if (orderService != null){
            return CommonResp.ok(orderService.payOrder());
        }
        return CommonResp.error(RespCode.BAD_REQUEST,ErrorBody.build(new Throwable("非法支付类型")));
    }



    @PostMapping("/sen/sms")
    public CommonResp sendSms(){
        return iomsOrderItemService.sendSms();
    }


    @PostMapping("/sen/sms2")
    public CommonResp sendSms2(){
        return iomsOrderItemService.sendSms2();
    }


    @GetMapping("/webSocket/download")
    public CommonResp download(@PathParam("userId") String userId,
                               @PathParam("loginType") String loginType){
        Lock lock = new ReentrantLock();
        lock.lock();
        Map<String,Object> map = new ConcurrentHashMap();
        map.put("a","a");
        String a = "a";
        StringBuffer buffer = new StringBuffer("buffer");
        buffer.append("bbb");
        StringBuilder builder = new StringBuilder("builder");
        builder.append("ccc");
        List<String> list = new ArrayList<>();
        List<String> strings = Collections.synchronizedList(list);
        strings.listIterator();
        Vector vector = new Vector();
        vector.add("1");
        List<String> list1 = new CopyOnWriteArrayList<>();
        list1.add("1");
        return iomsOrderItemService.download(userId,loginType);
    }




    @GetMapping("/es")
    public CommonResp es(){
        return iomsOrderItemService.es();
    }

    @GetMapping("/save")
    public CommonResp save(){
        return iomsOrderItemService.save();
    }

    @PostMapping("/page")
    public CommonResp findElasticsearchOrderVoList(@RequestBody ElasticsearchOrderQueryVo elasticsearchOrderQueryVo){
        return iomsOrderItemService.findElasticsearchOrderVoList(elasticsearchOrderQueryVo);
    }


    @PostMapping("/search/after")
    public CommonResp findOrderVoListBySearchAfter(@RequestBody ElasticsearchOrderQueryVo elasticsearchOrderQueryVo){
        return iomsOrderItemService.findOrderVoListBySearchAfter(elasticsearchOrderQueryVo);
    }
}
