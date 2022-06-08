package com.formssi.mall.order.infrastructure.es;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.formssi.mall.order.domain.repository.ElasticsearchOrderRepository;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.lucene.queries.SearchAfterSortedDocQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.searchafter.SearchAfterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Slf4j
@Component
public class SimpleSearchQueryEngine extends AbstractSearchQueryEngine {

    @Value("${spring.elasticsearch.rest.uris}")
    private List<String> nodes;

    @Autowired
    private ElasticsearchOrderRepository elasticsearchOrderRepository;

    @Override
    public SearchHits findPage(Object requestPara, Class clazz, Pageable pageable) {
        Query query = EsQueryParse.convertNativeSearchQuery(requestPara);
        //组装分页
        query.setPageable(pageable);
        return elasticsearchRestTemplate.search(query, clazz);
    }

    @Override
    public org.elasticsearch.search.SearchHits findPage(Object requestPara, SearchSourceBuilder searchSourceBuilder) {
        String[] hosts = nodes.get(0).split(":");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hosts[0],Integer.parseInt(hosts[1]),HttpHost.DEFAULT_SCHEME_NAME)));
        SearchRequest request = EsQueryParse.convertSearchSourceQuery(requestPara,searchSourceBuilder);
        request.source(searchSourceBuilder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return Optional.ofNullable(response).map(SearchResponse::getHits).orElseThrow(()->new IllegalStateException("ES服务器异常"));
        } catch (IOException e) {
            log.error("SimpleSearchQueryEngine.findPage:e{}",e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}
