package com.formssi.mall.common.entity.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Deprecated
public class CommonReq<T> {

    private ReqHead head;

    private T body;

}
