package com.formssi.mall.order.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.formssi.mall.order.infrastructure.es.annotation.EsEquals;
import com.formssi.mall.order.infrastructure.es.annotation.EsHighlight;
import com.formssi.mall.order.infrastructure.es.annotation.EsHighlightStyled;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * indexName 索引库名，个人建议以项目名称命名
 * type 类型，个人建议以实体类名称命名 注意：ES7.0之后将放弃type
 * shards 默认分区数
 * replicas 每个分区默认的备份数
 * indexStoreType 索引文件存储类型
 * refreshInterval 刷新间隔
 */
@Document(indexName="oms",shards=5,replicas=1,indexStoreType="fs",refreshInterval="-1")
@Data
public class ElasticsearchOrderVo implements Serializable {


    @Id
    private String id;

    /**
     * orderId
     */
    private Long orderId;

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
}
