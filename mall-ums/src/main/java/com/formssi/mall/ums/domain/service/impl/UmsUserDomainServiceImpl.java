package com.formssi.mall.ums.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.ums.cmd.UmsUserCmd;
import com.formssi.mall.ums.domain.entity.UmsUserDO;
import com.formssi.mall.ums.domain.repository.IUmsUserRepository;
import com.formssi.mall.ums.domain.repository.IUmsUserRoleRepository;
import com.formssi.mall.ums.domain.service.IUmsUserDomainService;
import com.formssi.mall.ums.domain.vo.UmsUserRoleDO;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.common.util.Sha1Utils;
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
 * 后台用户 领域服务实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-04-02 20:01:13
 */
@Slf4j
@Service
public class UmsUserDomainServiceImpl implements IUmsUserDomainService {

    @Autowired
    private IUmsUserRepository iBmsUserRepository;

    @Autowired
    private IUmsUserRoleRepository iBmsUserRoleRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public UmsUserDO saveUser(UmsUserCmd bmsUserCmd) {
        UmsUserDO bmsUserPO = BeanCopyUtils.copyProperties(bmsUserCmd, UmsUserDO.class);
        bmsUserPO.setSalt(Sha1Utils.genSalt());
        bmsUserPO.setCreateUserId(jwtTokenService.getUserId());
        bmsUserPO.setPassword(Sha1Utils.sha1("Aa111111", bmsUserPO.getSalt()));
        bmsUserPO.setCreateTime(LocalDateTime.now());
        iBmsUserRepository.save(bmsUserPO);
        return bmsUserPO;
    }

    @Override
    public void checkPermission(UmsUserDO bmsUserPO, List<Long> roleIds) {
        //超级管理员不检查
        JwtUser jwtUser = jwtTokenService.getJwtUser();
        if (jwtUser.getUserId() == 1L && "admin".equals(jwtUser.getUsername())) {
            log.info("登录用户是超级管理员不检查");
        } else {
            if (roleIds == null || roleIds.isEmpty()) {
                return;
            }
            //查询用户拥有的角色列表
            QueryWrapper<UmsUserRoleDO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(UmsUserRoleDO::getUserId, bmsUserPO.getCreateUserId());
            List<Long> haveRoleIds = iBmsUserRoleRepository.list(wrapper).stream().map(UmsUserRoleDO::getRoleId).collect(Collectors.toList());
            //判断是否越权
            if (!haveRoleIds.containsAll(roleIds)) {
                throw new BusinessException("没有权限操作: " + roleIds);
            }
        }
    }

    @Override
    public void saveChoiceRole(Long userId, List<Long> roleIds) {
        List<UmsUserRoleDO> userRolePOList = roleIds.stream().map(roleId -> {
            UmsUserRoleDO bmsUserRolePO = new UmsUserRoleDO();
            bmsUserRolePO.setUserId(userId);
            bmsUserRolePO.setRoleId(roleId);
            return bmsUserRolePO;
        }).collect(Collectors.toList());
        iBmsUserRoleRepository.saveBatch(userRolePOList);
    }

    @Override
    public UmsUserDO updateUser(UmsUserCmd bmsUserCmd) {
        UmsUserDO bmsUserPO = BeanCopyUtils.copyProperties(bmsUserCmd, UmsUserDO.class);
        iBmsUserRepository.updateById(bmsUserPO);
        return bmsUserPO;
    }

    @Override
    public void deleteUserRoleById(Long userId) {
        QueryWrapper<UmsUserRoleDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUserRoleDO::getUserId, userId);
        iBmsUserRoleRepository.remove(wrapper);
    }

}
