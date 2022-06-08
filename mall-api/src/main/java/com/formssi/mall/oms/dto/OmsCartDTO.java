package com.formssi.mall.oms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class OmsCartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 购物车key
     */
    private String cardKey;

    /**
     * 会员id
     */
    private Long member_id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;



}
