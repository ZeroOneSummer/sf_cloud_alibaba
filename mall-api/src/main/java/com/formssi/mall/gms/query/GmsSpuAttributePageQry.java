package com.formssi.mall.gms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

@Data
public class GmsSpuAttributePageQry extends PageQry {

    private Integer attributeKey;
    private String attributeValue;
}
