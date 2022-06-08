package com.formssi.mall.ums.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCmd {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "uuid不能为空")
    String uuid;

    @NotNull(message = "验证码不能为空")
    String captcha;

    @NotNull(message = "用户名不能为空")
    @Size(max = 20, message = "用户名不能超过20位")
    String username;

    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合")
    String password;

}
