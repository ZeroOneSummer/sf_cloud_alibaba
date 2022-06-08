package com.formssi.mall.pay.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 支付退款订单dto
 *
 * @author kk
 * @date 2022/05/10 17:27:26
 */
@Data
public class PayRefundOrderDto {
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
     * 交易号
     */
    @NotBlank
    private String tradeNo;
}
