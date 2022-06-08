package com.formssi.mall.gms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

@Data
public class GmsSpuSpecPageQry extends PageQry {

    private Long catalogId;

    private String specName;

    private Integer status;
}
