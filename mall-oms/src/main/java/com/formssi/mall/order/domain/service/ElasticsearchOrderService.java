package com.formssi.mall.order.domain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderQueryVo;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderVo;
import com.formssi.mall.order.infrastructure.es.SimpleSearchQueryEngine;

import java.util.List;

public interface ElasticsearchOrderService {


    /**
     * 单个保存
     * @param elasticsearchOrderVo
     * @return
     */
    ElasticsearchOrderVo save(ElasticsearchOrderVo elasticsearchOrderVo);

    /**
     * 批量保存
     * @param elasticsearchOrderVoList
     * @return
     */
    Iterable<ElasticsearchOrderVo> saveAll(List<ElasticsearchOrderVo> elasticsearchOrderVoList);


    /**
     * 查询
     * @param elasticsearchOrderQueryVo
     * @return
     */
    Page findElasticsearchOrderVoList(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo);


    /**
     * search_after分页
     * @param elasticsearchOrderQueryVo
     * @return
     */
    Page findOrderVoListBySearchAfter(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo);

}
