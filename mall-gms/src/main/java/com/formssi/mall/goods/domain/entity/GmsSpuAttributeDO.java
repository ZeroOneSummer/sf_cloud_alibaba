package com.formssi.mall.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("gms_spu_attribute")
public class GmsSpuAttributeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    @TableField(value = "spu_id")
    private Long spuId;

    @TableField("attribute_value")
    private String attributeValue;

    @TableField("attribute_key")
    private Integer attributeKey;

}
