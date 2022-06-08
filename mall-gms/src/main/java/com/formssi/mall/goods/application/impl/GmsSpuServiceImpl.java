package com.formssi.mall.goods.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.util.BeanCopyUtils;
import com.formssi.mall.exception.define.ResultCodeEnum;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.gms.cmd.GmsSpuCmd;
import com.formssi.mall.gms.dto.GmsSkuDto;
import com.formssi.mall.gms.dto.GmsSpuAttributeDTO;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import com.formssi.mall.gms.query.GmsSpuDetailQry;
import com.formssi.mall.gms.query.GmsSpuQry;
import com.formssi.mall.gms.query.GmsSpuSearchQry;
import com.formssi.mall.gms.vo.GmsSpuDetailVO;
import com.formssi.mall.goods.application.IGmsSpuService;
import com.formssi.mall.goods.domain.entity.GmsSkuDO;
import com.formssi.mall.goods.domain.entity.GmsSpuAttributeDO;
import com.formssi.mall.goods.domain.entity.GmsSpuDO;
import com.formssi.mall.goods.domain.repository.IGmsSpuRepository;
import com.formssi.mall.goods.domain.service.GmsSkuDomain;
import com.formssi.mall.goods.domain.service.GmsSpuAttributeDomain;
import com.formssi.mall.goods.domain.service.IGmsSpuDomainService;
import com.formssi.mall.goods.infrastructure.util.PageComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台商品 应用服务实现类
 * </p>
 *
 * @author hudemin
 * @since 2022-04-18 20:01:13
 */
@Transactional
@Service
@Slf4j
public class GmsSpuServiceImpl implements IGmsSpuService {

    @Autowired
    private IGmsSpuRepository iGmsSpuRepository;

    @Autowired
    private PageComponent<GmsSpuDO> pageComponent;

    @Autowired
    private IGmsSpuDomainService iGmsSpuDomainService;

    @Resource
    GmsSkuDomain gmsSkuDomain;

    @Resource
    GmsSpuAttributeDomain gmsSpuAttributeDomain;

    @Resource
    RestHighLevelClient client;

    @Override
    public CommonPage<GmsSpuDTO> listSpus(GmsSpuQry gmsSpuQry) {
        QueryWrapper<GmsSpuDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().like(StringUtils.isNotBlank(gmsSpuQry.getSpuName()), GmsSpuDO::getName, gmsSpuQry.getSpuName());
        CommonPage<GmsSpuDO> page = pageComponent.getPage(wrapper, iGmsSpuRepository.getBaseMapper(), gmsSpuQry.getCurrent(), gmsSpuQry.getSize());
        return BeanCopyUtils.copyProperties(page, new CommonPage<>());
    }

    public void addSpu(GmsSpuCmd gmsSpuCmd) {
        // 新增商品
        GmsSpuDO gmsSpuDO = iGmsSpuDomainService.addSpu(gmsSpuCmd);
        // 新增sku
        iGmsSpuDomainService.addSku(gmsSpuDO.getId(), gmsSpuCmd.getGmsSkuCmdList());
    }

    public void updateSpu(GmsSpuCmd gmsSpuCmd) {
        // 修改商品
        GmsSpuDO gmsSpuDO = iGmsSpuDomainService.updateSpu(gmsSpuCmd);
        // 新增或者修改sku，id存在就是修改，反之是修改。删除的逻辑是软删除，删除标记值有前端传入
        iGmsSpuDomainService.addOrUpdateSku(gmsSpuDO.getId(), gmsSpuCmd.getGmsSkuCmdList());
    }

    public void operateSpu22(GmsSpuCmd gmsSpuCmd) {
        // 修改商品
        GmsSpuDO gmsSpuDO = iGmsSpuDomainService.updateSpu(gmsSpuCmd);
        // 新增或者修改sku，id存在就是修改，反之是修改。删除的逻辑是软删除，删除标记值有前端传入
        iGmsSpuDomainService.addOrUpdateSku(gmsSpuDO.getId(), gmsSpuCmd.getGmsSkuCmdList());
    }

    public void operateSpu(Integer operateType, List<GmsSpuCmd> gmsSpuCmdList) {
        iGmsSpuDomainService.operateSpu(operateType,gmsSpuCmdList);
    }

    /**
     * 异步查询商品详情
     *
     * @param qry
     * @return
     */
    @Override
    public GmsSpuDetailVO spuDetail(GmsSpuDetailQry qry) {
        Future<GmsSpuDO> spuDOFuture = iGmsSpuDomainService.getSpuDetail(qry);
        Future<List<GmsSkuDO>> skuDOFuture = gmsSkuDomain.getSkuDetail(qry);
        Future<List<GmsSpuAttributeDO>> attrFuture = gmsSpuAttributeDomain.getAttr(qry);
        GmsSpuDetailVO vo = new GmsSpuDetailVO();
        try {
            GmsSpuDO gmsSpuDO = spuDOFuture.get(10, TimeUnit.SECONDS);
            List<GmsSkuDO> gmsSkuDO = skuDOFuture.get(10, TimeUnit.SECONDS);
            List<GmsSpuAttributeDO> attributeDOS = attrFuture.get(10, TimeUnit.SECONDS);
            GmsSpuDTO gmsSpuDTO = BeanCopyUtils.copyProperties(gmsSpuDO, new GmsSpuDTO());
            List<GmsSkuDto> gmsSkuDtos = BeanCopyUtils.copyListProperties(gmsSkuDO, GmsSkuDto.class);
            List<GmsSpuAttributeDTO> attrList = BeanCopyUtils.copyListProperties(attributeDOS, GmsSpuAttributeDTO.class);
            vo.setGmsSpuDTO(gmsSpuDTO);
            vo.setSkuList(gmsSkuDtos);
            vo.setAttrList(attrList);
        } catch (Exception e) {
            log.info("查询商品详情异常");
            throw new BusinessException(ResultCodeEnum.SPU_ERROR.getCode(), "查询商品详情异常");
        }
        return vo;
    }

    @Override
    public List<GmsSpuDTO> searchSpuBykey(GmsSpuSearchQry req) {
        String keyword = req.getKeyword();
        SearchSourceBuilder sc = new SearchSourceBuilder();
        sc.query(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("sub_title",keyword))
                .should(QueryBuilders.matchQuery("name",keyword))
                .should(QueryBuilders.matchQuery("introduce",keyword))
                .should(QueryBuilders.matchQuery("introduce",keyword))
                .should(QueryBuilders.matchQuery("sub_title",keyword)));
        SearchRequest gmsSpu = new SearchRequest("gms_spu");
        sc.size((int) req.getSize());
        sc.from((int) ((req.getCurrent() - 1) * req.getSize()));
        gmsSpu.source(sc);
        SearchResponse response = null;
        try {
            response = client.search(gmsSpu, RequestOptions.DEFAULT);
            if (RestStatus.OK.equals(response.status())) {
                List<String> collect = Arrays.stream(response.getHits().getHits()).map(item -> item.getId()).collect(Collectors.toList());
                log.info(String.valueOf(collect));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // todo

        return null;
    }
}