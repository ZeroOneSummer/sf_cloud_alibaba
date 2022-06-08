package com.formssi.mall.pay.application;

import com.formssi.mall.pay.domain.dto.PayOrderDto;
import com.formssi.mall.pay.domain.dto.PayRefundOrderDto;

/**
 * 支付服务
 *
 * @author kk
 * @date 2022/05/09 16:20:34
 */
public interface PayService {

    /**
     * 下单
     *
     * @param dto dto
     * @return {@link String}
     */
    String placeOrder(PayOrderDto dto);


    /**
     * 查询订单
     *
     * @param outTradeNo 订单号
     * @return {@link String}
     */
    String queryOrder(String outTradeNo);

    /**
     * 退款
     *
     * @param dto dto
     * @return {@link String}
     */
    String refund(PayRefundOrderDto dto);

    /**
     * 查询退款订单
     *
     * @param outTradeNo 订单号
     * @return {@link String}
     */
    String queryRefundOrder(String outTradeNo);

    /**
     * 关闭订单
     *
     * @param outTradeNo 订单号
     * @return {@link String}
     */
    String closeOrder(String outTradeNo);

    /**
     * APP支付
     *
     * @param dto dto
     * @return {@link String}
     */
    String appPay(PayOrderDto dto);

    /**
     * wap支付
     *
     * @param dto dto
     * @return {@link Object}
     */
    Object wapPay(PayOrderDto dto);
}