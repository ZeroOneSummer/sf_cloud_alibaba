package com.formssi.mall.cms.interfaces;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.cms.application.CmsLoginService;
import com.formssi.mall.cms.cmd.CmsLoginCmd;
import com.formssi.mall.cms.cmd.CmsMemberCmd;
import com.formssi.mall.cms.cmd.CmsSmsMessageCmd;
import com.formssi.mall.cms.infrastructure.util.CmsEnum;
import com.formssi.mall.jwt.entity.JwtUser;
import com.formssi.mall.jwt.service.JwtTokenService;
import com.formssi.mall.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/cms")
public class CmsLonginController {
    @Autowired
    private CmsLoginService cmsLoginService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtTokenService jwtTokenService;
    //会员注册
    @PostMapping("/register")
    public CommonResp register(@RequestBody @Valid CmsMemberCmd cmsMemberCmd)  {
        return CommonResp.ok(cmsLoginService.register(cmsMemberCmd));
    }

    @PostMapping("/cmsLogin")
    public Object cmsLogin(@RequestBody @Valid CmsLoginCmd cmsLoginCmd)  {
        return CommonResp.ok(cmsLoginService.cmsLogin(cmsLoginCmd));
    }

    @PostMapping("/cmsSmsMessage")
    public Object cmsSmsMessage(@RequestBody @Valid CmsSmsMessageCmd cmsSmsMessageCmd)  {
        return CommonResp.ok(cmsLoginService.cmsSmsMessage(cmsSmsMessageCmd));
    }

    @PostMapping("/cmsLoginOut")
    public Object cmsLoginOut(HttpServletRequest request)  {
        String token = request.getHeader(CmsEnum.CMSMEMBER_TOKEN);
        //redis 删除 token
        JwtUser jwtUser = jwtTokenService.parseToken(token);
        redisService.delete(CmsEnum.REDIS_CMSMEMBER_TOKEN+jwtUser.getUserId());
        return CommonResp.ok();
    }

}
