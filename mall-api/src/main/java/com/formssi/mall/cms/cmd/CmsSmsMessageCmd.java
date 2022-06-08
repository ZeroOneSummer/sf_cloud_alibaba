package com.formssi.mall.cms.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsSmsMessageCmd {

    /**
     * 手机
     */
    @NotNull(message = "手机不能为空")
    private String mobile;

    /**
     * 短信内容
     */
    private String content;
}
