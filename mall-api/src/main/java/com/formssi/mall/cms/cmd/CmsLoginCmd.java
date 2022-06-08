package com.formssi.mall.cms.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsLoginCmd {

    /**
     * 会员名称
     */
    @NotNull(message = "用户名不能为空")
    private String name;


    /**
     * 密码
     */
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合")
    private String password;



    /**
     * 短信内容
     */
    private String content;


}
