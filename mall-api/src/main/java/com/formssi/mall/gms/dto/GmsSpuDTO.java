package com.formssi.mall.gms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品dto
 * </p>
 *
 *@author hudemin
 *@since 2022-04-18 20:01:13
 */
@Getter
@Setter
public class GmsSpuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * spu id
     */
    private Long id;
    /**
     * 分类ID
     */
    private Long catalogId;

    /**
     * 商品编号
     */

    private String spuSn;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private Double price;

    /**
     * 市场价
     */
    private Double marketPrice;

    /**
     * 总销量
     */
    private Long totalSales;

    /**
     * 总评分
     */
    private Float totalScore;

    /**
     * 总评论
     */

    private Long totalComment;

    /**
     * 优先级
     */

    private Integer priority;

    /**
     * 商品图片
     */

    private String image;

    /**
     * 虚拟库存
     */

    private Integer virtualStock;

    /**
     * 可用积分
     */
    private Integer enableIntegral;

    /**
     * 商品状态 1：上架，2：下架
     */
    private Integer spuStatus;

    /**
     * 审批状态 1: 通过，2拒绝
     */
    private Integer approvalStatus;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 操作状态(0、1、2)分别代表D、I、U
     */
    private Integer optionStatus;

    /**
     * 介绍
     */
    private String introduce;
}