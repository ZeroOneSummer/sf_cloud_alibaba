package com.formssi.mall.goods.infrastructure.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.common.entity.resp.CommonPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * 分页工具
 */
@Component
@Slf4j
public class PageComponent<T> {

    public CommonPage<T> getPage(QueryWrapper<T> wrapper, BaseMapper<T> mapper, long current, long size) {
        CommonPage<T> commonPage = new CommonPage<>();
        Page<T> page = new Page<>(current, size);
        IPage<T> iPage = mapper.selectPage(page, wrapper);
        commonPage.setCurrent(iPage.getCurrent());
        commonPage.setSize(iPage.getSize());
        commonPage.setRecords(iPage.getRecords());
        commonPage.setTotal(iPage.getTotal());
        return commonPage;
    }

    public CommonPage<T> getPage(Function<IPage<T>, IPage<T>> function, long current, long size) {
        IPage<T> page = new Page<>(current, size);
        IPage<T> iPage = function.apply(page);
        CommonPage<T> commonPage = new CommonPage<>();
        commonPage.setCurrent(iPage.getCurrent());
        commonPage.setSize(iPage.getSize());
        commonPage.setRecords(iPage.getRecords());
        commonPage.setTotal(iPage.getTotal());
        return commonPage;
    }

}
