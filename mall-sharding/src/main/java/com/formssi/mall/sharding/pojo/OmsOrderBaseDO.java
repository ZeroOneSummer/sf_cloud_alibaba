package com.formssi.mall.sharding.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 *广播表
 */
@Getter
@Setter
@TableName("oms_order_base_t")
@Builder
public class OmsOrderBaseDO implements Serializable {

    private Long id;


    @TableField("attr_1")
    private String attr1;

    @TableField("attr_2")
    private String attr2;

    @TableField("attr_3")
    private String attr3;

    @TableField("attr_4")
    private String attr4;

    @TableField("attr_5")
    private String attr5;

    @TableField("name")
    private String name;

    @TableField("pwd")
    private String pwd;

    private static final long serialVersionUID = 1L;


}
