package com.formssi.mall.common.entity.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReqHead {

    private String source;

    private String uuid;

    private String tranDate;

    private String channel;

}
