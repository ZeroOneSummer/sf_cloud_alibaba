package com.formssi.mall.order.infrastructure.es;

import com.formssi.mall.order.infrastructure.enumeration.EsSortEnum;
import com.formssi.mall.order.infrastructure.es.annotation.*;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.searchafter.SearchAfterBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.HighlightQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 条件构造器转换类
 * QueryBuilder.matchAllQuery(); 匹配所有
 * QueryBuilder.ermQuery精准匹配(); 大小写敏感且不支持
 * QueryBuilder.matchPhraseQuery(); 对中文精确匹配
 * QueryBuilder.matchQuery();单个匹配, field不支持通配符, 前缀具高级特性
 * QueryBuilder.multiMatchQuery("text", "field1", "field2"..); 匹配多个字段
 *
 *参考ES官方文档：https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-query-builders.html
 */
public class EsQueryParse {

    /**
     * form+size条件构造器
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Query convertNativeSearchQuery(T t) {
        try {
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.requireFieldMatch(false);
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Object value = ClassUtils.getPublicMethod(clazz, "get" + captureName(field.getName())).invoke(t);
                //高亮不需要用到val,条件必须放到前面
                if (field.isAnnotationPresent(EsHighlight.class)){
                    EsHighlight esHighlight = field.getAnnotation(EsHighlight.class);
                    String filedName = StringUtils.isBlank(esHighlight.name()) ? field.getName() : esHighlight.name();
                    highlightBuilder.field(filedName,esHighlight.fragmentSize(),esHighlight.numberOfFragments(),esHighlight.fragmentOffset());
                }
                if (value == null) {
                    continue;
                }
                if (field.isAnnotationPresent(EsLike.class)) {
                    WildcardQueryBuilder query = getLikeQuery(field, (String) value);
                    boolQueryBuilder.must(query);
                }
                if (field.isAnnotationPresent(EsEquals.class)) {
                    MatchPhraseQueryBuilder query = getEqualsQuery(field, value);
                    boolQueryBuilder.must(query);
                }
                if (field.isAnnotationPresent(EsRange.class)) {
                    RangeQueryBuilder rangeQueryBuilder = getRangeQuery(field, value);
                    boolQueryBuilder.must(rangeQueryBuilder);
                }
                if (field.isAnnotationPresent(EsIn.class)) {
                    TermsQueryBuilder query = getInQuery(field, (List<?>) value);
                    boolQueryBuilder.must(query);
                }
            }
            if (clazz.isAnnotationPresent(EsHighlightStyled.class)){
                EsHighlightStyled esHighlightStyled = clazz.getAnnotation(EsHighlightStyled.class);
                highlightBuilder.postTags(esHighlightStyled.postTags());
                highlightBuilder.preTags(esHighlightStyled.preTags());
                queryBuilder.withHighlightBuilder(highlightBuilder);
            }
            return queryBuilder.withQuery(boolQueryBuilder).build();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Query.findAll();
    }


    /**
     * search_after条件构造器
     * @param t
     * @param searchSourceBuilder
     * @param <T>
     * @return
     */
    public static <T> SearchRequest convertSearchSourceQuery(T t, SearchSourceBuilder searchSourceBuilder){
        SearchRequest request = new SearchRequest();
        try {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Object value = ClassUtils.getPublicMethod(clazz, "get" + captureName(field.getName())).invoke(t);
                //高亮不需要用到val,条件必须放到前面
                if (field.isAnnotationPresent(EsHighlight.class)){
                    EsHighlight esHighlight = field.getAnnotation(EsHighlight.class);
                    String filedName = StringUtils.isBlank(esHighlight.name()) ? field.getName() : esHighlight.name();
                    highlightBuilder.field(filedName,esHighlight.fragmentSize(),esHighlight.numberOfFragments(),esHighlight.fragmentOffset());
                }
                if (value == null) {
                    continue;
                }
                if (field.isAnnotationPresent(EsLike.class)) {
                    WildcardQueryBuilder query = getLikeQuery(field, (String) value);
                    boolQueryBuilder.must(query);
                }
                if (field.isAnnotationPresent(EsEquals.class)) {
                    MatchPhraseQueryBuilder query = getEqualsQuery(field, value);
                    boolQueryBuilder.must(query);
                }
                if (field.isAnnotationPresent(EsRange.class)) {
                    RangeQueryBuilder rangeQueryBuilder = getRangeQuery(field, value);
                    boolQueryBuilder.must(rangeQueryBuilder);
                }
                if (field.isAnnotationPresent(EsIn.class)) {
                    TermsQueryBuilder query = getInQuery(field, (List<?>) value);
                    boolQueryBuilder.must(query);
                }
            }
            if (clazz.isAnnotationPresent(Document.class)){
                Document document = clazz.getAnnotation(Document.class);
                request.indices(document.indexName());
            }
            if (clazz.isAnnotationPresent(EsHighlightStyled.class)){
                EsHighlightStyled esHighlightStyled = clazz.getAnnotation(EsHighlightStyled.class);
                highlightBuilder.postTags(esHighlightStyled.postTags());
                highlightBuilder.preTags(esHighlightStyled.preTags());
                searchSourceBuilder.highlighter(highlightBuilder);
            }
            searchSourceBuilder.query(boolQueryBuilder);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * 一次匹配多个值
     * @param field
     * @param value
     * @return
     */
    private static TermsQueryBuilder getInQuery(Field field, List<?> value) {
        EsIn esIn = field.getAnnotation(EsIn.class);
        String filedName = StringUtils.isBlank(esIn.name()) ? field.getName() : esIn.name();
        return QueryBuilders.termsQuery(filedName, value);
    }

    /**
     * 大小范围查询
     * @param field
     * @param value
     * @return
     */
    private static RangeQueryBuilder getRangeQuery(Field field, Object value) {
        EsRange esRange = field.getAnnotation(EsRange.class);
        String filedName = StringUtils.isBlank(esRange.name()) ? field.getName() : esRange.name();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(filedName)
                .includeLower(esRange.includeLower())
                .includeUpper(esRange.includeUpper());
        if (esRange.lt()) {
            rangeQueryBuilder.lt(value);
        }
        if (esRange.gt()) {
            rangeQueryBuilder.gt(value);
        }
        return rangeQueryBuilder;
    }

    /**
     * 分词短语匹配：QueryBuilders。matchQuery 会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
     * 精准匹配短语: QueryBuilders.matchPhraseQuery()
     * 完全匹配： QueryBuilders.termQuery()
     * @param field
     * @param value
     * @return
     */
    private static MatchPhraseQueryBuilder getEqualsQuery(Field field, Object value) {
        EsEquals esEquals = field.getAnnotation(EsEquals.class);
        String filedName = StringUtils.isBlank(esEquals.name()) ? field.getName() : esEquals.name();
        return QueryBuilders.matchPhraseQuery(filedName, value);
    }

    /**
     * 模糊查询，?匹配单个字符，*匹配多个字符
     * @param field
     * @param likeValue
     * @return
     */
    private static WildcardQueryBuilder getLikeQuery(Field field, String likeValue) {
        EsLike esLike = field.getAnnotation(EsLike.class);
        String filedName = StringUtils.isBlank(esLike.name()) ? field.getName() : esLike.name();
        if (esLike.leftLike()) {
            likeValue = "%" + likeValue;
        }
        if (esLike.rightLike()) {
            likeValue = likeValue + "%";
        }
        return QueryBuilders.wildcardQuery(filedName, likeValue);
    }

    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
