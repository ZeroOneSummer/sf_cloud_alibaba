package com.formssi.mall.cms.domain.service;

import com.formssi.mall.cms.cmd.CmsLoginCmd;
import com.formssi.mall.cms.cmd.CmsMemberCmd;
import com.formssi.mall.cms.cmd.CmsSmsMessageCmd;
import com.formssi.mall.cms.domain.repository.po.CmsMemberPO;

public interface CmsLoginDomainService {

    boolean register(CmsMemberCmd cmsMemberCmd);

    boolean cmsSmsMessage(CmsSmsMessageCmd cmsSmsMessageCmd);


    String generatorToken(CmsMemberPO cmsMemberPO);

    CmsMemberPO checkPassWord(CmsLoginCmd cmsLoginCmd);


    void checkSmsMessage(CmsLoginCmd cmsLoginCmd);
}
