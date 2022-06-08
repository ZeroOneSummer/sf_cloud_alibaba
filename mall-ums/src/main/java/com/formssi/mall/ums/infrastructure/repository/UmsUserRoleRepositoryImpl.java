package com.formssi.mall.ums.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.ums.domain.repository.IUmsUserRoleRepository;
import com.formssi.mall.ums.domain.vo.UmsUserRoleDO;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsUserRoleMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户与角色对应关系 仓库实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
@Repository
public class UmsUserRoleRepositoryImpl extends ServiceImpl<UmsUserRoleMapper, UmsUserRoleDO> implements IUmsUserRoleRepository {


}
