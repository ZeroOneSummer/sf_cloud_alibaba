package com.formssi.mall.pay.application.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.formssi.mall.pay.application.PayService;
import com.formssi.mall.pay.domain.dto.PayOrderDto;
import com.formssi.mall.pay.domain.dto.PayRefundOrderDto;
import com.formssi.mall.pay.infrastructure.annotation.PayType;
import com.formssi.mall.pay.infrastructure.config.AlipayProperties;
import com.formssi.mall.pay.infrastructure.enumeration.PayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付宝服务impl
 *
 * @author kk
 * @date 2022/05/09 16:21:43
 */
@Slf4j
@Service
@PayType(value = PayTypeEnum.ALIPAY)
public class AlipayServiceImpl implements PayService {
    @Resource
    private AlipayProperties properties;

    @Override
    public String placeOrder(PayOrderDto dto) {
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getApplicationAlipayPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
        // 设置请求参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(properties.getReturnUrl());
        request.setNotifyUrl(properties.getNotifyUrl());
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(dto.getOutTradeNo());
        model.setTotalAmount(dto.getTotalAmount());
        model.setSubject(dto.getSubject());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        // 花呗
        if (StringUtils.isNotBlank(dto.getHbFqNum()) && StringUtils.isNotBlank(dto.getHbFqSellerPercent())) {
            ExtendParams extendParams = new ExtendParams();
            extendParams.setHbFqNum(dto.getHbFqNum());
            extendParams.setHbFqSellerPercent(dto.getHbFqSellerPercent());
            model.setExtendParams(extendParams);
        }
        request.setBizModel(model);
        // 请求
        AlipayTradePagePayResponse response;
        try {
            response = alipayClient.pageExecute(request, "GET");
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("调用成功~~" + response.isSuccess());
        } else {
            log.info("调用失败~~" + response.isSuccess());
        }
        String result = response.getBody();
        // 以下写自己的订单代码
        log.info("以下写自己的订单代码~");
        // 输出
        log.info(result);
        return result;
    }

    @Override
    public String queryOrder(String outTradeNo) {
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getApplicationAlipayPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", outTradeNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("调用成功~");
        } else {
            log.info("调用失败~");
        }
        String result = response.getBody();
        log.info("以下写自己的订单代码 ~");
        log.info(result);
        return result;
    }

    @Override
    public String refund(PayRefundOrderDto dto) {
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getApplicationAlipayPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", dto.getOutTradeNo());
        bizContent.put("refund_amount", dto.getTotalAmount());
        bizContent.put("trade_no", dto.getTradeNo());
        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response;
        try {
            response = alipayClient.execute(request, "GET");
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("调用成功~~" + response.isSuccess());
        } else {
            log.info("调用失败~~" + response.isSuccess());
        }
        String result = response.getBody();
        log.info(result);
        log.info("再插库一下~");
        return result;
    }

    @Override
    public String queryRefundOrder(String outTradeNo) {
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getApplicationAlipayPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", outTradeNo);
        bizContent.put("out_request_no", outTradeNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeFastpayRefundQueryResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("调用成功~");
        } else {
            log.info("调用失败~");
        }
        String result = response.getBody();
        log.info("以下写自己的订单代码 ~");
        log.info(result);
        return result;
    }

    @Override
    public String closeOrder(String outTradeNo) {
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getApplicationAlipayPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", outTradeNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeCloseResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("调用成功~");
        } else {
            log.info("调用失败~");
        }
        String result = response.getBody();
        log.info("关闭订单~");
        log.info(result);
        return result;
    }

    @Override
    public String appPay(PayOrderDto dto) {
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getApplicationAlipayPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(properties.getNotifyUrl());
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(dto.getOutTradeNo());
        model.setTotalAmount(dto.getTotalAmount());
        model.setSubject(dto.getSubject());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        AlipayTradeAppPayResponse response;
        try {
            response = alipayClient.sdkExecute(request);
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("调用成功~");
        } else {
            log.info("调用失败~");
        }
        String result = response.getBody();
        log.info("app支付~");
        log.info(result);
        return result;
    }

    @Override
    public Object wapPay(PayOrderDto dto) {
        AlipayClient alipayClient = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getApplicationAlipayPrivateKey(), properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(properties.getReturnUrl());
        request.setNotifyUrl(properties.getNotifyUrl());
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(dto.getOutTradeNo());
        model.setTotalAmount(dto.getTotalAmount());
        model.setSubject(dto.getSubject());
        model.setProductCode("QUICK_WAP_WAY");
        request.setBizModel(model);
        AlipayTradeWapPayResponse response;
        try {
            response = alipayClient.pageExecute(request, "GET");
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("调用成功~");
        } else {
            log.info("调用失败~");
        }
        String result = response.getBody();
        log.info("app支付~");
        log.info(result);
        return result;
    }
}