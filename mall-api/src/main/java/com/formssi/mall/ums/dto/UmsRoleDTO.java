package com.formssi.mall.ums.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/3/28 15:28
 */
@Getter
@Setter
public class UmsRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
