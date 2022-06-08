#####ES版本选择：
![ES对应Spring版本.png](https://upload-images.jianshu.io/upload_images/28076536-26b06996845556a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
先检查自己SpringBoot版本，我是用的SpringBoot版本是2.3.12.RELEASE，所以ES安装版本用的elasticsearch-7.6.2/kibana-7.6.2-windows-x86_64**(ES和kibana版本必须一致)**
ES对JDK的支持可参考：[https://www.elastic.co/cn/support/matrix#matrix_jvm](https://www.elastic.co/cn/support/matrix#matrix_jvm)
#####windows环境的搭建：
参考：[https://blog.csdn.net/pan_junbiao/article/details/114309373](https://blog.csdn.net/pan_junbiao/article/details/114309373)
#####ES组件基本介绍：
![ES对比数据库类型.png](https://upload-images.jianshu.io/upload_images/28076536-04d057c28e51edc4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#####集成到SpringBoot：
- 引入依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
```
- 配置文件
```
spring:
  data:
    elasticsearch: #ElasticsearchProperties
      cluster-name: elasticsearch #默认即为elasticsearch
      cluster-nodes: 127.0.0.1:9300 #配置es节点信息，逗号分隔，如果没有指定，则启动ClientNode
```
- 定义实体类
```


/**
 * indexName 索引库名，个人建议以项目名称命名
 * type 类型，个人建议以实体类名称命名 注意：ES7.0之后将放弃type
 * shards 默认分区数
 * replicas 每个分区默认的备份数
 * indexStoreType 索引文件存储类型
 * refreshInterval 刷新间隔
 */
@Document(indexName="oms",shards=5,replicas=1,indexStoreType="fs",refreshInterval="-1")
@Data
public class ElasticsearchOrderVo implements Serializable {

    private static final long serialVersionUID = 551589397625941750L;

    @Id
    private String id;

    /**
     * orderId
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 收货人
     */
    private String receiverName;

    /**
     * 省市区
     */
    private String receiverAreaName;

    /**
     * 详细地址
     */
    private String receiverAddress;

    /**
     * 手机
     */
    private String receiverPhone;

    /**
     * 总金额
     */
    private Long totalAmount;

    /**
     * 实际支付金额
     */
    private Long payAmount;

    /**
     * 运费金额
     */
    private Long freightAmount;

    /**
     * 积分抵扣金额
     */
    private Long integrationAmount;

    /**
     * 优惠券抵扣金额
     */
    private Long couponAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 可获取的积分
     */
    private Long integration;

    /**
     * 备注
     */
    private String memo;

    /**
     * 订单状态，1：待付款，2：待发货，3：待收货，4：已完成
     */
    private Integer status;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 发票类型：0->不开发票；1->电子发票；2->纸质发票
     */
    private Integer billType;

    /**
     * 发票抬头
     */
    private String billHeader;

    /**
     * 发票内容
     */
    private String billContent;

    /**
     * 收票人电话
     */
    private String billReceiverPhone;

    /**
     * 收票人邮箱
     */
    private String billReceiverEmail;

    /**
     * 订单类型：0->正常订单；1->秒杀订单
     */
    private Integer orderType;
}

```
- 定义Repository接口
```
@Component
public interface ElasticsearchOrderRepository extends ElasticsearchRepository<ElasticsearchOrderVo, String> {
    //泛型<T,ID> T是文档内容，ID是文档ID类型
}
```
- 定义Service
```
public interface ElasticsearchOrderService {

    /**
     * 保存
     * @param elasticsearchOrderVoList
     * @return
     */
    Iterable<ElasticsearchOrderVo> saveAll(List<ElasticsearchOrderVo> elasticsearchOrderVoList);
}


@Slf4j
@Service
public class ElasticsearchOrderServiceImpl implements ElasticsearchOrderService {

    @Autowired
    private ElasticsearchOrderRepository elasticsearchOrderRepository;

    @Override
    public Iterable<ElasticsearchOrderVo>  saveAll(List<ElasticsearchOrderVo> elasticsearchOrderVoList) {
        return elasticsearchOrderRepository.saveAll(elasticsearchOrderVoList);
    }
}
```
- 定义Controller
```
    public CommonResp es() {
        List<ElasticsearchOrderVo> elasticsearchOrderVoList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            ElasticsearchOrderVo elasticsearchOrderVo = new ElasticsearchOrderVo();
            elasticsearchOrderVo.setId(UUID.randomUUID().toString());
            elasticsearchOrderVo.setOrderId(Long.valueOf(i));
            elasticsearchOrderVo.setOrderSn("Order_"+i);
            elasticsearchOrderVo.setReceiverName("小明"+i);
            elasticsearchOrderVo.setReceiverPhone("1201212122"+i);
            elasticsearchOrderVo.setReceiverAreaName("广东省深圳市南山区");
            elasticsearchOrderVo.setReceiverAddress("四方精创资讯大厦"+i+"楼");
            elasticsearchOrderVo.setBillReceiverEmail("12456789@qq.com");
            elasticsearchOrderVo.setOrderType(1);
            elasticsearchOrderVo.setCouponAmount(100L);
            elasticsearchOrderVo.setFreightAmount(8L);
            elasticsearchOrderVo.setIntegrationAmount(1L);
            elasticsearchOrderVo.setPayAmount(200L);
            elasticsearchOrderVo.setStatus(4);
            elasticsearchOrderVo.setBillHeader("深圳市 南山区 软基"+i + "层");
            elasticsearchOrderVoList.add(elasticsearchOrderVo);
        }
        return CommonResp.ok(elasticsearchOrderService.saveAll(elasticsearchOrderVoList));
    }
```
- 浏览器请求：
![ES文档插入响应结果.png](https://upload-images.jianshu.io/upload_images/28076536-8d9bb3c72b218e39.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 通过Kibana进去查询下数据，看下数据是否有被插入进去
![Kibana查询结果.png](https://upload-images.jianshu.io/upload_images/28076536-21cb039c318cb069.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这里可以看到数据是有写入ES，但好像不是按顺序写进去的；
took:执行搜索耗时，单位毫秒
time_out:搜索是否超时
_shards:多少分片被搜索，成功多少，失败多少
hits：搜索结果展示
hits.total:匹配条件的总当总数
hits.max_score：最大匹配得分
hits._score：返回文档的匹配得分（得分越高，匹配程度越高，越靠前）
_index:索引名称
_type:这里我默认没有指定type，因为ES7之后就放弃type了，在没有指定type的情况系统默认设置type=_doc
_id:这个ID代码用UID生成的，也可以不指定，ES会默认生成一个ID
_source:这个就是我们用到的字段了
- 这里说明下：_idex_type_id可以直接定位到指定文档，比如：
![指定ID查询文章.png](https://upload-images.jianshu.io/upload_images/28076536-3d03c2cb6f969e5b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 返回指定字段：
![返回指定字段.png](https://upload-images.jianshu.io/upload_images/28076536-0af55af327ea2808.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 按照条件模糊匹配：名字等于小明5
![名字等于小明5.png](https://upload-images.jianshu.io/upload_images/28076536-b46f33f0f62ab635.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 按照条件精准匹配：名字等于小明5
![精准匹配.png](https://upload-images.jianshu.io/upload_images/28076536-7ed6c78790e70aff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
######官方文档参考：[https://www.elastic.co/guide/en/elasticsearch/reference/current/paginate-search-results.html](https://www.elastic.co/guide/en/elasticsearch/reference/current/paginate-search-results.html)
####SpringBoot实现ES分页
#####ES分页介绍：
- from+size 
优点：支持随机翻页
缺点：受制于 max_result_window 设置，不能无限制翻页。存在深度翻页问题，越往后翻页越慢。
适用场景：小型数据集或者大数据集返回 Top N（N <= 10000）结果集的业务场景，搜索引擎（谷歌、bing、百度、360、sogou等）支持随机跳转分页的业务场景
- Scroll 遍历查询
优点：支持全量遍历。ps：单次遍历的 size 值也不能超过 max_result_window 大小。
缺点：响应时间非实时。保留上下文需要足够的堆内存空间。
适用场景：全量或数据量很大时遍历结果数据，而非分页查询。
官方文档强调：不再建议使用scroll API进行深度分页。如果要分页检索超过 Top 10,000+ 结果时，推荐使用：PIT+search_after
- search_after 
优点：不严格受制于 max_result_window，可以无限制往后翻页。ps：不严格含义：单次请求值不能超过 max_result_window；但总翻页结果集可以超过。
缺点：只支持向后翻页，不支持随机翻页。
适用场景：不支持随机翻页，更适合手机端应用的场景。
#####from+size+自定义注解代码实现：
- 自定义注解
```
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsEquals {

    /**
     * filed name
     */
    String name() default "";
}

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsIn {
    /**
     * filed name
     */
    String name() default "";
}

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsLike {
    /**
     * filed name
     */
    String name() default "";

    boolean leftLike() default false;

    boolean rightLike() default false;
}

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsRange {

    /**
     * filed name
     */
    String name() default "";

    /**
     * <
     */
    boolean lt() default false;

    /**
     * >
     */
    boolean gt() default false;

    /**
     * 包含上界
     */
    boolean includeUpper() default false;

    /**
     * 包含下界
     */
    boolean includeLower() default false;
}

```
- 定义抽象类：AbstractSearchQueryEngine
```
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


    //todo search_after 深度分页

    //todo Scroll分页...

}
```
- 定义实现类
```
@Component
public class SimpleSearchQueryEngine extends AbstractSearchQueryEngine{

    @Override
    public SearchHits findPage(Object requestPara, Class clazz, Pageable pageable) {
        Query query = EsQueryParse.convertQuery(requestPara);
        //组装分页
        query.setPageable(pageable);
        return elasticsearchRestTemplate.search(query, clazz);
    }
}
```
- 自定义注解转换工具类
```

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
    public static <T> Query convertQuery(T t) {
        try {
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Object value = ClassUtils.getPublicMethod(clazz, "get" + captureName(field.getName())).invoke(t);
                if (value == null) {
                    continue;
                }
                if (field.isAnnotationPresent(EsLike.class)) {
                    WildcardQueryBuilder query = getLikeQuery(field, (String) value);
                    boolQueryBuilder.must(query);
                }
                if (field.isAnnotationPresent(EsEquals.class)) {
                    MatchQueryBuilder query = getEqualsQuery(field, value);
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
            return queryBuilder.withQuery(boolQueryBuilder).build();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Query.findAll();
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
    private static MatchQueryBuilder getEqualsQuery(Field field, Object value) {
        EsEquals esEquals = field.getAnnotation(EsEquals.class);
        String filedName = StringUtils.isBlank(esEquals.name()) ? field.getName() : esEquals.name();
        return QueryBuilders.matchQuery(filedName, value);
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
```
- Service方法
```
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
```
- Service实现
```

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
}

```
- Controller
```
    @PostMapping("/page")
    public CommonResp findElasticsearchOrderVoList(@RequestBody ElasticsearchOrderQueryVo elasticsearchOrderQueryVo){
        return iomsOrderItemService.findElasticsearchOrderVoList(elasticsearchOrderQueryVo);
    }
```
- RequestVO（ElasticsearchOrderQueryVo）
```
 /**
     * 分页
     */
    private Integer page;

    /**
     * 数量
     */
    private Integer size;

    /**
     * orderId
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 收货人
     */
    @EsEquals
    private String receiverName;

    /**
     * 省市区
     */
    private String receiverAreaName;

    /**
     * 详细地址
     */
    private String receiverAddress;

    /**
     * 手机
     */
    private String receiverPhone;


    /**
     * 实际支付金额
     */
    @EsRange(lt = true)
    private Long payAmount;


    /**
     * 创建时间
     */
    private Date createTime;

```
- POSTMAN相应结果：
![form+size响应结果.png](https://upload-images.jianshu.io/upload_images/28076536-6fbb0be98cda3a9b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)