package com.formssi.mall.ums.application;

import com.formssi.mall.ums.cmd.UmsUserCmd;
import com.formssi.mall.ums.dto.UmsUserDTO;
import com.formssi.mall.ums.query.UmsUserQry;
import com.formssi.mall.common.entity.resp.CommonPage;

import java.util.List;

/**
 * <p>
 * 后台用户 应用服务类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
public interface IUmsUserService {

    CommonPage<UmsUserDTO> listUsers(UmsUserQry pageQry);

    void saveUser(UmsUserCmd bmsUserCmd);

    void updateUser(UmsUserCmd bmsUserCmd);

    void deleteUserBatch(List<Long> userIds);

    UmsUserDTO getUserInfo(Long userId);

}
