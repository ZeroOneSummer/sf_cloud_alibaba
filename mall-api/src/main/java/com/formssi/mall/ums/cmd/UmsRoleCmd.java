package com.formssi.mall.ums.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsRoleCmd {

    private Long id;

    @NotNull(message = "角色名称不能为空")
    private String roleName;

    @NotNull(message = "备注不能为空")
    private String memo;

    private List<Long> menuIds;


}
