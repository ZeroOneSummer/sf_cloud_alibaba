package com.formssi.mall.goods.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("gms_spu_spec_value")
public class GmsSpuSpecValueDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    @TableField("spec_id")
    private Long specId;

    @TableField("spec_value")
    private String specValue;

    @TableField("spec_image")
    private String specImage;
}
