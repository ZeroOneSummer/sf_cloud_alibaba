package com.formssi.mall.ums.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.ums.domain.entity.UmsUserDO;
import com.formssi.mall.ums.domain.repository.IUmsUserRepository;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsUserMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 后台用户 仓库实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
@Repository
public class UmsUserRepositoryImpl extends ServiceImpl<UmsUserMapper, UmsUserDO> implements IUmsUserRepository {

}
