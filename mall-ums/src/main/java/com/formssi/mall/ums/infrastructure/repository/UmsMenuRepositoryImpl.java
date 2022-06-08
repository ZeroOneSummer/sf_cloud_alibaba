package com.formssi.mall.ums.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.ums.domain.entity.UmsMenuDO;
import com.formssi.mall.ums.domain.repository.IUmsMenuRepository;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsMenuMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台菜单 仓库实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
@Repository
public class UmsMenuRepositoryImpl extends ServiceImpl<UmsMenuMapper, UmsMenuDO> implements IUmsMenuRepository {


}
