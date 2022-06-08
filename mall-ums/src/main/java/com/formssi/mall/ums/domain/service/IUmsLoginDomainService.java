package com.formssi.mall.ums.domain.service;

import com.formssi.mall.ums.cmd.LoginCmd;
import com.formssi.mall.ums.domain.entity.UmsUserDO;
import com.formssi.mall.exception.exception.BusinessException;

import java.awt.image.BufferedImage;

/**
 * <p>
 * 后台登录 领域服务类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
public interface IUmsLoginDomainService {

    String generatorCode();

    BufferedImage generatorCaptcha(String code);

    void validateCode(String uuid, String code) throws BusinessException;

    UmsUserDO validatePwd(LoginCmd loginCmd) throws BusinessException;

    String generatorToken(UmsUserDO user);

}
