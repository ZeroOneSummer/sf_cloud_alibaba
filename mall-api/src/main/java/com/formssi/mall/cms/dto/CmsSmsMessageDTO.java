package com.formssi.mall.cms.dto;


import lombok.Data;


import java.io.Serializable;

@Data
public class CmsSmsMessageDTO implements Serializable {

    /**
     * 短信内容
     */
    private String content;



}