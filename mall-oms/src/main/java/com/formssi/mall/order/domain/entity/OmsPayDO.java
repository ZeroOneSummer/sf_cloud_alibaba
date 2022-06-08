package com.formssi.mall.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 訂單支付
 */
@Data
@TableName("oms_pay")
public class OmsPayDO implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    /**
     * 收款账户
     */
    @TableField("receiver_account")
    private String receiverAccount;

    /**
     * 付款账户
     */
    @TableField("payer_account")
    private String payerAccount;

    /**
     * 付款金额
     */
    @TableField("pay_amount")
    private Long payAmount;

    /**
     * 支付手续费
     */
    @TableField("fee")
    private Long fee;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

    /**
     * 支付方式 0->未支付，1支付宝，2微信，3银行卡支付
     */
    @TableField("pay_type")
    private Integer payType;

    private static final long serialVersionUID = 1L;


}