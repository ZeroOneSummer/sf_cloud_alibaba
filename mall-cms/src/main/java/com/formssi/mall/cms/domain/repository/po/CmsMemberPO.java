package com.formssi.mall.cms.domain.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cms_member")
public class CmsMemberPO implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员名称
     */
    @TableField("name")
    private String name;

    /**
     * 手机
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 电子邮件
     */
    @TableField("email")
    private String email;

    /**
     * 性别 1：男，2：女
     */
    @TableField("gender")
    private Byte gender;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 消费金额
     */
    @TableField("spent")
    private Long spent;

    /**
     * 可用余额
     */
    @TableField("balance")
    private Long balance;

    /**
     * 可用积分
     */
    @TableField("point")
    private Integer point;

    /**
     * 登录IP
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 登录时间
     */
    @TableField("login_date")
    private Date loginDate;

    /**
     * 连续登录失败次数
     */
    @TableField("login_failed_count")
    private Integer loginFailedCount;

    /**
     * 是否锁定 0：正常，1：锁定
     */
    @TableField("is_locked")
    private Byte isLocked;

    /**
     * 锁定时间
     */
    @TableField("locked_date")
    private Date lockedDate;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}