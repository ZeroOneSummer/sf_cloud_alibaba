package com.formssi.mall.ums.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsUserQry extends PageQry {

    private String username;

    private String email;

    private String mobile;

}
