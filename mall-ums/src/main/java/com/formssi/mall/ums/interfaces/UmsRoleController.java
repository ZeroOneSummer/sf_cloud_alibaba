package com.formssi.mall.ums.interfaces;

import com.formssi.mall.ums.application.IUmsRoleService;
import com.formssi.mall.ums.cmd.UmsRoleCmd;
import com.formssi.mall.ums.query.UmsRoleQry;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.exception.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * <p>
 * 后台角色 前端控制器
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 23:41:53
 */
@RestController
@RequestMapping("/ums")
public class UmsRoleController {
    
    @Autowired
    private IUmsRoleService iBmsRoleService;

    @PostMapping("/listRoles")
    public CommonResp listRoles(@RequestBody @Valid UmsRoleQry bmsRoleQry) {
        return CommonResp.ok(iBmsRoleService.listRoles(bmsRoleQry));
    }

    @PostMapping("/saveRole")
    public CommonResp addRole(@RequestBody @Valid UmsRoleCmd bmsRoleCmd) {
        iBmsRoleService.saveRole(bmsRoleCmd);
        return CommonResp.ok();
    }

    @PostMapping("/updateRole")
    public CommonResp updaterRole(@RequestBody @Valid UmsRoleCmd bmsRoleCmd) {
        ValidatorUtils.isNull(bmsRoleCmd.getId(), "角色ID不能为空");
        iBmsRoleService.updateRole(bmsRoleCmd);
        return CommonResp.ok();
    }

    @PostMapping("/deleteRoles")
    public CommonResp deleteRoles(@RequestBody List<Long> roleIds) {
        ValidatorUtils.isCollectionEmpty(roleIds, "删除角色ID不能为空");
        iBmsRoleService.deleteRoleBatch(roleIds);
        return CommonResp.ok();
    }

    @PostMapping("/queryRoleList")
    public CommonResp queryRoleList() {
        return CommonResp.ok(iBmsRoleService.queryRoleList());
    }

    @PostMapping("/queryRoleIdByUserId")
    public CommonResp queryRoleIdByUserId(@RequestBody Long userId) {
        ValidatorUtils.isNull(userId, "用户ID不能为空");
        return CommonResp.ok(iBmsRoleService.queryRoleIdByUserId(userId));
    }
    
}
