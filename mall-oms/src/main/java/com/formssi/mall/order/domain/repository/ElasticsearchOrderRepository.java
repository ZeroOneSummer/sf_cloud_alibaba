package com.formssi.mall.order.domain.repository;

import com.formssi.mall.order.domain.vo.ElasticsearchOrderVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface ElasticsearchOrderRepository extends ElasticsearchRepository<ElasticsearchOrderVo, String> {
}
