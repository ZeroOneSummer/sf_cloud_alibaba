package com.formssi.mall.ums.domain.service;

import com.formssi.mall.ums.cmd.UmsRoleCmd;
import com.formssi.mall.ums.domain.entity.UmsRoleDO;

import java.util.List;

/**
 * <p>
 * 后台角色 领域服务类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-04-02 20:01:13
 */
public interface IUmsRoleDomainService {

    UmsRoleDO saveRole(UmsRoleCmd bmsRoleCmd);

    void checkPermission(UmsRoleDO bmsRolePO, List<Long> roleIds);

    void saveChoiceMenu(Long RoleId, List<Long> roleIds);

    UmsRoleDO updateRole(UmsRoleCmd bmsRoleCmd);

    void deleteRoleMenuById(Long roleId);
}
