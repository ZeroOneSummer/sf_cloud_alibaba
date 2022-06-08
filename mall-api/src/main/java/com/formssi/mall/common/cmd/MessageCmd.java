package com.formssi.mall.common.cmd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jiangyaoting
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCmd {

    //接收人,可以多个
    @NotNull(message = "接收人不能为空!")
    private List<String> tos;

    //邮件主题
    private String subject;

    //邮件内容
    @NotNull(message = "邮件内容不能为空!")
    private String text;

    //图片
    private List<String> images;

}
