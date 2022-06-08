package com.formssi.mall.ums.interfaces;

import com.formssi.mall.ums.application.IUmsMenuService;
import com.formssi.mall.ums.cmd.UmsMenuCmd;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.exception.util.ValidatorUtils;
import com.formssi.mall.jwt.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * <p>
 * 后台菜单 前端控制器
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 23:41:53
 */
@RestController
@RequestMapping("/ums")
public class UmsMenuController {
    @Autowired
    private IUmsMenuService iBmsMenuService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/listMenus")
    public CommonResp listMenus() {
        return CommonResp.ok(iBmsMenuService.listMenus());
    }

    @PostMapping("/listRootTreeMenu")
    public CommonResp listRootTreeMenu() {
        return CommonResp.ok(iBmsMenuService.listRootTreeMenu());
    }

    @PostMapping("/saveMenu")
    public CommonResp saveMenu(@RequestBody @Valid UmsMenuCmd bmsMenuCmd) {
        iBmsMenuService.saveMenu(bmsMenuCmd);
        return CommonResp.ok();
    }

    @PostMapping("/updateMenu")
    public CommonResp updateMenu(@RequestBody @Valid UmsMenuCmd bmsMenuCmd) {
        ValidatorUtils.isNull(bmsMenuCmd.getId(), "菜单ID不能为空");
        iBmsMenuService.updateMenu(bmsMenuCmd);
        return CommonResp.ok();
    }

    @PostMapping("/deleteMenu")
    public CommonResp deleteMenu(@RequestBody Long menuId) {
        ValidatorUtils.isNull(menuId, "删除菜单ID不能为空");
        iBmsMenuService.deleteMenu(menuId);
        return CommonResp.ok();
    }

    @PostMapping("/queryNavigationMenu")
    public CommonResp queryNavigationMenu() {
        Long userId = jwtTokenService.getUserId();
        return CommonResp.ok(iBmsMenuService.queryNavigationMenu(userId));
    }

    @PostMapping("/queryTreeMenu")
    public CommonResp queryTreeMenu() {
        return CommonResp.ok(iBmsMenuService.queryTreeMenu());
    }


    @PostMapping("/queryMenuIdByRoleId")
    public CommonResp queryMenuIdByRoleId(@RequestBody Long roleId) {
        ValidatorUtils.isNull(roleId, "角色ID不能为空");
        return CommonResp.ok(iBmsMenuService.queryMenuIdByRoleId(roleId));
    }

}
