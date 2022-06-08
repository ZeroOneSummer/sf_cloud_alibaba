package com.formssi.mall.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 *訂單
 */
@Getter
@Setter
@TableName("oms_order")
public class OmsOrderDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    /**
     * 收货人
     */
    @TableField("receiver_name")
    private String receiverName;

    /**
     * 省市区
     */
    @TableField("receiver_area_name")
    private String receiverAreaName;

    /**
     * 详细地址
     */
    @TableField("receiver_address")
    private String receiverAddress;

    /**
     * 手机
     */
    @TableField("receiver_phone")
    private String receiverPhone;

    /**
     * 总金额
     */
    @TableField("total_amount")
    private Long totalAmount;

    /**
     * 实际支付金额
     */
    @TableField("pay_amount")
    private Long payAmount;

    /**
     * 运费金额
     */
    @TableField("freight_amount")
    private Long freightAmount;

    /**
     * 积分抵扣金额
     */
    @TableField("integration_amount")
    private Long integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    @TableField("coupon_amount")
    private Long couponAmount;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 可获取的积分
     */
    @TableField("integration")
    private Long integration;

    /**
     * 备注
     */
    @TableField("memo")
    private String memo;

    /**
     * 订单状态，1：待付款，2：待发货，3：待收货，4：已完成
     */
    @TableField("status")
    private Integer status;

    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    @TableField("bill_type")
    private Integer billType;

    /**
     * 发票抬头
     */
    @TableField("bill_header")
    private String billHeader;

    /**
     * 发票内容
     */
    @TableField("bill_content")
    private String billContent;

    /**
     * 收票人电话
     */
    @TableField("bill_receiver_phone")
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    @TableField("bill_receiver_email")
    private String billReceiverEmail;

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     */
    @TableField("order_type")
    private Integer orderType;

    private static final long serialVersionUID = 1L;


}