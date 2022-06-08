package com.formssi.mall.ums.application;

import com.formssi.mall.ums.cmd.UmsMenuCmd;
import com.formssi.mall.ums.dto.UmsMenuDTO;

import java.util.List;

/**
 * <p>
 * 后台菜单 应用服务类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
public interface IUmsMenuService {

    List<UmsMenuDTO> listMenus();

    List<UmsMenuDTO> listRootTreeMenu();

    void saveMenu(UmsMenuCmd umsMenuCmd);

    void updateMenu(UmsMenuCmd bmsMenuCmd);

    void deleteMenu(Long menuId);

    List<UmsMenuDTO> queryNavigationMenu(Long userId);

    List<UmsMenuDTO> queryTreeMenu();

    List<Long> queryMenuIdByRoleId(Long userId);

}
