package com.formssi.mall.order.domain.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.order.domain.repository.ElasticsearchOrderRepository;
import com.formssi.mall.order.domain.service.ElasticsearchOrderService;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderQueryVo;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderVo;
import com.formssi.mall.order.domain.vo.MyPageableRequest;
import com.formssi.mall.order.infrastructure.es.SimpleSearchQueryEngine;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.searchafter.SearchAfterBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ElasticsearchOrderServiceImpl implements ElasticsearchOrderService {

    @Autowired
    private SimpleSearchQueryEngine simpleSearchQueryEngine;

    @Autowired
    private ElasticsearchOrderRepository elasticsearchOrderRepository;

    @Override
    public ElasticsearchOrderVo save(ElasticsearchOrderVo elasticsearchOrderVo) {
        return elasticsearchOrderRepository.save(elasticsearchOrderVo);
    }

    @Override
    public Iterable<ElasticsearchOrderVo>  saveAll(List<ElasticsearchOrderVo> elasticsearchOrderVoList) {
        return elasticsearchOrderRepository.saveAll(elasticsearchOrderVoList);
    }

    @Override
    public Page findElasticsearchOrderVoList(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo) {
        SearchHits<ElasticsearchOrderVo> searchHits =  simpleSearchQueryEngine.findPage(elasticsearchOrderQueryVo,
                ElasticsearchOrderVo.class,
                PageRequest.of(elasticsearchOrderQueryVo.getPage(),elasticsearchOrderQueryVo.getSize(),Sort.by(Sort.Order.desc("orderId"),Sort.Order.desc("createTime"))));
        Page page = new Page(elasticsearchOrderQueryVo.getPage(),elasticsearchOrderQueryVo.getSize(),searchHits.getTotalHits());
        page.setRecords(searchHits.get().collect(Collectors.toList()));
        return page;
    }

    @Override
    public Page findOrderVoListBySearchAfter(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo) {
        SearchSourceBuilder searchSourceBuilder =  new SearchSourceBuilder();
        List<String> sortValues = elasticsearchOrderQueryVo.getSortValues();
        searchSourceBuilder.from(0); //from不用传值，默认是-1
        searchSourceBuilder.size(elasticsearchOrderQueryVo.getSize());
        searchSourceBuilder.sort("orderId").sort("createTime");//SortOrder.DESC
        if (CollectionUtils.isNotEmpty(sortValues)){
            searchSourceBuilder.searchAfter(sortValues.toArray());
        }
        org.elasticsearch.search.SearchHits searchHits = simpleSearchQueryEngine.findPage(elasticsearchOrderQueryVo,searchSourceBuilder);
        //处理高亮标签
        List<SearchHit> searchHitList = Arrays.stream(searchHits.getHits()).collect(Collectors.toList());
        searchHitList.stream().peek(hit->{
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            sourceAsMap.forEach((sourceKey,sourceVal)->{
                HighlightField highlightField = highlightFields.get(sourceKey);
                if (highlightField != null){
                    //拿到的高亮字段覆盖sourceAsMap中的val
                    sourceAsMap.put(sourceKey,Arrays.stream(highlightField.getFragments()).map(map->map.toString()).collect(Collectors.joining("")));
                }
            });
        }).collect(Collectors.toList());
        Page page = new Page(0,elasticsearchOrderQueryVo.getSize(),searchHits.getTotalHits().value);
        page.setRecords(searchHitList);
        return page;
    }
}
