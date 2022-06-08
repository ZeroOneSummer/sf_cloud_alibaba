package com.formssi.mall.goods.domain.service.impl;

import com.formssi.mall.goods.domain.entity.GmsCategoryDo;
import com.formssi.mall.goods.domain.repository.GmsCategoryRepository;
import com.formssi.mall.goods.domain.service.GmsCategoryDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:prms
 * @create: 2022-04-19 17:09
 * @version: 1.0
 */
@Service
@Slf4j
public class GmsCategoryDomainImpl implements GmsCategoryDomain {

    @Resource
    GmsCategoryRepository gmsCategoryRepository;


    @Override
    public List<GmsCategoryDo> listCate() {
        return gmsCategoryRepository.listCate();
    }
}
