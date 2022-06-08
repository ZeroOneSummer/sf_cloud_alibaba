package com.formssi.mall.gms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GmsSpuAttributeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long spuId;

    private Integer attributeKey;

    private String attributeValue;
}
