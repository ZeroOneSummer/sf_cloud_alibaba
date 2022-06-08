package com.formssi.mall.gms.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GmsSpuCmd {

    private Long id;
    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空")
    private Long catalogId;

    /**
     * 商品编号
     */
    @NotNull(message = "商品编号不能为空")
    private String spuSn;

    /**
     * 商品名称
     */
    @NotNull(message = "商品名称不能为空")
    private String name;


    /**
     * 展示图片
     */
    @NotNull(message = "展示图片不能为空")
    private String image;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
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

    private String spuImages;

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
     * sku集合
     */
    private List<GmsSkuCmd> gmsSkuCmdList;
}
