package com.formssi.mall.ums.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.formssi.mall.ums.domain.entity.UmsCaptchaDO;
import com.formssi.mall.ums.domain.repository.IUmsCaptchaRepository;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsCaptchaMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 图形验证码 仓库实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
@Repository
public class UmsCaptchaRepositoryImpl extends ServiceImpl<UmsCaptchaMapper, UmsCaptchaDO> implements IUmsCaptchaRepository {

}
