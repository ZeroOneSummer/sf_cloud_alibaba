package com.formssi.mall.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author:prms
 * @create: 2022-04-19 16:12
 * @version: 1.0
 */
@Getter
@Setter
@TableName("gms_category")
public class GmsCategoryDo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("parent_id")
    private Long parentId;

    @TableField("image")
    private String image;

    @TableField("level")
    private Integer level;

    @TableField("priority")
    private Integer priority;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("option_status")
    private Integer optionStatus;

}
