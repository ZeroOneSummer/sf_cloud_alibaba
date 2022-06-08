package com.formssi.mall.pay.infrastructure.utils;

/**
 * 微信支付常量
 *
 * @author user
 */
public class WechatPayConstant {

    /**
     * 微信支付主机地址
     */
    public static final String HOST = "https://api.mch.weixin.qq.com";
    /**
     * 获取更新证书地址
     */
    public static final String CERTIFICATES = "https://api.mch.weixin.qq.com/v3/certificates";

    /**
     * 下单
     */
    public static final String PLACE_ORDER = HOST + "/v3/pay/transactions/native";

    /**
     * Native订单状态查询, 根据商户订单号查询
     */
    public static final String NATIVE_QUERY = HOST + "/v3/pay/transactions/out-trade-no/%s?mchid=%s";

    /**
     * 关闭订单接口
     */
    public static final String NATIVE_CLOSE_ORDER = HOST + "/v3/pay/transactions/out-trade-no/%s/close";

    /**
     * 申请退款接口
     */
    public static final String NATIVE_REFUND_ORDER = HOST + "/v3/refund/domestic/refunds";

    /**
     * 退款状态查询接口
     */
    public static final String NATIVE_REFUND_QUERY = HOST + "/v3/refund/domestic/refunds/%s";
}
