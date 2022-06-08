package com.formssi.mall.pay.domain.dto;

import com.alipay.api.internal.mapping.ApiField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 支付宝订单
 *
 * @author kk
 * @date 2022/05/10 16:02:59
 */
@Data
public class PayOrderDto {
    /**
     * PayTypeEnum
     */
    @NotBlank
    private String code;
    /**
     * 订单号
     */
    @NotBlank
    private String outTradeNo;
    /**
     * 总金额
     */
    @NotBlank
    private String totalAmount;
    /**
     * 主题,描述
     */
    @NotBlank
    private String subject;
    /**
     * 花呗分期的分期数
     */
    private String hbFqNum;
    /**
     * 花呗分期承担的手续费比例的百分值，传入100代表100%
     */
    private String hbFqSellerPercent;
}
