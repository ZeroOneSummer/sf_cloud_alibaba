package com.formssi.mall.cms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class TokenDTO {
    private String token;
    private String refreshToken;
    private String displayName;
    private String avatar;
    private Long userId;
}
