package com.formssi.mall.ums.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.ums.application.IUmsUserService;
import com.formssi.mall.ums.cmd.UmsUserCmd;
import com.formssi.mall.ums.domain.entity.UmsUserDO;
import com.formssi.mall.ums.domain.repository.IUmsUserRepository;
import com.formssi.mall.ums.domain.service.IUmsUserDomainService;
import com.formssi.mall.ums.dto.UmsUserDTO;
import com.formssi.mall.ums.infrastructure.util.PageComponent;
import com.formssi.mall.ums.query.UmsUserQry;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 后台y用户 应用服务实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
@Service
public class UmsUserServiceImpl implements IUmsUserService {

    @Autowired
    private IUmsUserRepository iBmsUserRepository;

    @Autowired
    private PageComponent<UmsUserDO> pageComponent;

    @Autowired
    private IUmsUserDomainService iBmsUserDomainService;


    @Override
    public CommonPage<UmsUserDTO> listUsers(UmsUserQry bmsUserQry) {
        QueryWrapper<UmsUserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(StringUtils.isNotBlank(bmsUserQry.getUsername()), UmsUserDO::getUsername, bmsUserQry.getUsername())
                .like(StringUtils.isNotBlank(bmsUserQry.getEmail()), UmsUserDO::getEmail, bmsUserQry.getEmail())
                .like(StringUtils.isNotBlank(bmsUserQry.getMobile()), UmsUserDO::getMobile, bmsUserQry.getMobile());
        CommonPage<UmsUserDO> page = pageComponent.getPage(wrapper, iBmsUserRepository.getBaseMapper(), bmsUserQry.getCurrent(), bmsUserQry.getSize());
        return BeanCopyUtils.copyProperties(page, new CommonPage<>());
    }

    @Override
    @Transactional
    public void saveUser(UmsUserCmd bmsUserCmd) {
        // 保存用户
        UmsUserDO bmsUserDO = iBmsUserDomainService.saveUser(bmsUserCmd);
        // 检查用户是否能操作角色
        iBmsUserDomainService.checkPermission(bmsUserDO, bmsUserCmd.getRoleIds());
        // 保存选择的角色
        iBmsUserDomainService.saveChoiceRole(bmsUserDO.getId(), bmsUserCmd.getRoleIds());
    }

    @Override
    @Transactional
    public void updateUser(UmsUserCmd bmsUserCmd) {
        // 保存用户
        UmsUserDO bmsUserDO = iBmsUserDomainService.updateUser(bmsUserCmd);
        // 检查用户是否能操作角色
        iBmsUserDomainService.checkPermission(bmsUserDO, bmsUserCmd.getRoleIds());
        // 删除用户角色关联表
        iBmsUserDomainService.deleteUserRoleById(bmsUserDO.getId());
        // 保存选择的角色
        iBmsUserDomainService.saveChoiceRole(bmsUserDO.getId(), bmsUserCmd.getRoleIds());
    }

    @Override
    @Transactional
    public void deleteUserBatch(List<Long> userIds) {
        userIds.forEach(userId -> {
            iBmsUserDomainService.deleteUserRoleById(userId);
        });
        iBmsUserRepository.removeBatchByIds(userIds);
    }

    @Override
    public UmsUserDTO getUserInfo(Long userId) {
        QueryWrapper<UmsUserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUserDO::getId, userId);
        UmsUserDO bmsUserDO = iBmsUserRepository.getOne(wrapper);
        return BeanCopyUtils.copyProperties(bmsUserDO, UmsUserDTO.class);
    }

}
