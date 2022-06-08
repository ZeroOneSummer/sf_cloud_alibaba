package com.formssi.mall.ums.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.ums.domain.entity.UmsRoleDO;
import com.formssi.mall.ums.domain.repository.IUmsRoleRepository;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsRoleMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台角色 仓库实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
@Repository
public class UmsRoleRepositoryImpl extends ServiceImpl<UmsRoleMapper, UmsRoleDO> implements IUmsRoleRepository {

}
