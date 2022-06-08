package com.formssi.mall.ums.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.formssi.mall.ums.domain.vo.UmsRoleMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author zhangmiao
 * @since 2022-03-27 20:01:14
 */
public interface UmsRoleMenuMapper extends BaseMapper<UmsRoleMenuDO> {

    List<Long> queryMenuIdByUserId(@Param("userId") Long userId);

}
