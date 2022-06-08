package com.formssi.mall.ums.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.ums.cmd.UmsRoleCmd;
import com.formssi.mall.ums.domain.entity.UmsRoleDO;
import com.formssi.mall.ums.domain.repository.IUmsRoleMenuRepository;
import com.formssi.mall.ums.domain.repository.IUmsRoleRepository;
import com.formssi.mall.ums.domain.service.IUmsRoleDomainService;
import com.formssi.mall.ums.domain.vo.UmsRoleMenuDO;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsRoleMenuMapper;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.jwt.entity.JwtUser;
import com.formssi.mall.jwt.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台角色 领域服务实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-04-02 20:01:13
 */
@Slf4j
@Service
public class UmsRoleDomainServiceImpl implements IUmsRoleDomainService {

    @Autowired
    private IUmsRoleRepository iBmsRoleRepository;

    @Autowired
    private IUmsRoleMenuRepository iBmsRoleMenuRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UmsRoleMenuMapper bmsRoleMenuMapper;

    @Override
    public UmsRoleDO saveRole(UmsRoleCmd bmsRoleCmd) {
        UmsRoleDO bmsRolePO = BeanCopyUtils.copyProperties(bmsRoleCmd, UmsRoleDO.class);
        bmsRolePO.setCreateTime(LocalDateTime.now());
        iBmsRoleRepository.save(bmsRolePO);
        return bmsRolePO;
    }

    @Override
    public void checkPermission(UmsRoleDO bmsRolePO, List<Long> menuIds) {
        //超级管理员不检查
        JwtUser jwtUser = jwtTokenService.getJwtUser();
        if (jwtUser.getUserId() == 1L && "admin".equals(jwtUser.getUsername())) {
            log.info("登录用户是超级管理员不检查");
        } else {
            if (menuIds == null || menuIds.isEmpty()) {
                return;
            }
            //查询用户拥有的菜单列表
            List<Long> hasMenuIds = bmsRoleMenuMapper.queryMenuIdByUserId(bmsRolePO.getCreateUserId());
            //判断是否越权
            if (!hasMenuIds.containsAll(menuIds)) {
                throw new BusinessException("没有权限操作: " + menuIds);
            }
        }
    }

    @Override
    public void saveChoiceMenu(Long roleId, List<Long> menuIds) {
        List<UmsRoleMenuDO> RoleRolePOList = menuIds.stream().map(menuId -> {
            UmsRoleMenuDO bmsRoleRolePO = new UmsRoleMenuDO();
            bmsRoleRolePO.setRoleId(roleId);
            bmsRoleRolePO.setMenuId(menuId);
            return bmsRoleRolePO;
        }).collect(Collectors.toList());
        iBmsRoleMenuRepository.saveBatch(RoleRolePOList);
    }

    @Override
    public UmsRoleDO updateRole(UmsRoleCmd bmsRoleCmd) {
        UmsRoleDO bmsRolePO = BeanCopyUtils.copyProperties(bmsRoleCmd, UmsRoleDO.class);
        iBmsRoleRepository.updateById(bmsRolePO);
        return bmsRolePO;
    }

    @Override
    public void deleteRoleMenuById(Long roleId) {
        QueryWrapper<UmsRoleMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenuDO::getRoleId, roleId);
        iBmsRoleMenuRepository.remove(wrapper);
    }

}
