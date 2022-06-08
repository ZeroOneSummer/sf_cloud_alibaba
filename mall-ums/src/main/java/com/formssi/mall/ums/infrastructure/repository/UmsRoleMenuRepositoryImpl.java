package com.formssi.mall.ums.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.ums.domain.repository.IUmsRoleMenuRepository;
import com.formssi.mall.ums.domain.vo.UmsRoleMenuDO;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsRoleMenuMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色与菜单对应关系 仓库实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
@Repository
public class UmsRoleMenuRepositoryImpl extends ServiceImpl<UmsRoleMenuMapper, UmsRoleMenuDO> implements IUmsRoleMenuRepository {

}
