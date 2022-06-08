package com.formssi.mall.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 后台菜单
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
@Getter
@Setter
@TableName("ums_menu")
public class UmsMenuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 菜单URL
     */
    @TableField("url")
    private String url;

    /**
     * 授权(多个用逗号分隔)
     */
    @TableField("perms")
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @TableField("`type`")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("orders")
    private Integer orders;

}
