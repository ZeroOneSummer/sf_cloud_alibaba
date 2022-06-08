package com.formssi.mall.cms.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsMemberCmd {
    /**
     * ID
     */
    private Long id;

    /**
     * 会员名称
     */
    @NotNull(message = "用户名不能为空")
    private String name;

    /**
     * 手机
     */
    @NotNull(message = "手机不能为空")
    private String mobile;



    /**
     * 昵称
     */
    @NotNull(message = "用户名不能为空")
    private String nickName;



    /**
     * 密码
     */
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合")
    private String password;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 性别 1：男，2：女
     */
    private Byte gender;

    /**
     * 头像
     */
    private String avatar;












}