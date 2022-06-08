package com.formssi.mall.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author 王亮
 * @since 2022-04-12 20:01:14
 */
@Getter
@Setter
@TableName("oms_cart")
public class   OmsCartDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 购物车key
     */
    @TableField("card_key")
    private String cardKey;

    /**
     * 会员id
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    @TableField("expireTime")
    private LocalDateTime expireTime;



}
