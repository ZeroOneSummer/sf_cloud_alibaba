package com.formssi.mall.mongodb;

import com.alibaba.fastjson.JSON;
import com.formssi.mall.mongodb.base.BaseTest;
import com.formssi.mall.mongodb.bean.Book;
import com.formssi.mall.mongodb.bean.MongoQuery;
import com.formssi.mall.mongodb.bean.MongoQuery.OptionType;
import com.formssi.mall.mongodb.bean.MongoQuery.OrderBy;
import com.formssi.mall.mongodb.bean.PageResult;
import com.formssi.mall.mongodb.service.MongoService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.formssi.mall.mongodb.bean.MongoQuery.MongoCondition;
import static com.formssi.mall.mongodb.bean.MongoQuery.add;

/**
 * @author jp
 * @version 1.0
 * @Description: MongoDB测试类
 * @date 2022/5/16 10:20
 */
@Slf4j
public class MongoDBTests extends BaseTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoService mongoService;

    @Test
    public void testCollection() throws Exception{
        //增加集合
        mongoService.createCollection("t_book");

        //删除集合
        mongoService.dropCollection("book");

        //增加唯一索引
        mongoService.createUniqueIndex("t_book", "name");
    }

    @Test
    public void testInsert() throws Exception{
        //单条插入
        Book book = mongoService.insert(new Book("202205170002", "西游记", 65.0d, new Date()));

        //批量插入
        mongoService.insertMulti(Arrays.asList(
                new Book("202205170003", "三国演义", 77.0d, new Date()),
                new Book("202205170004", "红楼梦", 102.0d, new Date())
        ), Book.class);

        //存在则更新
        mongoService.saveOrUpdate(new Book("202205170004", "红楼梦", 150.0d, new Date()));
    }

    @Test
    public void testUpdate() throws Exception{
        //根据ID更新(排除主键)
        mongoService.updateById("202205170004", new Book("202205170005", "红楼梦", 100.1d, new Date()), Book.class);

        //存在更新或不存在插入
        MongoQuery mongoQuery = MongoQuery.create().append("id", "202205170005");
        mongoService.updateOrInsert(mongoQuery, new Book("202205170006", "红楼梦2", 100.0d, new Date()), Book.class);
    }

    @Test
    public void testQuery() throws Exception{
        //查询所有
        List<Book> books = mongoService.findAll(Book.class);
        log.info(JSON.toJSONString(books.size()));

        //总记录数
        Long num = mongoService.findCount(new Query(), Book.class);
        log.info(JSON.toJSONString(num));

        //查询By id
        Book book = mongoService.findById("202205170005", Book.class);
        log.info(JSON.toJSONString(book));

        //分页查询
        PageResult<Book> pageResult = mongoService.findAll(Book.class, 1, 3);
        log.info(JSON.toJSONString(pageResult));

        //组合+分页+排序查询(由于语法问题，addGroup目前只支持添加一个)
//        MongoQuery mongoQuery = new MongoQuery().addGroup(
//                                        RelationType.AND,
//                                        new MongoQuery().add("price", 60, MongoQuery.OptionType.GTE)
//                                                .add("price", 100, MongoQuery.OptionType.LTE)   // 6条符合
//                                                .add("pkId", Arrays.asList("202205170001", "202205170004", "202205170006"), OptionType.IN) // 2条符合
//                                                .getConditionList()
//                                );
//        PageResult<Book> pageResult2 = mongoService.findByCondition(mongoQuery, Book.class, 1, 10, OrderBy.DESC("price"));
//        log.info(JSON.toJSONString(pageResult2));

        //todo 改造后：简单条件追加，默认（AND + IS）
        MongoQuery mongoQuery = MongoQuery.create().append("name", "西游记").append("price", 30d, OptionType.GT);
        PageResult<Book> pageResult22 = mongoService.findByCondition(mongoQuery, Book.class, 1, 10, OrderBy.DESC("price"));
        log.info(JSON.toJSONString(pageResult22));

        //todo 改造后：复杂查询
        Query query22 = MongoQuery.createQuery().addCriteria(
                MongoCondition.create().orOperator(
                        add("name", "杀死一只知更鸟"), // 1条符合
                        MongoCondition.create().andOperator(
                            add("price", "60", OptionType.GTE),
                            add("price", "60", OptionType.LTE),
                            add("price", Arrays.asList("202205170001", "202205170004", "202205170006"), OptionType.IN)  // 2条符合
                        )
                )
        );
        PageResult<Book> pageResult2 = mongoService.findByNative(query22, Book.class, 1, 10, OrderBy.DESC("price"));
        log.info(JSON.toJSONString(pageResult2));

        //原生查询
        Query query = new Query();
        query.addCriteria(
            new Criteria().orOperator(
                Criteria.where("name").is("杀死一只知更鸟"), // 1条符合
                new Criteria().andOperator(
                    Criteria.where("price").gte(60),
                    Criteria.where("price").lte(100),
                    Criteria.where("pkId").in("202205170001", "202205170004", "202205170006") // 2条符合
                )
            )
        );
        List<Book> books2 = mongoService.findByNative(query, Book.class);
        log.info(JSON.toJSONString(books2));
        //原生+分页
        PageResult<Book> pageResult3 = mongoService.findByNative(query, Book.class, 1, 2, OrderBy.DESC("price"));
        log.info(JSON.toJSONString(pageResult3));

        //json查询
        String json = "{ \"$or\" : [{ \"name\" : \"杀死一只知更鸟\"}, { \"$and\" : [{ \"price\" : { \"$gte\" : 60}}, " +
                "{ \"price\" : { \"$lte\" : 100}}, { \"_id\" : { \"$in\" : [\"202205170001\", \"202205170004\", \"202205170006\"]}}]}]} ";
        List<Book> books3 = mongoService.findByJson(json, Book.class);
        log.info(JSON.toJSONString(books3));
    }

    @Test
    public void testLikeQuery(){
        //原生
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("传"));
        List<Book> books = mongoTemplate.find(query, Book.class);
        log.info(JSON.toJSONString(books));

        //工具类
        MongoQuery mongoQuery = MongoQuery.create().append("name", "传", OptionType.LIKE);
        List<Book> books2 = mongoService.findByCondition(mongoQuery, Book.class);
        log.info(JSON.toJSONString(books2));
    }

    @Test
    public void testDelete() throws Exception{
        //by id删除
        mongoService.deleteById("202205170002", Book.class);
        //by Object删除
        mongoService.deleteObject(new Book("202205170002", "西游记2", 65.0d, new Date()));
        //条件删除
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                    Criteria.where("price").gte(100),
                    Criteria.where("price").lte(120))
                );
        mongoService.deleteByNative(query, Book.class);
    }

    @Test
    public void testAggregation(){
        Aggregation aggregation = Aggregation.newAggregation(
                //规定可用的字段
                Aggregation.project("_id", "name", "price", "_class"),
                //分组字段、统计字段及别名
                Aggregation.group("_id", "_class").sum("price").as("totalCount"),
                //排序字段
                Aggregation.sort(Sort.Direction.DESC, "totalCount"),
                //分页
                Aggregation.skip(0L),
                Aggregation.limit(10)
        )
                //.withOptions(new AggregationOptions(true, true, 10))  //添加额外操作：如磁盘、执行计划、游标
                ;
        AggregationResults<Book> aggregateResult = mongoTemplate.aggregate(aggregation, "t_book", Book.class);
        //返回分组情况
        log.info(JSON.toJSONString(aggregateResult.getMappedResults()));
        //返回统计结果
        Document doc = aggregateResult.getRawResults();
        log.info(doc.toJson());
    }

    @Transactional
    @Test
    public void testTransaction() throws Exception{
        //单机版不支持事务
        Book book = mongoService.insert(new Book("202205170002", "西游记", 65.0d, new Date()));
        int num = 1/0;
    }
}
