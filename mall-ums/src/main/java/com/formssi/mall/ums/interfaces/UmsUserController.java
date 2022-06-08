package com.formssi.mall.ums.interfaces;

import com.formssi.mall.ums.application.IUmsUserService;
import com.formssi.mall.ums.cmd.UmsUserCmd;
import com.formssi.mall.ums.query.UmsUserQry;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.exception.util.ValidatorUtils;
import com.formssi.mall.jwt.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 后台用户 前端控制器
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 23:41:53
 */
@RestController
@RequestMapping("/ums")
public class UmsUserController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private IUmsUserService iBmsUserService;

    @PostMapping("/listUsers")
    public CommonResp listUsers(@RequestBody @Valid UmsUserQry bmsUserQry) {
        return CommonResp.ok(iBmsUserService.listUsers(bmsUserQry));
    }

    @PostMapping("/saveUser")
    public CommonResp saveUser(@RequestBody @Valid UmsUserCmd bmsUserCmd) {
        iBmsUserService.saveUser(bmsUserCmd);
        return CommonResp.ok();
    }

    @PostMapping("/updateUser")
    public CommonResp updateUser(@RequestBody @Valid UmsUserCmd bmsUserCmd) {
        ValidatorUtils.isNull(bmsUserCmd.getId(), "用户ID不能为空");
        iBmsUserService.updateUser(bmsUserCmd);
        return CommonResp.ok();
    }

    @PostMapping("/deleteUsers")
    public CommonResp deleteUsers(@RequestBody List<Long> userIds) {
        ValidatorUtils.isCollectionEmpty(userIds, "删除用户ID不能为空");
        iBmsUserService.deleteUserBatch(userIds);
        return CommonResp.ok();
    }

}
