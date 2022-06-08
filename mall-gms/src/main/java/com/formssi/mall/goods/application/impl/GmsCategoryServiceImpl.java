package com.formssi.mall.goods.application.impl;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.gms.dto.GmsCategoryDTO;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import com.formssi.mall.gms.query.GmsCategoryPageQry;
import com.formssi.mall.gms.query.GmsSpuPageByCateQry;
import com.formssi.mall.goods.application.GmsCategoryService;
import com.formssi.mall.goods.domain.entity.GmsCategoryDo;
import com.formssi.mall.goods.domain.entity.GmsSpuDO;
import com.formssi.mall.goods.domain.service.GmsCategoryDomain;
import com.formssi.mall.goods.domain.service.IGmsSpuDomainService;
import com.formssi.mall.redis.service.RedisService;
import com.formssi.mall.redis.service.RedissonLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author:prms
 * @create: 2022-04-19 16:55
 * @version: 1.0
 */
@Service
@Slf4j
public class GmsCategoryServiceImpl implements GmsCategoryService {

    @Resource
    GmsCategoryDomain gmsCategoryDomain;

    @Resource
    IGmsSpuDomainService gmsSpuDomainService;

    @Resource
    RedisService template;

    @Resource
    RedissonLockService redissonLockService;

    private final static String GMS_CATE_LOCK_KEY = "gms_cate_lock_key";

    private final static String GMS_CATE_VALUE_KEY = "gms_cate_value_key";

    /**
     * 查询商品分类树
     *
     * @param qry
     * @return
     */
    @Override
    public List<GmsCategoryDTO> cateTreeNode(GmsCategoryPageQry qry) {
        List<GmsCategoryDTO> rList = (List<GmsCategoryDTO>) template.get(GMS_CATE_VALUE_KEY);
        if (rList == null || rList.isEmpty()) {
            try {
                boolean lock = redissonLockService.tryLock(GMS_CATE_LOCK_KEY, 3000);
                if (lock) {
                    rList = (List<GmsCategoryDTO>) template.get(GMS_CATE_VALUE_KEY);
                    if (rList == null || rList.isEmpty()) {
                        List<GmsCategoryDo> list = gmsCategoryDomain.listCate();
                        List<GmsCategoryDTO> collect = list.parallelStream()
                                .filter(item -> item.getParentId() == 0)
                                .map(cateListFunction(list)).collect(Collectors.toList());
                        template.set(GMS_CATE_VALUE_KEY, collect);
                        return collect;
                    }
                    return rList;
                }
            } finally {
                redissonLockService.unlock(GMS_CATE_LOCK_KEY);
            }
        }
        return rList;
    }

    /**
     * 筛选子分类
     *
     * @param id
     * @param list
     * @return
     */
    private List<GmsCategoryDTO> getSubNodes(Long id, List<GmsCategoryDo> list) {
        List<GmsCategoryDTO> collect = list.parallelStream()
                .filter(item -> id.equals(item.getParentId()))
                .map(cateListFunction(list)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 函数式方法提取
     *
     * @param list
     * @return
     */
    private Function<GmsCategoryDo, GmsCategoryDTO> cateListFunction(List<GmsCategoryDo> list) {
        return item -> {
            GmsCategoryDTO dto = new GmsCategoryDTO();
            BeanCopyUtils.copyProperties(item, dto);
            dto.setZList(getSubNodes(item.getId(), list));
            return dto;
        };
    }

    /**
     * 查询商品列表
     *
     * @param body
     * @return
     */
    @Override
    public CommonPage<GmsSpuDTO> gmsListSpuByCateId(GmsSpuPageByCateQry body) {
        CommonPage<GmsSpuDO> page = gmsSpuDomainService.gmsListSpuByCateId(body);
        return BeanCopyUtils.copyProperties(page, new CommonPage<GmsSpuDTO>());
    }


}
