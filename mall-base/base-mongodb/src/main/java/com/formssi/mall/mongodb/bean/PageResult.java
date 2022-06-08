package com.formssi.mall.mongodb.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class PageResult<T> {

    /**
     * 当前页
     */
    public Integer currentPage;

    /**
     * 页面容量
     */
    public Integer pageSize;

    /**
     * 总页数
     */
    public Long totalPage;

    /**
     * 总记录数
     */
    public Long total;

    /**
     * 偏移量
     */
    private Long skip;

    /**
     * 排序
     */
    private Sort sort;

    /**
     * 数据
     */
    private List<T> data;

    public PageResult<T> setTotal(Long total){
        this.total = total;
        this.totalPage = getPageSize() == 0 ? 0 :
                getTotal() % getPageSize() == 0 ? (getTotal() / getPageSize()) : (getTotal() / getPageSize() + 1);
        return this;
    }

    public static Query addPage(Integer currentPage, Integer pageSize, MongoQuery.OrderBy orderBy){
        Query query = new Query();
        if (!ObjectUtils.isEmpty(currentPage) && !ObjectUtils.isEmpty(pageSize)){
            query.limit(pageSize);
            query.skip(Integer.toUnsignedLong(pageSize * (currentPage - 1)));
        }
        if (orderBy != null && ArrayUtils.isNotEmpty(orderBy.getField())){
            query.with(Sort.by(orderBy.getOrderType(), orderBy.getField()));
        }
        return query;
    }

    public static Query resetPage(Query query){
        if (Objects.nonNull(query)){
            query.limit(0);
            query.skip(0);
        }
        return query;
    }
}
