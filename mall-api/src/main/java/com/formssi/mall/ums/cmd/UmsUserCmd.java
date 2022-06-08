package com.formssi.mall.ums.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsUserCmd {

    private long id;

    @NotNull(message = "用户名不能为空")
    @Size(max = 20, message = "用户名不能超过20位")
    private String username;

//    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合")
//    @NotNull(message = "password不能为空")
//    private String password;

    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "请输入正确的邮箱格式")
    private String email;

    @Size(max = 11, message = "手机号码不能超过11位")
    private String mobile;

    private List<Long> roleIds;  //选择角色ID

}
