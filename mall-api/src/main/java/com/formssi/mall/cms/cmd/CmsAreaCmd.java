package com.formssi.mall.cms.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsAreaCmd {
    /**
     * 区域编号
     */
    @NotNull(message = "区域编号不能为空")
    private String code;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 区域名称
     */
    private String name;
    /**
     * 父级编号
     */

    private  String parentCode;
}
