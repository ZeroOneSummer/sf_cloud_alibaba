package com.formssi.mall.flash.dto;

import com.formssi.mall.flash.cmd.FlashItemCmd;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class FlashActDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 审核状态 0未通过 1通过
     */
    private Integer approvalStatus;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

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
    private List<FlashItemCmd> flashItemCmdList;



}