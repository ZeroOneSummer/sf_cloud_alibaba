package com.formssi.mall.cms.application;

import com.formssi.mall.ums.dto.LoginDTO;
import com.formssi.mall.cms.cmd.CmsLoginCmd;
import com.formssi.mall.cms.cmd.CmsMemberCmd;
import com.formssi.mall.cms.cmd.CmsSmsMessageCmd;
import com.formssi.mall.cms.dto.CmsSmsMessageDTO;

public interface CmsLoginService {

     boolean register(CmsMemberCmd cmsMemberCmd);

     LoginDTO cmsLogin(CmsLoginCmd cmsLoginCmd);

     CmsSmsMessageDTO cmsSmsMessage(CmsSmsMessageCmd cmsSmsMessageCmd);
}
