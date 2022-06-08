package com.formssi.mall.gms.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GmsSpuSpecDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long specId;

    private Long catalogId;

    private String specName;

    private Integer status;

    private List<GmsSpuSpecValueDTO> specValueList;

}
