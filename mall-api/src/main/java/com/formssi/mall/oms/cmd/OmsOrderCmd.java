package com.formssi.mall.oms.cmd;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 訂單cmd
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/12 9:04
 */
@Data
public class OmsOrderCmd {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 收货人
     */
    private String receiverName;

    /**
     * 省市区
     */
    private String receiverAreaName;

    /**
     * 详细地址
     */
    private String receiverAddress;

    /**
     * 手机
     */
    private String receiverPhone;

    /**
     * 总金额
     */
    private Long totalAmount;

    /**
     * 实际支付金额
     */
    private Long payAmount;

    /**
     * 运费金额
     */
    private Long freightAmount;

    /**
     * 积分抵扣金额
     */
    private Long integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    private Long couponAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 可获取的积分
     */
    private Long integration;

    /**
     * 备注
     */
    private String memo;

    /**
     * 订单状态，1：待付款，2：待发货，3：待收货，4：已完成
     */
    private Integer status;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private Integer billType;

    /**
     * 发票抬头
     */
    private String billHeader;

    /**
     * 发票内容
     */
    private String billContent;

    /**
     * 收票人电话
     */
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    private String billReceiverEmail;

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     */
    private Integer orderType;

    /**
     * 商品编号
     */
    private String spuSn;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品图片
     */
    private String spuImage;

    /**
     * 产品ID
     */
    private String skuId;

    /**
     * 价格
     */
    private Long price;

    /**
     * 数量
     */
    private Long quantity;


}
