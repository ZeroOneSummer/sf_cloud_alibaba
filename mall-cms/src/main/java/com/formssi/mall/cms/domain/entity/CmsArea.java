package com.formssi.mall.cms.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "cms_area")
public class CmsArea implements Serializable {
    /**
     * 区域编号
     */

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
    @TableField(value = "parent_code")
    private  String parentCode;
}
