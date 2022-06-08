package com.formssi.mall.ums.domain.service;

import com.formssi.mall.ums.cmd.UmsUserCmd;
import com.formssi.mall.ums.domain.entity.UmsUserDO;

import java.util.List;

/**
 * <p>
 * 后台用户 领域服务类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-04-02 20:01:13
 */
public interface IUmsUserDomainService {

    UmsUserDO saveUser(UmsUserCmd bmsUserCmd);

    void checkPermission(UmsUserDO bmsUserPO, List<Long> roleIds);

    void saveChoiceRole(Long userId, List<Long> roleIds);

    UmsUserDO updateUser(UmsUserCmd bmsUserCmd);

    void deleteUserRoleById(Long userId);
}
