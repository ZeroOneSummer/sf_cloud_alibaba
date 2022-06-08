package com.formssi.mall.common.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPage<T>{

    private long current = 1;

    private long size = 10;

    private long total;

    private List<T> records;

}
