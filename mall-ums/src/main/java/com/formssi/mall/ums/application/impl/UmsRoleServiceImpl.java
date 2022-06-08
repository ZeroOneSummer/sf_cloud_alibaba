package com.formssi.mall.ums.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.ums.application.IUmsRoleService;
import com.formssi.mall.ums.cmd.UmsRoleCmd;
import com.formssi.mall.ums.domain.entity.UmsRoleDO;
import com.formssi.mall.ums.domain.repository.IUmsRoleRepository;
import com.formssi.mall.ums.domain.repository.IUmsUserRoleRepository;
import com.formssi.mall.ums.domain.service.IUmsRoleDomainService;
import com.formssi.mall.ums.domain.vo.UmsUserRoleDO;
import com.formssi.mall.ums.dto.UmsRoleDTO;
import com.formssi.mall.ums.infrastructure.util.PageComponent;
import com.formssi.mall.ums.query.UmsRoleQry;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台角色 应用服务实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
@Service
public class UmsRoleServiceImpl implements IUmsRoleService {

    @Autowired
    private IUmsRoleRepository iBmsRoleRepository;

    @Autowired
    private PageComponent<UmsRoleDO> pageComponent;

    @Autowired
    private IUmsRoleDomainService iBmsRoleDomainService;

    @Autowired
    private IUmsUserRoleRepository iBmsUserRoleRepository;
    
    @Override
    public CommonPage<UmsRoleDTO> listRoles(UmsRoleQry bmsRoleQry) {
        QueryWrapper<UmsRoleDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(StringUtils.isNotBlank(bmsRoleQry.getRoleName()), UmsRoleDO::getRoleName, bmsRoleQry.getRoleName());
        CommonPage<UmsRoleDO> page = pageComponent.getPage(wrapper, iBmsRoleRepository.getBaseMapper(), bmsRoleQry.getCurrent(), bmsRoleQry.getSize());
        return BeanCopyUtils.copyProperties(page, new CommonPage<>());
    }

    @Override
    public void saveRole(UmsRoleCmd bmsRoleCmd) {
        // 保存角色
        UmsRoleDO bmsUserDO = iBmsRoleDomainService.saveRole(bmsRoleCmd);
        // 检查角色是否能操作菜单
        iBmsRoleDomainService.checkPermission(bmsUserDO, bmsRoleCmd.getMenuIds());
        // 保存选择的角色
        iBmsRoleDomainService.saveChoiceMenu(bmsUserDO.getId(), bmsRoleCmd.getMenuIds());
    }

    @Override
    public void updateRole(UmsRoleCmd bmsRoleCmd) {
        // 保存角色
        UmsRoleDO bmsUserDO = iBmsRoleDomainService.updateRole(bmsRoleCmd);
        // 检查角色是否能操作菜单
        iBmsRoleDomainService.checkPermission(bmsUserDO, bmsRoleCmd.getMenuIds());
        // 删除角色菜单关联表
        iBmsRoleDomainService.deleteRoleMenuById(bmsUserDO.getId());
        // 保存选择的角色
        iBmsRoleDomainService.saveChoiceMenu(bmsUserDO.getId(), bmsRoleCmd.getMenuIds());
    }

    @Override
    public void deleteRoleBatch(List<Long> roleIds) {
        roleIds.forEach(userId -> {
            iBmsRoleDomainService.deleteRoleMenuById(userId);
        });
        iBmsRoleRepository.removeBatchByIds(roleIds);
    }

    @Override
    public List<UmsRoleDTO> queryRoleList() {
        return BeanCopyUtils.copyListProperties(iBmsRoleRepository.list(), UmsRoleDTO.class);
    }

    @Override
    public List<Long> queryRoleIdByUserId(Long userId) {
        QueryWrapper<UmsUserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUserRoleDO::getUserId, userId);
        List<UmsUserRoleDO> userRoleDOList = iBmsUserRoleRepository.list(wrapper);
        return userRoleDOList.stream().map(UmsUserRoleDO::getRoleId).collect(Collectors.toList());
    }

}
