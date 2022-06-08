package com.formssi.mall.mongodb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.formssi.mall.mongodb.bean.MongoQuery;
import com.formssi.mall.mongodb.bean.PageResult;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.NonNull;
import org.apache.commons.lang.ArrayUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.formssi.mall.mongodb.bean.MongoQuery.OrderBy;
import static com.formssi.mall.mongodb.bean.MongoQuery.getCriteriaByOptionType;

public class MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    //------------------- collection --------------------------

    /**
     * 功能描述: 创建集合
     * @param collectionName 集合名称，相当于传统数据库的表名
     */
    public void createCollection(String collectionName) {
        mongoTemplate.createCollection(collectionName);
    }

    /**
     * 功能描述: 删除集合
     * @param collectionName 集合名称
     */
    public void dropCollection(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
    }

    /**
     * 功能描述: 集合是否存在
     * @param collectionName 集合名称
     */
    public Boolean existsCollection(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    /**
     * 功能描述: 创建普通索引
     * 索引是顺序排列
     * @param collectionName 集合名称，相当于关系型数据库中的表名
     * @param filedName      对象中的某个属性名
     */
    public String createIndex(String collectionName, String filedName) {
        IndexOptions options = new IndexOptions();
        //创建按filedName升序排的索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(filedName), options);
    }

    /**
     * 功能描述: 创建唯一索引
     * 索引是顺序排列，且唯一的索引
     * @param collectionName 集合名称，相当于关系型数据库中的表名
     * @param filedName      对象中的某个属性名
     */
    public String createUniqueIndex(String collectionName, String filedName) {
        IndexOptions options = new IndexOptions();
        // 设置为唯一
        options.unique(true);
        //创建按filedName升序排的索引
        return mongoTemplate.getCollection(collectionName).createIndex(Indexes.ascending(filedName), options);
    }

    /**
     * 功能描述: 获取当前集合对应的所有索引的名称
     * @param collectionName 集合名称
     */
    public List<String> getAllIndexs(String collectionName) {
        ListIndexesIterable<Document> list = mongoTemplate.getCollection(collectionName).listIndexes();
        //上面的list不能直接获取size，因此初始化arrayList就不设置初始化大小了
        List<String> indexes = new ArrayList<>();
        for (Document document : list) {
            document.forEach((k, v) -> {
                //提取出索引的名称
                if (k.equals("name")) {
                    indexes.add(v.toString());
                }
            });
        }
        return indexes;
    }

    /**
     * 功能描述: 删除索引
     * @param collectionName 集合名称
     * @param indexName 索引名称
     */
    public void deleteIndex(String collectionName, String indexName) {
        mongoTemplate.getCollection(collectionName).dropIndex(indexName);
    }


    //------------------- CRUD ------------------------

    /**
     * 功能描述: 往对应的集合中插入一条数据并返回
     * @param t              存储对象
     * @param collectionName 集合名称
     */
    public <T> T insert(T t, String collectionName) {
        return mongoTemplate.insert(t, collectionName);
    }

    /**
     * 功能描述: 往对应的集合中插入一条数据并返回
     * @param t  存储对象
     */
    public <T> T insert(T t) {
        return mongoTemplate.insert(t);
    }

    /**
     * 功能描述: 往对应的集合中插入一条数据(存在则修改)并返回
     * @param t  存储对象
     */
    public <T> T saveOrUpdate(T t) {
        return mongoTemplate.save(t);
    }

    /**
     * 功能描述: 批量插入数据，不要包含重复的id
     * @param list 对象列表
     */
    public <T> Collection<T> insertMulti(List<T> list, String collectionName) {
        return mongoTemplate.insert(list, collectionName);
    }

    /**
     * 功能描述: 批量插入数据，不要包含重复的id
     * @param list 对象列表
     * @param clazz 集合对应的反射对象
     */
    public <T> Collection<T> insertMulti(List<T> list, Class<?> clazz) {
        return mongoTemplate.insert(list, clazz);
    }

    /**
     * 功能描述: 原生删除
     * @param query 原生条件
     * @param t     更新对象
     * @param clazz 集合对象的反射
     */
    public <T> UpdateResult updateByNative(Query query, @NonNull T t, Class<?> clazz) {
        Update update = new Update();
        String json = JSON.toJSONString(t);
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.forEach((key, value) -> {
            //不更新主键，主键用原字段
            if (!key.equals(getRealIdName(clazz, false))) {
                update.set(key, value);
            }
        });
        return mongoTemplate.updateFirst(query, update, clazz);
    }

    /**
     * 功能描述: 根据ID更新
     * @param id 主键ID
     * @param t 更新对象
     * @param clazz 集合对象的反射
     */
    public <T> UpdateResult updateById(String id, @NonNull T t, Class<?> clazz) {
        Query query = new Query();
        query.addCriteria(Criteria.where(getRealIdName(clazz)).is(id));
        Update update = new Update();
        String json = JSON.toJSONString(t);
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.forEach((key, value) -> {
            //不更新主键，主键用原字段
            if (!key.equals(getRealIdName(clazz, false))) {
                update.set(key, value);
            }
        });
        return mongoTemplate.updateFirst(query, update, clazz);
    }

    /**
     * 功能描述: 条件更新，无则插入
     * @param mongoQuery 查询条件
     * @param t 更新对象
     * @param clazz 集合对象的反射
     */
    public <T> UpdateResult updateOrInsert(MongoQuery mongoQuery, @NonNull T t, Class<?> clazz) {
        Query query = new Query();
        if (mongoQuery != null && mongoQuery.getConditionList() != null) {
            mongoQuery.getConditionList().forEach(condition -> query.addCriteria(getCriteriaByOptionType(condition.getKey(), condition.getValue(), condition.getType())));
        }
        Update update = new Update();
        String json = JSON.toJSONString(t);
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.forEach((key, value) -> {
            //不更新主键，主键用原字段
            if (!key.equals(getRealIdName(clazz, false))) {
                update.set(key, value);
            }
        });
        UpdateResult result = mongoTemplate.upsert(query, update, clazz);
        mongoQuery.clear();
        return result;
    }

    /**
     * 功能描述: 根据ID批量更新
     * @param id 主键ID
     * @param t 更新对象
     */
    public <T> UpdateResult updateMultiById(String id, @NonNull T t) {
        Query query = new Query(Criteria.where(getRealIdName(t.getClass())).is(id));
        Update update = new Update();
        String json = JSON.toJSONString(t);
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.forEach((key, value) -> {
            //不更新主键，主键用原字段
            if (!key.equals(getRealIdName(t.getClass(), false))) {
                update.set(key, value);
            }
        });
        return mongoTemplate.updateMulti(query, update, t.getClass());
    }

    /**
     * 功能描述: 根据ID删除
     * @param id 主键ID
     * @param clazz 对象类型
     */
    public <T> DeleteResult deleteById(String id, Class<T> clazz) {
        Query query = new Query(Criteria.where(getRealIdName(clazz)).is(id));
        // mongodb在删除对象的时候会判断对象类型，如果你不传入对象类型，只传入了集合名称，它是找不到的
        return mongoTemplate.remove(query, clazz);
    }

    /**
     * 功能描述: 根据对象删除
     * @param obj 对象类型
     */
    public <T> DeleteResult deleteObject(Object obj) {
        return mongoTemplate.remove(obj);
    }

    /**
     * 功能描述: 原生删除
     * @param query 原生条件
     * @param clazz 对象类型
     */
    public <T> DeleteResult deleteByNative(Query query, Class<T> clazz) {
        return mongoTemplate.remove(query, clazz);
    }

    /**
     * 功能描述: 根据ID查询
     * @param id      主键id
     * @param clazz   返回值的反射对象
     */
    public <T> T findById(Object id, Class<T> clazz) {
        return mongoTemplate.findById(id, clazz);
    }

    /**
     * 功能描述: 查询所有
     * @param clazz   对象类型
     */
    public <T> List<T> findAll(Class<T> clazz) {
        PageResult<T> pageResult = findAll(clazz, null, null);
        return pageResult.getData();
    }

    /**
     * 功能描述: 分页查询
     * @param clazz    对象类型
     */
    public <T> PageResult<T> findAll(Class<T> clazz, Integer currentPage, Integer pageSize) {
        Query query = PageResult.addPage(currentPage, pageSize, null);
        List<T> list = mongoTemplate.find(query, clazz);
        //setTotal一定要放到最后面
        return new PageResult<T>().setCurrentPage(currentPage).setPageSize(query.getLimit())
                .setSkip(query.getSkip()).setTotal(findCount(query, clazz)).setData(list);
    }

    /**
     * 功能描述: 原生总数查询
     * @param query      原生条件
     * @param clazz      对象类型
     */
    public <T> Long findCount(Query query, Class<T> clazz) {
        return mongoTemplate.count(PageResult.resetPage(query), clazz);
    }

    /**
     * 功能描述: 复杂条件查询
     * @param mongoQuery      查询条件
     * @param clazz           对象类型
     */
    public <T> List<T> findByCondition(MongoQuery mongoQuery, Class<T> clazz) {
        PageResult<T> pageResult = findByCondition(mongoQuery, clazz, null, null, null);
        return pageResult.getData();
    }

    /**
     * 功能描述: 复杂条件+分页查询
     * @param mongoQuery      查询条件
     * @param clazz           对象类型
     */
    public <T> PageResult<T> findByCondition(MongoQuery mongoQuery,
                                           Class<T> clazz,
                                           Integer currentPage,
                                           Integer pageSize) {
        return findByCondition(mongoQuery, clazz, currentPage, pageSize, null);
    }

    /**
     * 功能描述: 复杂条件+分页查询+排序
     * @param mongoQuery      查询条件
     * @param clazz           对象类型
     * @param orderBy         排序枚举
     */
    public <T> PageResult<T> findByCondition(MongoQuery mongoQuery,
                                           Class<T> clazz,
                                           Integer currentPage,
                                           Integer pageSize,
                                           OrderBy orderBy) {
        if (ObjectUtils.isEmpty(mongoQuery) || CollectionUtils.isEmpty(mongoQuery.getConditionList())) {
            return findAll(clazz, currentPage, pageSize);
        }else {
            Query query = PageResult.addPage(currentPage, pageSize, orderBy); //分页
            mongoQuery.getConditionList().forEach(condition -> query.addCriteria(getCriteriaByOptionType(condition.getKey(), condition.getValue(), condition.getType())));
            List<T> list = mongoTemplate.find(query, clazz);
            //setTotal一定要放到最后面
            PageResult<T> pageResult = new PageResult<T>().setCurrentPage(currentPage).setPageSize(query.getLimit())
                                                        .setSkip(query.getSkip()).setTotal(findCount(query, clazz)).setData(list);
            mongoQuery.clear();
            return pageResult;
        }
    }

    /**
     * 功能描述: JSON查询
     * @param jsonQuery     json条件
     * @param clazz         对象类型
     */
    public <T> List<T> findByJson(String jsonQuery, Class<T> clazz) {
        //jsonQuery = "{$or:[{age:{$gt:25}},{name:'张宇'}]}";
        Query query = new BasicQuery(jsonQuery);
        return mongoTemplate.find(query, clazz);
    }

    /**
     * 功能描述: 原生查询(关系运算符、逻辑运算符、分页、排序等全部自定义后传入)
     * @param query         原生条件
     * @param clazz         对象类型
     */
    public <T> List<T> findByNative(Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    /**
     * 功能描述: 原生分页：组合条件+分页查询+排序
     * @param query           查询条件
     * @param clazz           对象类型
     * @param orderBy         排序枚举
     */
    public <T> PageResult<T> findByNative(Query query,
                                         Class<T> clazz,
                                         Integer currentPage,
                                         Integer pageSize,
                                         OrderBy orderBy) {
        if (ObjectUtils.isEmpty(query)) {
            return findAll(clazz, currentPage, pageSize);
        }else {
            if (!ObjectUtils.isEmpty(currentPage) && !ObjectUtils.isEmpty(pageSize)){
                query.limit(pageSize);
                query.skip(Integer.toUnsignedLong(pageSize * (currentPage - 1)));
            }
            if (orderBy != null && ArrayUtils.isNotEmpty(orderBy.getField())){
                query.with(Sort.by(orderBy.getOrderType(), orderBy.getField()));
            }
            List<T> list = mongoTemplate.find(query, clazz);
            //setTotal一定要放到最后面
            return new PageResult<T>().setCurrentPage(currentPage).setPageSize(query.getLimit())
                    .setSkip(query.getSkip()).setTotal(findCount(query, clazz)).setData(list);
        }
    }

    /**
     * 获取真实的实体id
     * @param clazz
     * @return
     */
    private static <T> String getRealIdName(Class<T> clazz){
        return getRealIdName(clazz, true);
    }
    private static <T> String getRealIdName(@NonNull Class<T> clazz, boolean isFind){
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(m -> m.setAccessible(true));
        Field field = Arrays.stream(fields).filter(f -> f.isAnnotationPresent(Id.class)).findAny().orElse(null);
        if (Objects.isNull(field) && fields.length > 0){
            //无注解用原主键字段
            return "serialVersionUID".equals(fields[0].getName()) ? fields[1].getName() : fields[0].getName();
        }
        return isFind ? "_id" : field.getName();
    }
}
