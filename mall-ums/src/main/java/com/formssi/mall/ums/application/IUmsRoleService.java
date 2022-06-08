package com.formssi.mall.ums.application;

import com.formssi.mall.ums.cmd.UmsRoleCmd;
import com.formssi.mall.ums.dto.UmsRoleDTO;
import com.formssi.mall.ums.query.UmsRoleQry;
import com.formssi.mall.common.entity.resp.CommonPage;

import java.util.List;

/**
 * <p>
 * 后台角色 应用服务类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
public interface IUmsRoleService {


    CommonPage<UmsRoleDTO> listRoles(UmsRoleQry bmsRoleQry);

    void saveRole(UmsRoleCmd bmsRoleCmd);

    void updateRole(UmsRoleCmd bmsRoleCmd);

    void deleteRoleBatch(List<Long> roleIds);

    List<UmsRoleDTO> queryRoleList();

    List<Long> queryRoleIdByUserId(Long userId);

}
