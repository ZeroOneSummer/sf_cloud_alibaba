package com.formssi.mall.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("gms_spu_spec")
public class GmsSpuSpecDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    @TableField(value = "catalog_id")
    private Long catalogId;

    @TableField("spec_name")
    private String specName;

    @TableField("status")
    private Integer status;

}
