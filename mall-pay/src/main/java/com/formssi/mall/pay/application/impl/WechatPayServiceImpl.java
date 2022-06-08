package com.formssi.mall.pay.application.impl;

import com.alibaba.fastjson.JSONObject;
import com.formssi.mall.pay.application.PayService;
import com.formssi.mall.pay.domain.dto.PayOrderDto;
import com.formssi.mall.pay.domain.dto.PayRefundOrderDto;
import com.formssi.mall.pay.infrastructure.annotation.PayType;
import com.formssi.mall.pay.infrastructure.config.WechatPropertiesV3;
import com.formssi.mall.pay.infrastructure.enumeration.PayTypeEnum;
import com.formssi.mall.pay.infrastructure.utils.HttpUtils;
import com.formssi.mall.pay.infrastructure.utils.WechatPayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 微信支付服务impl
 *
 * @author kk
 * @date 2022/05/10 11:36:58
 */
@Slf4j
@Service
@PayType(value = PayTypeEnum.WECHAT)
public class WechatPayServiceImpl implements PayService {
    @Resource
    private WechatPropertiesV3 properties;

    @Override
    public String placeOrder(PayOrderDto dto) {
        JSONObject payObj = new JSONObject();
        payObj.put("mchid", properties.getMchId());
        payObj.put("out_trade_no", dto.getOutTradeNo());
        payObj.put("appid", properties.getWxPayAppid());
        payObj.put("description", dto.getSubject());
        payObj.put("notify_url", properties.getCallbackUrl());
        JSONObject amountObj = new JSONObject();
        amountObj.put("total", dto.getTotalAmount());
        amountObj.put("currency", "CNY");
        payObj.put("amount", amountObj);
        //  ...等参数
        String body = payObj.toJSONString();
        String url = String.format(WechatPayConstant.PLACE_ORDER, "native");
        return HttpUtils.doPost(body, url);
    }

    @Override
    public String queryOrder(String outTradeNo) {
        String url = String.format(WechatPayConstant.NATIVE_QUERY, outTradeNo, properties.getMchId());
        return HttpUtils.doGet(url);
    }

    @Override
    public String refund(PayRefundOrderDto dto) {
        // 请求body参数
        JSONObject refundObj = new JSONObject();
        //订单号
        refundObj.put("out_trade_no", dto.getOutTradeNo());
        refundObj.put("out_refund_no", dto.getOutTradeNo());
        refundObj.put("notify_url", properties.getCallbackUrl());
        JSONObject amountObj = new JSONObject();
        //退款金额
        amountObj.put("refund", dto.getTotalAmount());
        //实际支付的总金额
        amountObj.put("total", dto.getTotalAmount());
        amountObj.put("currency", "CNY");
        refundObj.put("amount", amountObj);
        String body = refundObj.toJSONString();
        return HttpUtils.doPost(body, WechatPayConstant.NATIVE_REFUND_ORDER);
    }

    @Override
    public String queryRefundOrder(String outTradeNo) {
        String url = String.format(WechatPayConstant.NATIVE_REFUND_QUERY, outTradeNo);
        return HttpUtils.doGet(url);
    }

    @Override
    public String closeOrder(String outTradeNo) {
        String url = String.format(WechatPayConstant.NATIVE_CLOSE_ORDER, outTradeNo);
        JSONObject payObj = new JSONObject();
        payObj.put("mchid", properties.getMchId());
        String body = payObj.toJSONString();
        return HttpUtils.doPost(body, url);
    }

    @Override
    public String appPay(PayOrderDto dto) {
        JSONObject payObj = new JSONObject();
        payObj.put("appid", properties.getWxPayAppid());
        payObj.put("mchid", properties.getMchId());
        payObj.put("description", dto.getSubject());
        payObj.put("out_trade_no", dto.getOutTradeNo());
        payObj.put("notify_url", properties.getCallbackUrl());
        JSONObject amountObj = new JSONObject();
        amountObj.put("total", dto.getTotalAmount());
        amountObj.put("currency", "CNY");
        payObj.put("amount", amountObj);
        //  ...等参数
        String body = payObj.toJSONString();
        String url = String.format(WechatPayConstant.PLACE_ORDER, "app");
        return HttpUtils.doPost(body, url);
    }

    @Override
    public Object wapPay(PayOrderDto dto) {
        return null;
    }
}
