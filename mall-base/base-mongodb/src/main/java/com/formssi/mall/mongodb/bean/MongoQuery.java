package com.formssi.mall.mongodb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class MongoQuery {

    private List<MongoCondition> conditionList;

    public List<MongoCondition> getConditionList() {
        return conditionList;
    }

    public static MongoQuery create(){
        return new MongoQuery();
    }

    public static Query createQuery(){
        return new Query();
    }

    //默认IS
    public MongoQuery append(String key, Object value){
        return append(key, value, OptionType.IS);
    }

    public MongoQuery append(String key, Object value, @NonNull OptionType type){
        if (CollectionUtils.isEmpty(conditionList)){
            conditionList = new ArrayList<>();
        }
        conditionList.add(new MongoCondition(key, value, type));
        return this;
    }

    public void clear(){
        if (!CollectionUtils.isEmpty(conditionList)) conditionList.clear();
    }

    //默认IS
    public static Criteria add(String key, Object value){
        return add(key, value, OptionType.IS);
    }

    public static Criteria add(String key, Object value, @NonNull OptionType type){
        Assert.hasLength(key, "key is not null or empty.");
        return getCriteriaByOptionType(key, value, type);
    }

    public static Criteria getCriteriaByOptionType(String key, Object value, OptionType type) {
        Criteria criteria = null;
        switch (type){
            case IS:
                criteria = Criteria.where(key).is(value);
                break;
            case NE:
                criteria = Criteria.where(key).ne(value);
                break;
            case IN:
                if (value instanceof List){
                    List values = (List)value;
                    criteria = Criteria.where(key).in(values.toArray());
                }else{
                    criteria = Criteria.where(key).in(value);
                }
                break;
            case NIN:
                criteria = Criteria.where(key).nin(value);
                break;
            case GT:
                criteria = Criteria.where(key).gt(value);
                break;
            case LT:
                criteria = Criteria.where(key).lt(value);
                break;
            case GTE:
                criteria = Criteria.where(key).gte(value);
                break;
            case LTE:
                criteria = Criteria.where(key).lte(value);
                break;
            case LIKE:
                criteria = Criteria.where(key).regex(value.toString());
                break;
            default:
                throw new RuntimeException("OptionType is undefined!");
        }
        return criteria;
    }

    /**
     * 逻辑运算字段映射实体
     * key      字段名
     * value    字段值
     * type     操作类型
     */
    @Data
    @AllArgsConstructor
    public static class MongoCondition {

        @NonNull
        private String key;
        //模糊查询，传正则表达式
        private Object value;
        @NonNull
        private OptionType type;

        public static Criteria create(){
            return new Criteria();
        }
    }

    /**
     * 逻辑运算符枚举
     */
    @Getter
    @AllArgsConstructor
    public enum OptionType {
        IS("IS", "="),
        NE("NE", "!="),
        IN("IN", "in"),
        NIN("NIN", "not in"),
        GT("GT", ">"),
        LT("LT", "<"),
        GTE("GTE", ">="),
        LTE("LTE", "<="),
        LIKE("LIKE", "like"),
        ;
        private final String name;
        private final String desc;
    }

    /**
     * 排序内部类，枚举无法动态添加排序字段
     */
    @Data
    @AllArgsConstructor
    public final static class OrderBy {

        private Sort.Direction orderType;

        private String[] field;

        public static OrderBy ASC(String...field){
            return new OrderBy(Sort.Direction.ASC, field);
        }

        public static OrderBy DESC(String...field){
            return new OrderBy(Sort.Direction.DESC, field);
        }
    }
}
