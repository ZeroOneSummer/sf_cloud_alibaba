package com.formssi.mall.order.infrastructure.es;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.searchafter.SearchAfterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

public abstract class AbstractSearchQueryEngine<T,R> {

    @Autowired
    protected ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * from+size 分页查询
     * @param requestPara
     * @param clazz
     * @param pageable
     * @return
     */
    public abstract SearchHits<R> findPage(T requestPara, Class<R> clazz, Pageable pageable);


    /**
     * searchAfter分页查询
     * @param requestPara
     * @param searchSourceBuilder
     * @return
     */
    public abstract org.elasticsearch.search.SearchHits  findPage(T requestPara, SearchSourceBuilder searchSourceBuilder);

    //todo Scroll分页...

}
