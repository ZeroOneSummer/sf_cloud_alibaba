package com.formssi.mall.gms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author:prms
 * @create: 2022-05-12 14:18
 * @version: 1.0
 */
@Data
public class GmsSpuSearchQry extends PageQry {

    @NotBlank(message = "查询关键字为空")
    private String keyword;

}
