package com.formssi.mall.ums.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.ums.application.IUmsMenuService;
import com.formssi.mall.ums.cmd.UmsMenuCmd;
import com.formssi.mall.ums.domain.entity.UmsMenuDO;
import com.formssi.mall.ums.domain.repository.IUmsMenuRepository;
import com.formssi.mall.ums.domain.vo.UmsRoleMenuDO;
import com.formssi.mall.ums.dto.UmsMenuDTO;
import com.formssi.mall.ums.infrastructure.repository.mapper.UmsRoleMenuMapper;
import com.formssi.mall.common.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台菜单 应用服务实现类
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:13
 */
@Service
public class UmsMenuServiceImpl implements IUmsMenuService {

    @Autowired
    private IUmsMenuRepository iBmsMenuRepository;

    @Autowired
    private UmsRoleMenuMapper bmsRoleMenuMapper;

    @Override
    public List<UmsMenuDTO> listMenus() {
        List<UmsMenuDO> menuDOList = iBmsMenuRepository.list();
        return BeanCopyUtils.copyListProperties(menuDOList, UmsMenuDTO.class);
    }

    @Override
    public List<UmsMenuDTO> listRootTreeMenu() {
        QueryWrapper<UmsMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().ne(UmsMenuDO::getType, 2);
        List<UmsMenuDO> menuDOList = iBmsMenuRepository.list(wrapper);
        List<UmsMenuDTO> menuDTOList = BeanCopyUtils.copyListProperties(menuDOList, UmsMenuDTO.class);
        List<UmsMenuDTO> treeMenuList = menuDTOList.stream()
                .filter(item -> item.getParentId() == 0)
                .peek(item -> item.setChildrenList(getChildren(item, menuDTOList)))
                .collect(Collectors.toList());
        UmsMenuDTO rootNode = new UmsMenuDTO();
        rootNode.setId(0L);
        rootNode.setName("顶级菜单");
        rootNode.setChildrenList(treeMenuList);
        return Collections.singletonList(rootNode);
    }

    @Override
    public void saveMenu(UmsMenuCmd umsMenuCmd) {
        UmsMenuDO bmsMenuDO = BeanCopyUtils.copyProperties(umsMenuCmd, UmsMenuDO.class);
        iBmsMenuRepository.save(bmsMenuDO);
    }

    @Override
    public void updateMenu(UmsMenuCmd bmsMenuCmd) {
        UmsMenuDO bmsMenuDO = BeanCopyUtils.copyProperties(bmsMenuCmd, UmsMenuDO.class);
        iBmsMenuRepository.updateById(bmsMenuDO);
    }

    @Override
    public void deleteMenu(Long menuId) {
        iBmsMenuRepository.removeById(menuId);
    }

    @Override
    public List<UmsMenuDTO> queryNavigationMenu(Long userId) {
        List<Long> menuIds = bmsRoleMenuMapper.queryMenuIdByUserId(userId);
        QueryWrapper<UmsMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(UmsMenuDO::getId, menuIds)
                .ne(UmsMenuDO::getType, 2);
        List<UmsMenuDO> menuDOList = iBmsMenuRepository.list(wrapper);
        List<UmsMenuDTO> menuDTOList = BeanCopyUtils.copyListProperties(menuDOList, UmsMenuDTO.class);
        return menuDTOList.stream()
                .filter(item -> item.getParentId() == 0)
                .peek(item -> item.setChildrenList(getChildren(item, menuDTOList)))
                .collect(Collectors.toList());
    }

    @Override
    public List<UmsMenuDTO> queryTreeMenu() {
        QueryWrapper<UmsMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .ne(UmsMenuDO::getType, 2);
        List<UmsMenuDO> menuDOList = iBmsMenuRepository.list(wrapper);
        List<UmsMenuDTO> menuDTOList = BeanCopyUtils.copyListProperties(menuDOList, UmsMenuDTO.class);
        return menuDTOList.stream()
                .filter(item -> item.getParentId() == 0)
                .peek(item -> item.setChildrenList(getChildren(item, menuDTOList)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> queryMenuIdByRoleId(Long roleId) {
        QueryWrapper<UmsRoleMenuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenuDO::getRoleId, roleId);
        List<UmsRoleMenuDO> roleMenuDOList = bmsRoleMenuMapper.selectList(wrapper);
        return roleMenuDOList.stream().map(UmsRoleMenuDO::getMenuId).collect(Collectors.toList());
    }

    private List<UmsMenuDTO> getChildren(UmsMenuDTO root, List<UmsMenuDTO> all) {
        return all.stream()
                .filter(item -> Objects.equals(item.getParentId(), root.getId()))
                .peek(item -> item.setChildrenList(getChildren(item, all)))
                .collect(Collectors.toList());
    }

}
