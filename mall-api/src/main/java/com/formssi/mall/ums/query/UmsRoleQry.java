package com.formssi.mall.ums.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

/**
 * <p>
 * 角色管理查询对象
 * </p>
 *
 * @author eirc-ye
 * @since 2022/3/28 16:00
 */
@Data
public class UmsRoleQry extends PageQry {

    private String roleName;

}
