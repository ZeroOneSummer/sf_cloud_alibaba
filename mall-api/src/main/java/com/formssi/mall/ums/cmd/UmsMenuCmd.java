package com.formssi.mall.ums.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsMenuCmd {

    private Long id;

    private Long parentId;

    @NotNull(message = "菜单名称不能为空")
    private String name;

    @NotNull(message = "路由地址不能为空")
    private String url;

    private String perms;

    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    private String icon;

    private Integer orders;

}
