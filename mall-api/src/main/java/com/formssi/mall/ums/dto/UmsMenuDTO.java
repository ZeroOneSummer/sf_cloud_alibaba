package com.formssi.mall.ums.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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
public class UmsMenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权(多个用逗号分隔)
     */
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 子节点
     */
    private List<UmsMenuDTO> childrenList;


}
