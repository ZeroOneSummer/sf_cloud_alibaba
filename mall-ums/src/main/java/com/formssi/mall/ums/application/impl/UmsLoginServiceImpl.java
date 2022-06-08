package com.formssi.mall.ums.application.impl;

import com.formssi.mall.ums.application.IUmsLoginService;
import com.formssi.mall.ums.cmd.LoginCmd;
import com.formssi.mall.ums.domain.entity.UmsCaptchaDO;
import com.formssi.mall.ums.domain.entity.UmsUserDO;
import com.formssi.mall.ums.domain.repository.IUmsCaptchaRepository;
import com.formssi.mall.ums.domain.repository.IUmsUserRepository;
import com.formssi.mall.ums.domain.service.IUmsLoginDomainService;
import com.formssi.mall.ums.dto.LoginDTO;
import com.formssi.mall.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * <p>
 * 后台登录 应用服务实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
@Service
public class UmsLoginServiceImpl implements IUmsLoginService {

    @Autowired
    private IUmsLoginDomainService iBmsLoginDomainService;

    @Autowired
    private IUmsCaptchaRepository iBmsCaptchaRepository;

    @Autowired
    private IUmsUserRepository iBmsUserRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        //生成文字验证码
        String code = iBmsLoginDomainService.generatorCode();
        //生成验证码实体
        UmsCaptchaDO bmsCaptchaDO = UmsCaptchaDO.getCaptcha(uuid, code);
        //保存验证码实体
        iBmsCaptchaRepository.save(bmsCaptchaDO);
        //生成图片验证码
        return iBmsLoginDomainService.generatorCaptcha(code);
    }

    @Override
    public LoginDTO login(LoginCmd loginCmd) {
        //验证图片验证码
        iBmsLoginDomainService.validateCode(loginCmd.getUuid(), loginCmd.getCaptcha());
        //验证用户密码
        UmsUserDO bmsUserDO = iBmsLoginDomainService.validatePwd(loginCmd);
        //生成token
        String token = iBmsLoginDomainService.generatorToken(bmsUserDO);
        //组合登录信息并返回
        return LoginDTO.builder().username(bmsUserDO.getUsername()).token(token).build();
    }

}
