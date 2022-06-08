package com.formssi.mall.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 图形验证码
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
@Getter
@Setter
@Builder
@TableName("ums_captcha")
public class UmsCaptchaDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId("uuid")
    private String uuid;

    /**
     * 验证码
     */
    @TableField("`code`")
    private String code;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;

    public static UmsCaptchaDO getCaptcha(String uuid, String code){
        // 5分钟后过期
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(5);
        return UmsCaptchaDO.builder()
                .uuid(uuid)
                .code(code)
                .expireTime(dateTime)
                .build();
    }

}
