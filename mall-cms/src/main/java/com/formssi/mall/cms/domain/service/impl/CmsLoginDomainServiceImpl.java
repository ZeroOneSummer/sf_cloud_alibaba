package com.formssi.mall.cms.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.common.util.DesUtils;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.cms.cmd.CmsLoginCmd;
import com.formssi.mall.cms.cmd.CmsMemberCmd;
import com.formssi.mall.cms.cmd.CmsSmsMessageCmd;
import com.formssi.mall.cms.domain.repository.CmsSmsMessageRepository;
import com.formssi.mall.cms.domain.repository.po.CmsMemberPO;
import com.formssi.mall.cms.domain.repository.po.CmsSmsMessagePO;
import com.formssi.mall.cms.domain.service.CmsLoginDomainService;
import com.formssi.mall.cms.domain.repository.CmsLoginRepository;
import com.formssi.mall.cms.infrastructure.util.CmsEnum;
import com.formssi.mall.jwt.JwtConstant;
import com.formssi.mall.jwt.entity.JwtUser;
import com.formssi.mall.jwt.service.JwtTokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CmsLoginDomainServiceImpl implements CmsLoginDomainService {
    @Autowired
    private CmsLoginRepository cmsLoginRepository;
    @Autowired
    private CmsSmsMessageRepository cmsSmsMessageRepository;

    @Autowired
    private JwtTokenService jwtTokenService;
    @Override
    public boolean register( CmsMemberCmd cmsMemberCmd) {
        CmsMemberPO cmsMemberPO = new CmsMemberPO();
        String encode = DesUtils.encode(cmsMemberCmd.getPassword(), CmsEnum.SECRETKEY);
        BeanUtils.copyProperties(cmsMemberCmd, cmsMemberPO);
        cmsMemberPO.setPassword(encode);
        cmsMemberPO.setCreateTime(new Date());
        return cmsLoginRepository.save(cmsMemberPO);
    }

    @Override
    public boolean cmsSmsMessage(CmsSmsMessageCmd cmsSmsMessageCmd) {
        CmsSmsMessagePO cmsSmsMessagePO = new CmsSmsMessagePO();
        cmsSmsMessagePO.setContent(cmsSmsMessageCmd.getContent());
        cmsSmsMessagePO.setMobile(cmsSmsMessageCmd.getMobile());
        cmsSmsMessagePO.setStatus(CmsEnum.SMSMESSAGESTATUS);
        cmsSmsMessagePO.setSmsType(CmsEnum.SMSMESSAGETYPE);
        cmsSmsMessagePO.setCreateTime(new Date());
        return cmsSmsMessageRepository.save(cmsSmsMessagePO);
    }



    @Override
    public String generatorToken(CmsMemberPO cmsMemberPO) {

        //生成token
        JwtUser jwtUser = JwtUser.builder()
                .userId(cmsMemberPO.getId())
                .username(cmsMemberPO.getName())
                .expired(System.currentTimeMillis() + JwtConstant.TOKEN_EXPIRE_TIME)
                .build();
        return jwtTokenService.generateToken(jwtUser);

    }

    @Override
    public CmsMemberPO checkPassWord(CmsLoginCmd cmsLoginCmd) {

        QueryWrapper<CmsMemberPO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CmsMemberPO::getName, cmsLoginCmd.getName());
        CmsMemberPO cmsMemberPO = cmsLoginRepository.getOne(wrapper);
        String encode = DesUtils.encode(cmsLoginCmd.getPassword(), CmsEnum.SECRETKEY);
        //账号不存在、密码错误
        if (cmsMemberPO == null || !cmsMemberPO.getPassword().equals(encode)) {
            throw new BusinessException("账号或密码不正确");
        }
        //账号锁定
        if (CmsEnum.ISOCKED.equals(cmsMemberPO.getIsLocked())) {
            throw new BusinessException("账号已被锁定，请联系管理员");
        }
        return cmsMemberPO;

    }

    @Override
    public void checkSmsMessage(CmsLoginCmd cmsLoginCmd) {
        CmsSmsMessagePO cmsSmsMessagePO = cmsSmsMessageRepository.getOne(new QueryWrapper<CmsSmsMessagePO>().eq("content", cmsLoginCmd.getContent()));
        if (cmsSmsMessagePO == null) {
            throw new BusinessException("手机验证码不正确");
        }
        long sysmill = System.currentTimeMillis();
        long createTime = cmsSmsMessagePO.getCreateTime().getTime();
        //大于5分钟过期
        if (sysmill - createTime > CmsEnum.EXPIRE_TIME) {
            throw new BusinessException("手机验证码过期");
        }
    }



}
