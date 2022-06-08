package com.formssi.mall.oms.cmd;

import lombok.*;
import java.time.LocalDateTime;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author 王亮
 * @since 2022-04-12 20:01:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmsCartCmd{



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
