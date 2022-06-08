package com.formssi.mall.ums.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.ums.cmd.LoginCmd;
import com.formssi.mall.ums.domain.entity.UmsCaptchaDO;
import com.formssi.mall.ums.domain.entity.UmsUserDO;
import com.formssi.mall.ums.domain.repository.IUmsCaptchaRepository;
import com.formssi.mall.ums.domain.repository.IUmsUserRepository;
import com.formssi.mall.ums.domain.service.IUmsLoginDomainService;
import com.formssi.mall.common.util.Sha1Utils;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.jwt.JwtConstant;
import com.formssi.mall.jwt.entity.JwtUser;
import com.formssi.mall.jwt.service.JwtTokenService;
import com.formssi.mall.redis.service.RedisService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台登录 领域服务实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
@Service
public class UmsLoginDomainServiceImpl implements IUmsLoginDomainService {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private IUmsCaptchaRepository iBmsCaptchaRepository;

    @Autowired
    private IUmsUserRepository iBmsUserRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RedisService redisService;

    @Override
    public String generatorCode() {
        return defaultKaptcha.createText();
    }

    @Override
    public BufferedImage generatorCaptcha(String code) {
        return defaultKaptcha.createImage(code);
    }

    @Override
    public void validateCode(String uuid, String code) throws BusinessException {
        UmsCaptchaDO captcha = iBmsCaptchaRepository.getOne(new QueryWrapper<UmsCaptchaDO>().eq("uuid", uuid));
        if (captcha == null) {
            throw new BusinessException("验证码不正确");
        }
        //删除验证码
        iBmsCaptchaRepository.removeById(uuid);
        if (!captcha.getCode().equalsIgnoreCase(code) && captcha.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("验证码过期");
        }
    }

    @Override
    public UmsUserDO validatePwd(LoginCmd loginCmd) throws BusinessException {
        //用户信息
        QueryWrapper<UmsUserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUserDO::getUsername, loginCmd.getUsername());
        UmsUserDO user = iBmsUserRepository.getOne(wrapper);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(Sha1Utils.sha1(loginCmd.getPassword(), user.getSalt()))) {
            throw new BusinessException("账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被锁定，请联系管理员");
        }
        return user;
    }

    @Override
    public String generatorToken(UmsUserDO user) {
        JwtUser jwtUser = JwtUser.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .salt(user.getSalt())
                .expired(System.currentTimeMillis() + JwtConstant.TOKEN_EXPIRE_TIME)
                .build();
        //生成token
        String token = jwtTokenService.generateToken(jwtUser);
        jwtUser.setExpired(System.currentTimeMillis() + JwtConstant.REFRESH_TOKEN_EXPIRE_TIME);
        //生成refreshToken
        String refreshToken = jwtTokenService.generateToken(jwtUser);
        //缓存refreshToken
        redisService.set(jwtUser.getUserId() + JwtConstant.REDIS_REFRESH_TOKEN_KEY, refreshToken, JwtConstant.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return jwtTokenService.encodeToken(token, refreshToken);
    }


}
