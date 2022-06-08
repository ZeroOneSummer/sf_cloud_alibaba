package com.formssi.mall.gms.cmd;

import lombok.Data;

import java.io.Serializable;

@Data
public class GmsSpuSpecValueCmd implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long specId;

    private String specValue;

    private String specImage;
}

