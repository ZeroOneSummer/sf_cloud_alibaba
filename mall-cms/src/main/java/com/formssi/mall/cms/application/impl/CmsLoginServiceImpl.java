package com.formssi.mall.cms.application.impl;

import com.formssi.mall.ums.dto.LoginDTO;
import com.formssi.mall.cms.application.CmsLoginService;
import com.formssi.mall.cms.cmd.CmsLoginCmd;
import com.formssi.mall.cms.cmd.CmsMemberCmd;
import com.formssi.mall.cms.cmd.CmsSmsMessageCmd;
import com.formssi.mall.cms.domain.repository.po.CmsMemberPO;
import com.formssi.mall.cms.domain.service.CmsLoginDomainService;
import com.formssi.mall.cms.dto.CmsSmsMessageDTO;
import com.formssi.mall.cms.dto.TokenDTO;
import com.formssi.mall.cms.infrastructure.util.CmsEnum;
import com.formssi.mall.jwt.JwtConstant;
import com.formssi.mall.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CmsLoginServiceImpl implements CmsLoginService {
    @Autowired
    private CmsLoginDomainService cmsLoginDomainService;
    @Autowired
    private RedisService redisService;
    @Override
    public boolean register(CmsMemberCmd cmsMemberCmd) {

        return cmsLoginDomainService.register(cmsMemberCmd);
    }

    @Override
    public LoginDTO cmsLogin(CmsLoginCmd cmsLoginCmd) {
        //验证短信验证码是否正确
        cmsLoginDomainService.checkSmsMessage(cmsLoginCmd);
        //验证用户名密码是否正确
         CmsMemberPO cmsMemberPO = cmsLoginDomainService.checkPassWord(cmsLoginCmd);
        //生成token
        String token = cmsLoginDomainService.generatorToken(cmsMemberPO);
        TokenDTO tokenDTO = TokenDTO.builder()
                .token(token)
                .displayName(cmsMemberPO.getName())
                .userId(cmsMemberPO.getId())
                .build();
        //redis 保存token
        redisService.set(CmsEnum.REDIS_CMSMEMBER_TOKEN + cmsMemberPO.getId(), tokenDTO, JwtConstant.REFRESH_TOKEN_EXPIRE_TIME / 1000, TimeUnit.SECONDS);
        //组合登录信息并返回
        return LoginDTO.builder().username(cmsMemberPO.getName()).token(token).build();

    }



    @Override
    public CmsSmsMessageDTO cmsSmsMessage(CmsSmsMessageCmd cmsSmsMessageCmd) {
        String content = String.valueOf((int) (Math.random() * 900000 + 100000));
        cmsSmsMessageCmd.setContent(content);
        cmsLoginDomainService.cmsSmsMessage(cmsSmsMessageCmd);
        CmsSmsMessageDTO cmsSmsMessageDTO = new CmsSmsMessageDTO();
        cmsSmsMessageDTO.setContent(content);
        return cmsSmsMessageDTO;
    }



}
