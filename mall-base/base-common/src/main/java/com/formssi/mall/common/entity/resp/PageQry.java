package com.formssi.mall.common.entity.resp;

import lombok.Data;

@Data
public class PageQry {

    private long current = 1;

    private long size = 10;

}
