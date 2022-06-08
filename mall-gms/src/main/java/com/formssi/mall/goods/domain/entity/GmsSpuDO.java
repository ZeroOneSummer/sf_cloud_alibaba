package com.formssi.mall.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author hudemin
 * @since 2022-04-14 20:01:14
 */

@Getter
@Setter
@TableName("gms_spu")
public class GmsSpuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类ID
     */
    @TableField(value = "catalog_id")
    private Long catalogId;

    /**
     * 商品编号
     */
    @TableField("spu_sn")
    private String spuSn;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 展示图片
     */
    @TableField("image")
    private String image;

    /**
     * 价格
     */
    @TableField("price")
    private Double price;

    /**
     * 市场价
     */
    @TableField("market_price")
    private Double marketPrice;

    /**
     * 总销量
     */
    @TableField("total_sales")
    private Long totalSales;

    /**
     * 总评分
     */
    @TableField("total_score")
    private Float totalScore;

    /**
     * 总评论
     */
    @TableField("total_comment")
    private Long totalComment;

    /**
     * 优先级
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 商品图片
     */
    @TableField("spu_images")
    private String spuImages;

    /**
     * 虚拟库存
     */
    @TableField("virtual_stock")
    private Integer virtualStock;

    /**
     * 可用积分
     */
    @TableField("enable_integral")
    private Integer enableIntegral;

    /**
     * 商品状态 1：上架，2：下架
     */
    @TableField("spu_status")
    private Integer spuStatus;

    /**
     * 审批状态 1: 通过，2拒绝
     */
    @TableField("approval_status")
    private Integer approvalStatus;

    /**
     * 拒绝原因
     */
    @TableField("refuse_reason")
    private String refuseReason;

    /**
     * 关键字
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 操作状态(0、1、2)分别代表D、I、U
     */
    @TableField("option_status")
    private Integer optionStatus;

    /**
     * 介绍
     */
    @TableField("introduce")
    private String introduce;

}
