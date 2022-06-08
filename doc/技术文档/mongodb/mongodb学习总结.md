### MongoDB 学习总结


### MongoDB介绍
   - 什么是MongoDB
    1.MongoDB是一个文档数据库（以JSON为数据库模型），由C++语言编写，旨在为WEB应用提供可扩展的高性能数据存储解决方案。
    2.MongoDB是一个介于关系数据库和非关系数据之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的。它支持的数据结构非常松散，数据格式是BSON,一种类型JSON的二进制形式
    存储格式，简称BinaryJSON，和JSON一样支持内嵌的稳定对象和数据对象，因此可以存储比较 复杂的数据类型，而且支持建立索引和事务，（mysql能做的事情，MongoDB都能做）

    
### MongoDB概念：
    SQL概念                           MongoDB概念
    数据库（database）                 数据库（database）:最外层的概念，可以理解逻辑上的名称空间，一个数据库包含多个不同名称的集合
    表（table）                       集合（collection）:相当于SQL中的表，一个集合可以存放多个不同的文档。
    行（row）                         文档（document）  :一个文档相当于数据表中的一行，由多个不同的字段组成
    列（column）                      字段（field）     :文档中的一个属性，等同于列（column）
    索引（index）                      索引（index）     :独立的检索式数据结构，与SQL概念一致
    主键（primary key）                _id(字段)        :每个文档中都拥有一个唯一的id字段，相当于SQL中的主键（primary key）
    视图（view）                      视图（view）       :可以看作一种虚拟的（非真实存在的）集合，与SQL中的视图类似。从MongoDB 3.4版本开始提供了视图功能，其通过聚合管道技术实现
    表连接（table joins）              聚合操作（$lookup）:MongoDB用于实现“类似”表的连接（tablejoin）的聚合操作符

 - 尽管这些概念大多与SQL标准定义类似，但MongoDB与传统RDBMS仍然存在不少差异，包括:
    ·半结构化，在一个集合中，文档所拥有的字段并不需要是相同的，而且也不需要对所用的字段进行声明。因此，MongoDB具有很明显的半结构化特点。除了松散的表结构，文档还可以支持多级的嵌套、数组等灵活的数据类型，非常契合面向对象的编程模型。
    ·弱关系，MongoDB没有外键的约束，也没有非常强大的表连接能力。类似的功能需要使用聚合管道技术来弥补。


### MongoDB技术优势
    MongoDB基于灵活的JSON文档模型，非常适合敏捷式的快速开发。与此同时，其与生俱来的高可用、高水平扩展能力使得它在处理海量、高并发的数据应用颇具优势。
    ·JSON结构和对象模型接近，开发代码量低
    ·JSON的动态模型意味着更容易响应新的业务需求
    ·复制提供99.99%高可用
    ·分片结构支持海量数据和无缝扩容


### MongoDB应用场景
    ·游戏场景，使用MongoDB存储游戏用户信息，用户的装备、积分等直接以内嵌文档的形式存储，方便查询、更新；
    ·物流场景，使用MongoDB存储订单信息，订单状态在运送过程中会不断更新，以MongoDB存储内嵌数组的形式来存储，一次查询就能将订单所有的变更读取出来；
    ·社交场景，使用MongoDB存储用户信息，以及用户发表的朋友圈信息，通过地理位置索引实现附近的人、地点等功能；
    ·物联网场景，使用MongoDB存储所有介入的智能设备信息，以及设备汇报的日志信息，并对这些信息进行多维度的分析；
    ·大数据应用，使用云数据库MongoDB作为大数据的云存储系统，随时进行数据提取分析，掌握行业动态。
   

### Docker快速安装MongoDB
    1·拉取最新镜像，mongo带有版本号，就是下载对应版本
    docker pull mongo
    2·查看镜像是非下载成功
    docker images | grep mongo 
    3·mongo镜像的容器开始运行
    docker run --name mongodb -v ~/docker/mongo:/data/db -p 27017:27017 -d mongo
    执行上述命令之后, 一个挂载了 mongo镜像的容器就开始运行了
    --name 设置了容器的名字
    -v 设置了路径的映射, 将本地路径映射到容器中. 此处, 路径可以自定义
    -p 设置了端口的映射, 将容器的27017(右侧) 映射到了本地的27017(右侧)
    4·进入容器
    docker exec -it mongodb bash
    使用交互的形式, 在 名字为 mongodb 的容器中实行 bash这个命令
    5·用户的创建和数据库的建立
    用户的创建
    ·输入以下命令进入 mongo

    ·创建用户
    # 进入 admin 的数据库
    use admin
    # 创建管理员用户
    db.createUser(
    {
    user: "admin",
    pwd: "123456",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
    }
    )
    # 创建有可读写权限的用户. 对于一个特定的数据库, 比如'demo'
    db.createUser({
    user: 'test',
    pwd: '123456',
    roles: [{role: "read", db: "demo"}]
    })

    ·数据库的建立
    use demo;

    6·mongo 是否正常启动的校验
    db.info.save({name: 'test', age: '22'})

    ·查看写入的数据
    db.info.find();
    
    7·远程连接的开启，在 mongodb 的容器当中。
    #更新源
    apt-get update
    # 安装 vim
    apt-get install vim
    # 修改 mongo 配置文件
    vim /etc/mongod.conf.orig
    ·将其中的
    bindIp: 127.0.0.1
    注释掉# bindIp: 127.0.0.1
    或者改成bindIp: 0.0.0.0
    即可开启远程连接

### 什么是mongo shell
    mongo shell是基于JavaScript语法的，MongoDB使用了SpiderMonkey作为其内部的JavaScript解释器
    引擎，这是由Mozilla官方提供的JavaScript内核解释器，该解释器也被同样用于大名鼎鼎的Firefox浏览
    器产品之中。SpiderMonkey对ECMA Script标准兼容性非常好，可以支持ECMA Script 6。可以通过下
    面的命令检查JavaScript解释器的版本：

### Mongo Shell常用命令
    命令                                 说明
    show dbs | show databases           显示数据库列表
    use                                 数据库名 切换数据库，如果不存在创建数据库
    db.dropDatabase()                   删除数据库
    show collections | show tables      显示当前数据库的集合列表
    db.集合名.stats()                    查看集合详情
    db.集合名.drop()                     删除集合
    show users                          显示当前数据库的用户列表
    show roles                          显示当前数据库的角色列表
    show profile                        显示最近发生的操作
    load("xxx.js")                      执行一个JavaScript脚本文件
    exit | quit()                       退出当前shell
    help                                查看mongodb支持哪些命令
    db.help()                           查询当前数据库支持的方法
    db.集合名.help()                     显示集合的帮助信息
    db.version()                        查看数据库版本
    
### 建议可以直接使用idea里面的数据连接，或者使用Studio 3T软件来连接MongoDB

 ·数据库操作

    #查看所有库
    show dbs
    # 切换到指定数据库，不存在则创建
    use test
    # 删除当前数据库
    db.dropDatabase()

 ·集合操作

    #查看集合
    show collections
    #创建集合
    db.createCollection("emp")
    #删除集合
    db.emp.drop()
    
 ·创建集合语法

    db.createCollection(name, options)
      ·options参数
        字段      类型       描述
        capped   布尔      （可选）如果为true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。
        size     数值      （可选）为固定集合指定一个最大值（以字节计）。如果 capped 为 true，也需要指定该字段。
        max      数值      （可选）指定固定集合中包含文档的最大数量。
        注意： 当集合不存在时，向集合中插入文档也会创建集合

### 安全认证
    # 设置管理员用户名密码需要切换到admin库
    use admin
    #创建管理员
    db.createUser({user:"admin",pwd:"admin",roles:["root"]})
    # 查看所有用户信息
    show users
    #删除用户
    db.dropUser("admin")

 ·常用权限

    权限名                     描述
    read                      允许用户读取指定数据库
    readWrite                 允许用户读写指定数据库
    dbAdmin                   允许用户在指定数据库中执行管理函数，如索引创建、删除，查看统计或访问system.profile
    dbOwner                   允许用户在指定数据库中执行任意操作，增、删、改、查等
    userAdmin                 允许用户向system.users集合写入，可以在指定数据库里创建、删除和管理用户
    clusterAdmin              只在admin数据库中可用，赋予用户所有分片和复制集相关函数的管理权限
    readAnyDatabase           只在admin数据库中可用，赋予用户所有数据库的读权限
    readWriteAnyDatabase      只在admin数据库中可用，赋予用户所有数据库的读写权限
    userAdminAnyDatabase      只在admin数据库中可用，赋予用户所有数据库的userAdmin权限
    dbAdminAnyDatabase        只在admin数据库中可用，赋予用户所有数据库的dbAdmin权限
    root                      只在admin数据库中可用。超级账号，超级权限

·用户认证，返回1表示认证成功

    创建应用数据库用户
    默认情况下，MongoDB不会启用鉴权，以鉴权模式启动MongoDB
    启用鉴权之后，连接MongoDB的相关操作都需要提供身份认证。
    use appdb
    db.createUser({user:"appdb",pwd:"123456",roles:["dbOwner"]})
    mongod -f /mongodb/conf/mongo.conf --auth
    mongo 192.168.65.174:27017 -u fox -p fox --authenticationDatabase=admin

### MongoDB整合SpringBoot
    ·环境准备
        1.引入依赖
        <!--spring data mongodb-->
        <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>

        2.配置yml
        spring:
             data:
                mongodb:
                uri: mongodb://admin:admin@192.168.0.5:27017/test?authSource=admin
                #uri等同于下面的配置
                #database: test
                #host: 192.168.0.5
                #port: 27017
                #username: admin
                #password: admin
                #authentication-database: admin 
        3.使用时注入mongoTemplate
        @Autowired
        MongoTemplate mongoTemplate;

### 集合操作
    @Test
    public void testCollection(){
    boolean exists = mongoTemplate.collectionExists("emp");
    if (exists) {
    //删除集合
    mongoTemplate.dropCollection("emp");
    }
    //创建集合
    mongoTemplate.createCollection("emp");
    }

### 文档操作
  ·相关注解

    @Document
    修饰范围: 用在类上
    作用: 用来映射这个类的一个对象为mongo中一条文档数据。
    属性:( value 、collection )用来指定操作的集合名称
    @Id
    修饰范围: 用在成员变量、方法上
    作用: 用来将成员变量的值映射为文档的_id的值
    @Field
    修饰范围: 用在成员变量、方法上
    作用: 用来将成员变量及其值映射为文档中一个key:value对。
    属性:( name , value )用来指定在文档中 key的名称,默认为成员变量名
    @Transient
    修饰范围:用在成员变量、方法上
    作用:用来指定此成员变量不参与文档的序列化

  ·创建实体

    @Document("emp") //对应emp集合中的一个文档
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Employee {
    @Id //映射文档中的_id
    private Integer id;
    @Field("username")
    private String name;
    @Field
    private int age;
    @Field
    private Double salary;
    @Field
    private Date birthday;
    }
    
  ·添加文档
   
    insert方法返回值是新增的Document对象，里面包含了新增后id的值。如果集合不存在会自动创建集通过Spring Data MongoDB还会给集合中多加一个class的属性，
    存储新增时Document对应Java中类的全限定路径。这么做为了查询时能把Document转换为Java类型。
    @Test
    public void testInsert(){
    Employee employee = new Employee(1, "小明", 30,10000.00, new Date());
    //添加文档
    // sava: _id存在时更新数据
    //mongoTemplate.save(employee);
    // insert： _id存在抛出异常 支持批量操作
    mongoTemplate.insert(employee);
    List<Employee> list = Arrays.asList(
    new Employee(2, "张三", 21,5000.00, new Date()),
    new Employee(3, "李四", 26,8000.00, new Date()),
    new Employee(4, "王五",22, 8000.00, new Date()),
    new Employee(5, "张龙",28, 6000.00, new Date()),
    new Employee(6, "赵虎",24, 7000.00, new Date()),
    new Employee(7, "赵六",28, 12000.00, new Date()));
    //插入多条数据
    mongoTemplate.insert(list,Employee.class);
    }

  ·查询文档

    Criteria是标准查询的接口，可以引用静态的Criteria.where的把多个条件组合在一起，就可以轻松地将多个方法标准和查询连接起来，方便我们操作查询语句。

 ![img_1.png](img_1.png)
 ![img_2.png](img_2.png)

    @Test
    public void testFind(){
    System.out.println("==========查询所有文档===========");
    //查询所有文档
    List<Employee> list = mongoTemplate.findAll(Employee.class);
    list.forEach(System.out::println);
    System.out.println("==========根据_id查询===========");
    //根据_id查询
    Employee e = mongoTemplate.findById(1, Employee.class);
    System.out.println(e);
    System.out.println("==========findOne返回第一个文档===========");
    //如果查询结果是多个，返回其中第一个文档对象
    Employee one = mongoTemplate.findOne(new Query(), Employee.class);
    System.out.println(one);
    System.out.println("==========条件查询===========");
    //new Query() 表示没有条件
    //查询薪资大于等于8000的员工
    //Query query = new Query(Criteria.where("salary").gte(8000));
    //查询薪资大于4000小于10000的员工
    //Query query = new Query(Criteria.where("salary").gt(4000).lt(10000));
    //正则查询（模糊查询） java中正则不需要有//
    //Query query = new Query(Criteria.where("name").regex("张"));
    //and or 多条件查询
    Criteria criteria = new Criteria();
    //and 查询年龄大于25&薪资大于8000的员工
    //criteria.andOperator(Criteria.where("age").gt(25),Criteria.where("salary").gt(8000));
    //or 查询姓名是张三或者薪资大于8000的员工
    criteria.orOperator(Criteria.where("name").is("张三"),Criteria.where("salary").gt(5000));
    Query query = new Query(criteria);
    //sort排序
    //query.with(Sort.by(Sort.Order.desc("salary")));
    //skip limit 分页 skip用于指定跳过记录数，limit则用于限定返回结果数量。
    query.with(Sort.by(Sort.Order.desc("salary")))
    .skip(0) //指定跳过记录数
    .limit(4); //每页显示记录数
    //查询结果
    List<Employee> employees = mongoTemplate.find(
    query, Employee.class);
    employees.forEach(System.out::println);
    }

    @Test
    public void testFindByJson() {
    //使用json字符串方式查询
    //等值查询
    //String json = "{name:'张三'}";
    //多条件查询
    String json = "{$or:[{age:{$gt:25}},{salary:{$gte:8000}}]}";
    Query query = new BasicQuery(json);
    //查询结果
    List<Employee> employees = mongoTemplate.find(
    query, Employee.class);
    employees.forEach(System.out::println);
    }

 ·更新文档

    在Mongodb中无论是使用客户端API还是使用Spring Data，更新返回结果一定是受行数影响。如果更新后的结果和更新前的结果是相同，返回0。
    updateFirst() 只更新满足条件的第一条记录
    updateMulti() 更新所有满足条件的记录
    upsert() 没有符合条件的记录则插入数据

    @Test
    public void testUpdate(){
    //query设置查询条件
    Query query = new Query(Criteria.where("salary").gte(15000));
    System.out.println("==========更新前===========");
    List<Employee> employees = mongoTemplate.find(query, Employee.class);
    employees.forEach(System.out::println);
    Update update = new Update();
    //设置更新属性
    update.set("salary",13000);
    //updateFirst() 只更新满足条件的第一条记录
    //UpdateResult updateResult = mongoTemplate.updateFirst(query, update,
    Employee.class);
    //updateMulti() 更新所有满足条件的记录
    //UpdateResult updateResult = mongoTemplate.updateMulti(query, update,
    Employee.class);
    //upsert() 没有符合条件的记录则插入数据
    //update.setOnInsert("id",11); //指定_id
    UpdateResult updateResult = mongoTemplate.upsert(query, update,
    Employee.class);
    //返回修改的记录数
    System.out.println(updateResult.getModifiedCount());
    System.out.println("==========更新后===========");
    employees = mongoTemplate.find(query, Employee.class);
    employees.forEach(System.out::println);
    }

 ·删除文档

     @Test
     public void testDelete(){
     //删除所有文档
     //mongoTemplate.remove(new Query(),Employee.class);
     //条件删除
     Query query = new Query(Criteria.where("salary").gte(10000));
     mongoTemplate.remove(query,Employee.class);
     }

### 聚合操作
    聚合操作处理数据记录并返回计算结果(诸如统计平均值，求和等)。聚合操作组值来自多个文档，可以对分组数据执行各种操作以返回单个结果。聚合操作包含三类：单一作用聚合、聚合管道、
    MapReduce。
    单一作用聚合：提供了对常见聚合过程的简单访问，操作都从单个集合聚合文档。
    聚合管道是一个数据聚合的框架，模型基于数据处理流水线的概念。文档进入多级管道，将文档转换为聚合结果。
    MapReduce操作具有两个阶段：处理每个文档并向每个输入文档发射一个或多个对象的map阶段，以及reduce组合map操作的输出阶段。

 ·单一作用聚合
    
    MongoDB提供 db.collection.estimatedDocumentCount(), db.collection.count(),db.collection.distinct() 这类单一作用的聚合函数。 所有这些操作都聚合来自单个集合的文档。虽然这
    些操作提供了对公共聚合过程的简单访问，但它们缺乏聚合管道和map-Reduce的灵活性和功能。

    ·函数                                            描述
    db.collection.estimatedDocumentCount()         忽略查询条件，返回集合或视图中所有文档的计数
    db.collection.count()                          返回与find()集合或视图的查询匹配的文档计数 。等同于 db.collection.find(query).count()构造
    db.collection.distinct()                       在单个集合或视图中查找指定字段的不同值，并在数组中返回结果。
    
    ·函数用法
    #检索books集合中所有文档的计数
    db.books.estimatedDocumentCount()
    #计算与查询匹配的所有文档
    db.books.count({favCount:{$gt:50}})
    #返回不同type的数组
    db.books.distinct("type")
    #返回收藏数大于90的文档不同type的数组
    db.books.distinct("type",{favCount:{$gt:90}})

    注意：在分片群集上，如果存在孤立文档或正在进行块迁移，则db.collection.count()没有查询谓词可能
    导致计数不准确。要避免这些情况，请在分片群集上使用 db.collection.aggregate()方法。

### 聚合管道
    聚合操作处理数据记录并返回计算结果(诸如统计平均值，求和等)。聚合操作组值来自多个文档，可以对
    分组数据执行各种操作以返回单个结果。聚合操作包含三类：单一作用聚合、聚合管道、MapReduce。
       ·单一作用聚合：提供了对常见聚合过程的简单访问，操作都从单个集合聚合文档。
       ·聚合管道是一个数据聚合的框架，模型基于数据处理流水线的概念。文档进入多级管道，将文档转
    换为聚合结果。
       ·MapReduce操作具有两个阶段：处理每个文档并向每个输入文档发射一个或多个对象的map阶
    段，以及reduce组合map操作的输出阶段。
   
·单一作用聚合

    MongoDB提供 db.collection.estimatedDocumentCount(), db.collection.count(),db.collection.distinct() 这类单一作用的聚合函数。所有这些操作都聚合来自单个集合的文档。
    虽然这些操作提供了对公共聚合过程的简单访问，但它们缺乏聚合管道和map-Reduce的灵活性和功能。
    
    函数                                           描述
    db.collection.estimatedDocumentCount()        忽略查询条件，返回集合或视图中所有文档的计数
    db.collection.count()                         返回与find()集合或视图的查询匹配的文档计数。等同于 db.collection.find(query).count()构造
    db.collection.distinct()                      在单个集合或视图中查找指定字段的不同值，并在数组中返回结果。

 ·检索books集合中所有文档的计数

    db.books.estimatedDocumentCount()
    #计算与查询匹配的所有文档
    db.books.count({favCount:{$gt:50}})
    #返回不同type的数组
    db.books.distinct("type")
    #返回收藏数大于90的文档不同type的数组
    db.books.distinct("type",{favCount:{$gt:90}})
    注意：在分片群集上，如果存在孤立文档或正在进行块迁移，则db.collection.count()没有查询谓词可能
    导致计数不准确。要避免这些情况，请在分片群集上使用 db.collection.aggregate()方法。

  ·什么是 MongoDB 聚合框架

    MongoDB 聚合框架（Aggregation Framework）是一个计算框架，它可以：
       ·作用在一个或几个集合上；
       ·对集合中的数据进行的一系列运算；
       ·将这些数据转化为期望的形式；
    从效果而言，聚合框架相当于 SQL 查询中的GROUP BY、 LEFT OUTER JOIN 、 AS等。

  ·管道（Pipeline）和阶段（Stage）
    
    整个聚合运算过程称为管道（Pipeline），它是由多个阶段（Stage）组成的， 每个管道：
        ·接受一系列文档（原始数据）；
        ·每个阶段对这些文档进行一系列运算；
        ·结果文档输出给下一个阶段；
 ·聚合管道操作语法

    pipeline = [$stage1, $stage2, ...$stageN];
    db.collection.aggregate(pipeline, {options})
    pipelines 一组数据聚合阶段。除$out、$Merge和$geonear阶段之外，每个阶段都可以在管道中出现多次。
    options 可选，聚合操作的其他参数。包含：查询计划、是否使用临时文件、 游标、最大操作时间、读写策略、强制索引等等
    
 ·常用的管道聚合阶段
 聚合管道包含非常丰富的聚合阶段，下面是最常用的聚合阶段

    阶段          描述          SQL等价运算符
    $match       筛选条件       WHERE
    $project     投影          AS
    $lookup      左外连接       LEFT OUTER JOIN
    $sort        排序          ORDER BY
    $group       分组          GROUP BY
    $skip/$limit 分页
    $unwind      展开数组
    $graphLookup 图搜索
    $facet/$bucket 分面搜索   

###数据准备
 准备数据集，执行脚本  

     var tags = ["nosql","mongodb","document","developer","popular"];
     var types = ["technology","sociality","travel","novel","literature"];
     var books=[];

    for(var i=0;i<50;i++){
    var typeIdx = Math.floor(Math.random()*types.length);
    var tagIdx = Math.floor(Math.random()*tags.length);
    var tagIdx2 = Math.floor(Math.random()*tags.length);
    var favCount = Math.floor(Math.random()*100);
    var username = "xx00"+Math.floor(Math.random()*10);
    var age = 20 + Math.floor(Math.random()*15);
    var book = {
        title: "book-"+i,
        type: types[typeIdx],
        tag: [tags[tagIdx],tags[tagIdx2]],
        favCount: favCount,
        author: {name:username,age:age}
    };
         books.push(book)
    }

    db.books.insertMany(books);

 ·$project 投影操作。将原始字段投影成指定名称，如将集合中的title投影成name
    
    db.books.aggregate([{$project:{name:"$title"}}])

 ·$project 可以灵活控制输出文档的格式，也可以剔除不需要的字段
    
    db.books.aggregate([{$project:{name:"$title",_id:0,type:1,author:1}}])
    //_id:0 ；0代表不显示（_id默认是显示），1代表是显示


 从嵌套文档中排除字段
     
    db.books.aggregate([
        {$project:{name:"$title",_id:0,type:1,"author.name":1}}
    ])
    或者
    db.books.aggregate([
        {$project:{name:"$title",_id:0,type:1,author:{name:1}}}
    ])

 ·$match 用于对文档进行筛选，之后可以在得到的文档子集上做聚合，$match可以使用除了地理空间之外的所有常规查询操作符，在实际应用中尽可能将$match放在管道的前面位置。这样有两个好处：一是
  可以快速将不需要的文档过滤掉，以减少管道的工作量；二是如果再投射和分组之前执行$match，查询可以使用索引。
    
    db.books.aggregate([{$match:{type:"technology"}}])
    
    筛选管道操作和其他管道操作配合时候时，尽量放到开始阶段，这样可以减少后续管道操作符要操作的文档数，提升效率
    
 ·$count 计数并返回与查询匹配的结果数
    
    db.books.aggregate([ {$match:{type:"technology"}}, {$count: "type_count"}])

    $match阶段筛选出type匹配technology的文档，并传到下一阶段；
    $count阶段返回聚合管道中剩余文档的计数，并将该值分配给type_count

·$group 按指定的表达式对文档进行分组，并将每个不同分组的文档输出到下一个阶段。输出文档包含一个_id字段，该字段按键包含不同的组。输出文档还可以包含计算字段，该字段保存由$group的_id字段分组的
 一些accumulator表达式的值。$group不会输出具体的文档而只是统计信息。

     $group: { _id: <expression>, <field1>: { <accumulator1> : <expression1> }, ...
     } }
    
    ·id字段是必填的;但是，可以指定id值为null来为整个输入文档计算累计值。
    ·剩余的计算字段是可选的，并使用运算符进行计算。
    ·_id和表达式可以接受任何有效的表达式。
    
     accumulator操作符

    名称                描述                                                                            类比sql
    $avg               计算均值                                                                         avg
    $first             返回每组第一个文档，如果有排序，按照排序，如果没有按照默认的存储的顺序的第一个文档。     limit  0,1
    $last              返回每组最后一个文档，如果有排序，按照排序，如果没有按照默认的存储的顺序的最后个文档。    -
    $max               根据分组，获取集合中所有文档对应值得最大值。                                         max
    $min               根据分组，获取集合中所有文档对应值得最小值。                                         min
    $push              将指定的表达式的值添加到一个数组中。                                                 -
    $addToSet          将表达式的值添加到一个集合中（无重复值，无序）。                                      -
    $sum               计算总和                                                                         sum
    $stdDevPop         返回输入值的总体标准偏差（population standard deviation）                           -
    $stdDevSamp        返回输入值的样本标准偏差（the sample standard deviation）                           -

    $group阶段的内存限制为100M。默认情况下，如果stage超过此限制，$group将产生错误。但是，要允许处理大型数据集，请将allowDiskUse选项设置为true以启用$group操作以写入临时文件。
    book的数量，收藏总数和平均值

    db.books.aggregate([
        {$group:{_id:null,count:{$sum:1},pop:{$sum:"$favCount"},avg:
        {$avg:"$favCount"}}}
    ])

    统计每个作者的book收藏总数
    db.books.aggregate([
        {$group:{_id:"$author.name",pop:{$sum:"$favCount"}}}
    ])
    
    统计每个作者的每本book的收藏数
    db.books.aggregate([
        {$group:{_id:{name:"$author.name",title:"$title"},pop:{$sum:"$favCount"}}}
    ])

    每个作者的book的type合集
    db.books.aggregate([
        {$group:{_id:"$author.name",types:{$addToSet:"$type"}}}
    ])

 ·$unwind 可以将数组拆分为单独的文档
    v3.2+支持如下语法：
     
    {
     $unwind:
        {
            #要指定字段路径，在字段名称前加上$符并用引号括起来。
            path: <field path>,
            #可选,一个新字段的名称用于存放元素的数组索引。该名称不能以$开头。
            includeArrayIndex: <string>,
            #可选，default :false，若为true,如果路径为空，缺少或为空数组，则$unwind输出文档
            preserveNullAndEmptyArrays: <boolean>
        } 
    }

    姓名为xx006的作者的book的tag数组拆分为多个文档
    db.books.aggregate([
        {$match:{"author.name":"xx006"}},
        {$unwind:"$tag"}
    ])

    每个作者的book的tag合集
    db.books.aggregate([
        {$unwind:"$tag"},
        {$group:{_id:"$author.name",types:{$addToSet:"$tag"}}}
    ])
    
    ·案例 示例数据
    db.books.insert([
    {
        "title" : "book-51",
        "type" : "technology",
        "favCount" : 11,
        "tag":[],
        "author" : {
        "name" : "fox",
        "age" : 28
        }
        },{
        "title" : "book-52",
        "type" : "technology",
        "favCount" : 15,
        "author" : {
        "name" : "fox",
        "age" : 28
        }
        },{
        "title" : "book-53",
        "type" : "technology",
        "tag" : [
        "nosql",
        "document"
        ],
        "favCount" : 20,
        "author" : {
        "name" : "fox",
        "age" : 28
    }
    }])

    ·测试
    # 使用includeArrayIndex选项来输出数组元素的数组索引
    db.books.aggregate([
        {$match:{"author.name":"fox"}},
        {$unwind:{path:"$tag", includeArrayIndex: "arrayIndex"}}
    ])
    # 使用preserveNullAndEmptyArrays选项在输出中包含缺少size字段，null或空数组的文档
    db.books.aggregate([
        {$match:{"author.name":"fox"}},
        {$unwind:{path:"$tag", preserveNullAndEmptyArrays: true}}
    ])

 ·$limit 限制传递到管道中下一阶段的文档数

     db.books.aggregate([
         {$limit : 5 }
     ])
    此操作仅返回管道传递给它的前5个文档。 $limit对其传递的文档内容没有影响。
    注意：当$sort在管道中的$limit之前立即出现时，$sort操作只会在过程中维持前n个结果，其中n是指定的限制，而MongoDB只需要将n个项存储在内存中。

 ·$skip 跳过进入stage的指定数量的文档，并将其余文档传递到管道中的下一个阶段
     
    db.books.aggregate([
        {$skip : 5 }
     ])
    此操作将跳过管道传递给它的前5个文档。 $skip对沿着管道传递的文档的内容没有影响

 ·$sort 对所有输入文档进行排序，并按排序顺序将它们返回到管道。
    语法：
    
    { $sort: { <field1>: <sort order>, <field2>: <sort order> ... } }
    要对字段进行排序，请将排序顺序设置为1或-1，以分别指定升序或降序排序，如下例所示：
    db.books.aggregate([
         {$sort : {favCount:-1,title:1}}
    ])

 ·$lookup: Mongodb 3.2版本新增，主要用来实现多表关联查询， 相当关系型数据库中多表关联查询。每个输入待处理的文档，经过$lookup阶段的处理，输出的新文档中会包含一个新生成的数组（可根据需要命名
     新key ）。数组列存放的数据是来自被Join集合的适配文档，如果没有，集合为空（即 为[ ])
    语法：
    
    db.collection.aggregate([{
        $lookup: {
        from: "<collection to join>",
        localField: "<field from the input documents>",
        foreignField: "<field from the documents of the from collection>",
        as: "<output array field>"
    }
    })
    
    属性              作用
    from             同一个数据库下等待被Join的集合。
    localField       源集合中的match值，如果输入的集合中，某文档没有 localField这个Key（Field），在处理的过程中，会默认为此文档含有 localField：null的键值对。
    foreignField     待Join的集合的match值，如果待Join的集合中，文档没有foreignField值，在处理的过程中，会默认为此文档含有 foreignField：null的键值对。
    as               为输出文档的新增值命名。如果输入的集合中已存在该值，则会覆盖掉
    注意：null = null 此为真
    其语法功能类似于下面的伪SQL语句：
    SELECT *, <output array field>
    FROM collection
    WHERE <output array field> IN (SELECT *
    FROM <collection to join>
    WHERE <foreignField>= <collection.localField>);

    数据准备
    db.customer.insert({customerCode:1,name:"customer1",phone:"13112345678",address: "test1"})
    db.customer.insert({customerCode:2,name:"customer2",phone:"13112345679",address: "test2"})
    db.order.insert({orderId:1,orderCode:"order001",customerCode:1,price:200})
    db.order.insert({orderId:2,orderCode:"order002",customerCode:2,price:400})
    db.orderItem.insert({itemId:1,productName:"apples",qutity:2,orderId:1})
    db.orderItem.insert({itemId:2,productName:"oranges",qutity:2,orderId:1})
    db.orderItem.insert({itemId:3,productName:"mangoes",qutity:2,orderId:1})
    db.orderItem.insert({itemId:4,productName:"apples",qutity:2,orderId:2})
    db.orderItem.insert({itemId:5,productName:"oranges",qutity:2,orderId:2})
    db.orderItem.insert({itemId:6,productName:"mangoes",qutity:2,orderId:2})

    关联查询
    db.customer.aggregate([
    {
        $lookup: {
        from: "order",
        localField: "customerCode",
        foreignField: "customerCode",
        as: "customerOrder"
                 }
    }
    ])

    db.order.aggregate([
    {$lookup: {
        from: "customer",
        localField: "customerCode",
        foreignField: "customerCode",
        as: "curstomer"
        }
        },
        {$lookup: {
        from: "orderItem",
        localField: "orderId",
        foreignField: "orderId",
        as: "orderItem"
             }
    }
    ])

    ·聚合操作示例1
        统计每个分类的book文档数量
        db.books.aggregate([
            {$group:{_id:"$type",total:{$sum:1}}},
            {$sort:{total:-1}}
        ])
        
        标签的热度排行，标签的热度则按其关联book文档的收藏数（favCount）来计算
        db.books.aggregate([
            {$match:{favCount:{$gt:0}}},
            {$unwind:"$tag"},
            {$group:{_id:"$tag",total:{$sum:"$favCount"}}},
            {$sort:{total:-1}}
        ])
        1. $match阶段：用于过滤favCount=0的文档。
        2. $unwind阶段：用于将标签数组进行展开，这样一个包含3个标签的文档会被拆解为3个条目。
        3. $group阶段：对拆解后的文档进行分组计算，$sum："$favCount"表示按favCount字段进行累       加。
        4. $sort阶段：接收分组计算的输出，按total得分进行排序。
        统计book文档收藏数[0,10),[10,60),[60,80),[80,100),[100,+∞）
        db.books.aggregate([{
            $bucket:{
                groupBy:"$favCount",
                boundaries:[0,10,60,80,100],
                default:"other",
                output:{"count":{$sum:1}}
            }
        }])

    ·聚合操作示例2
        导入邮政编码数据集 :https://media.mongodb.org/zips.json
        使用mongoimport工具导入数据（https://studio3t.com/download/）
             
        mongoimport -h 192.168.0.5 -d aggdemo -u test -p 123456 --
        authenticationDatabase=admin -c zips --file
        D:\zips.json  
        注意要手动保存页面上的json为JSON文件 到D:\zips.json    

             h,--host ：代表远程连接的数据库地址，默认连接本地Mongo数据库；
            --port：代表远程连接的数据库的端口，默认连接的远程端口27017；
            -u,--username：代表连接远程数据库的账号，如果设置数据库的认证，需要指定用户账号；
            -p,--password：代表连接数据库的账号对应的密码；
            -d,--db：代表连接的数据库；
            -c,--collection：代表连接数据库中的集合；
            -f, --fields：代表导入集合中的字段；
            --type：代表导入的文件类型，包括csv和json,tsv文件，默认json格式；
            --file：导入的文件名称
            --headerline：导入csv文件时，指明第一行是列名，不需要导入；

       返回人口超过1000万的州
            db.zips.aggregate( [
                { $group: { _id: "$state", totalPop: { $sum: "$pop" } } },
                { $match: { totalPop: { $gt: 10*1000*1000 } } }
            ] )

       这个聚合操作的等价SQL是： 
            SELECT state, SUM(pop) AS totalPop
            FROM zips
            GROUP BY state
            HAVING totalPop >= (10*1000*1000)
        
        返回各州平均城市人口
            db.zips.aggregate( [
            { $group: { _id: { state: "$state", city: "$city" }, cityPop: { $sum: "$pop" }
            } },
            { $group: { _id: "$_id.state", avgCityPop: { $avg: "$cityPop" } } },
            { $sort:{avgCityPop:-1}}
            ] )

        按州返回最大和最小的城市
            db.zips.aggregate( [
                { $group:
                   {
                    _id: { state: "$state", city: "$city" },
                    pop: { $sum: "$pop" }
                   }
                },
                { $sort: { pop: 1 } },
                { $group:
                    {
                    _id : "$_id.state",
                    biggestCity: { $last: "$_id.city" },
                    biggestPop: { $last: "$pop" },
                    smallestCity: { $first: "$_id.city" },
                    smallestPop: { $first: "$pop" }
                    }
                },
                { $project:
                    { _id: 0,
                    state: "$_id",
                    biggestCity: { name: "$biggestCity", pop: "$biggestPop" },
                    smallestCity: { name: "$smallestCity", pop: "$smallestPop" }
                    }
                },
                { $sort: { state: 1 } }
            ] )


 ###MapReduce
    MapReduce操作将大量的数据处理工作拆分成多个线程并行处理，然后将结果合并在一起。MongoDB提供的Map-Reduce非常灵活，对于大规模数据分析也相当实用。
    MapReduce具有两个阶段：
        1. 将具有相同Key的文档数据整合在一起的map阶段
        2. 组合map操作的结果进行统计输出的reduce阶段  

    MapReduce的基本语法
     db.collection.mapReduce(
        function() {emit(key,value);}, //map 函数
        function(key,values) {return reduceFunction}, //reduce 函数
        {
            out: <collection>,
            query: <document>,
            sort: <document>,
            limit: <number>,
            finalize: <function>,
            scope: <document>,
            jsMode: <boolean>,
            verbose: <boolean>,
          bypassDocumentValidation: <boolean>
        }
     )

     ·map，将数据拆分成键值对，交给reduce函数
     ·reduce，根据键将值做统计运算
     ·out，可选，将结果汇入指定表
     ·quey，可选筛选数据的条件，筛选的数据送入map
     ·sort，排序完后，送入map
     ·limit，限制送入map的文档数
     ·finalize，可选，修改reduce的结果后进行输出
     ·scope，可选，指定map、reduce、finalize的全局变量
     ·jsMode，可选，默认false。在mapreduce过程中是否将数 据转换成bson格式。
     ·verbose，可选，是否在结果中显示时间，默认false
     ·bypassDocmentValidation，可选，是否略过数据校验
    
    统计type为travel的不同作者的book文档收藏数

    db.books.mapReduce(
        function(){emit(this.author.name,this.favCount)},
        function(key,values){return Array.sum(values)},
        {
            query:{type:"travel"},
            out: "books_favCount"
        }
    )

### Springboot中整合MongoDB进行聚合操作
    MongoTemplate提供了aggregate方法来实现对数据的聚合操作。

    ·实体结构
        public class Zips {
            @Id //映射文档中的_id
            private String id;
            @Field
            private String city;
            @Field
            private Double[] loc;
            @Field
            private Integer pop;
            @Field
            private String state;
        }
返回人口超过1000万的州
    
    db.zips.aggregate( [
        { $group: { _id: "$state", totalPop: { $sum: "$pop" } } },
        { $match: { totalPop: { $gt: 10*1000*1000 } } }
    ] )
    ·java实现
    public void test(){
        //$group
        GroupOperation groupOperation =
        Aggregation.group("state").sum("pop").as("totalPop");
        //$match
        MatchOperation matchOperation = Aggregation.match(
        Criteria.where("totalPop").gte(10*1000*1000));
        // 按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation =
        Aggregation.newAggregation(Zips.class,
        groupOperation, matchOperation);
        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults =
        mongoTemplate.aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
             System.out.println(map);
        }
    }

·返回各州平均城市人口

    db.zips.aggregate( [
         { $group: { _id: { state: "$state", city: "$city" }, cityPop: { $sum: "$pop" }
    } },
        { $group: { _id: "$_id.state", avgCityPop: { $avg: "$cityPop" } } },
        { $sort:{avgCityPop:-1}}
    ] )


java实现

    @Test
    public void test2(){
        //$group
        GroupOperation groupOperation =
        Aggregation.group("state","city").sum("pop").as("cityPop");
        //$group
        GroupOperation groupOperation2 =
        Aggregation.group("_id.state").avg("cityPop").as("avgCityPop");
        //$sort
        SortOperation sortOperation =
        Aggregation.sort(Sort.Direction.DESC,"avgCityPop");
        // 按顺序组合每一个聚合步骤
        TypedAggregation<Zips> typedAggregation =
        Aggregation.newAggregation(Zips.class,
        groupOperation, groupOperation2,sortOperation);
        //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
        AggregationResults<Map> aggregationResults =
        mongoTemplate.aggregate(typedAggregation, Map.class);
        // 取出最终结果
        List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
            System.out.println(map);
        }
    }

按州返回最大和最小的城市
    
    db.zips.aggregate( [
        { $group:
        {
            _id: { state: "$state", city: "$city" },
            pop: { $sum: "$pop" }
        }
        },
        { $sort: { pop: 1 } },
        { $group:
        {
            _id : "$_id.state",
            biggestCity: { $last: "$_id.city" },
            biggestPop: { $last: "$pop" },
            smallestCity: { $first: "$_id.city" },
            smallestPop: { $first: "$pop" }
        }
        },
        { $project:
        { _id: 0,
            state: "$_id",
            biggestCity: { name: "$biggestCity", pop: "$biggestPop" },
            smallestCity: { name: "$smallestCity", pop: "$smallestPop" }
        }
        },
        { $sort: { state: 1 } }
    ] )

java实现

    @Test
    public void test3(){
    //$group
    GroupOperation groupOperation = Aggregation.group("state","city").sum("pop").as("pop");
    //$sort
    SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC,"pop");
    //$group
    GroupOperation groupOperation2 = Aggregation.group("_id.state")
                                                .last("_id.city").as("biggestCity")
                                                .last("pop").as("biggestPop")
                                                .first("_id.city").as("smallestCity")
                                                .first("pop").as("smallestPop");
    //$project
    ProjectionOperation projectionOperation = Aggregation
                                                .project("state","biggestCity","smallestCity")
                                                .and("_id").as("state")
                                                .andExpression(
                                                "{ name: \"$biggestCity\", pop: \"$biggestPop\" }")
                                                .as("biggestCity")
                                                .andExpression(
                                                "{ name: \"$smallestCity\", pop: \"$smallestPop\" }"
                                                ).as("smallestCity")
                                                .andExclude("_id");
    //$sort
    SortOperation sortOperation2 = Aggregation.sort(Sort.Direction.ASC,"state");
    
    // 按顺序组合每一个聚合步骤
    TypedAggregation<Zips> typedAggregation = Aggregation.newAggregation(
                        Zips.class, groupOperation, sortOperation, groupOperation2,
                        projectionOperation,sortOperation2);
    //执行聚合操作,如果不使用 Map，也可以使用自定义的实体类来接收数据
    AggregationResults<Map> aggregationResults = mongoTemplate.aggregate(typedAggregation, Map.class);
   
    // 取出最终结果
    List<Map> mappedResults = aggregationResults.getMappedResults();
        for(Map map:mappedResults){
          System.out.println(map);
        }
    }

###索引
    索引是一种用来快速查询数据的数据结构。B+Tree就是一种常用的数据库索引数据结构，MongoDB采用B+Tree 做索引，索引创建在colletions上。MongoDB不使用索引的查询，先扫描所有的文档，再
        匹配符合条件的文档。 使用索引的查询，通过索引找到文档，使用索引能够极大的提升查询效率。

    MongoDB索引数据结构
        B+Tree

    索引的分类
        ·按照索引包含的字段数量，可以分为单键索引和组合索引（或复合索引）。
        ·按照索引字段的类型，可以分为主键索引和非主键索引。
        ·按照索引节点与物理记录的对应方式来分，可以分为聚簇索引和非聚簇索引，其中聚簇索引是指索引节点上直接包含了数据记录，而后者则仅仅包含一个指向数据记录的指针。
        ·按照索引的特性不同，又可以分为唯一索引、稀疏索引、文本索引、地理空间索引等
  
    与大多数数据库一样，MongoDB支持各种丰富的索引类型，包括单键索引、复合索引，唯一索引等一些常用的结构。由于采用了灵活可变的文档类型，因此它也同样支持对嵌套字段、数组进行索引。通过
    建立合适的索引，我们可以极大地提升数据的检索速度。在一些特殊应用场景，MongoDB还支持地理空间索引、文本检索索引、TTL索引等不同的特性。

    ·索引操作
        创建索引语法格式
        db.collection.createIndex(keys, options)
        ·Key 值为你要创建的索引字段，1 按升序创建索引， -1 按降序创建索引
        ·可选参数列表如下：
        Parameter               Type            Description
        background              Boolean         建索引过程会阻塞其它数据库操作，background可 指定以后台方式创建索引，即增加 "background" 可选参数。 "background" 默认值为false。
        unique                  Boolean         建立的索引是否唯一。指定为true创建唯一索引。默认值为false.
        name                    string          索引的名称。如果未指定，MongoDB的通过连接索引的字段名和排序顺序生成一个索引名称。
        dropDups                Boolean         3.0+版本已废弃。在建立唯一索引时是否删除重复记录,指定 true 创建唯一索引。默认值为 false.
        sparse                  Boolean         对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 false.
        expireAfterSeconds      integer         指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。
        v                       index version   索引的版本号。默认的索引版本取决于mongod创建索引时运行的版本。
        weights                 document        索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。
        default_language        string          对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语
        language_override       string          对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language

    # 创建索引后台执行
    db.values.createIndex({open: 1, close: 1}, {background: true})
    # 创建唯一索引
    db.values.createIndex({title:1},{unique:true})

    
    ·查看索引
    #查看索引信息
    db.books.getIndexes()
    #查看索引键
    db.books.getIndexKeys()
    
    
    ·查看索引占用空间
    db.collection.totalIndexSize([is_detail])
    is_detail：可选参数，传入除0或false外的任意数据，都会显示该集合中每个索引的大小及总大小。如果传入0或false则只显示该集合中所有索引的总大小。默认值为false。

    ·删除索引
    #删除集合指定索引
    db.col.dropIndex("索引名称")
    #删除集合所有索引
    db.col.dropIndexes()     

    ·索引类型
        单键索引（Single Field Indexes）在某一个特定的字段上建立索引 mongoDB在ID上建立了唯一的单键索引,所以经常会使用id来进行查询； 在索引字段上进行精确匹配、排序以及范围查找都会使用此索引
        db.books.createIndex({title:1})
 
    复合索引（Compound Index）
    复合索引是多个字段组合而成的索引，其性质和单字段索引类似。但不同的是，复合索引中字段的顺序、字段的升降序对查询性能有直接的影响，因此在设计复合索引时则需要考虑不同的查询场景。
    db.books.createIndex({type:1,favCount:1})
    
    多键索引（Multikey Index）
    在数组的属性上建立索引。针对这个数组的任意值的查询都会定位到这个文档,既多个索引入口或者键值引用同一个文档

    准备inventory集合：
    db.inventory.insertMany([
    { _id: 5, type: "food", item: "aaa", ratings: [ 5, 8, 9 ] },
    { _id: 6, type: "food", item: "bbb", ratings: [ 5, 9 ] },
    { _id: 7, type: "food", item: "ccc", ratings: [ 9, 5, 8 ] },
    { _id: 8, type: "food", item: "ddd", ratings: [ 9, 5 ] },
    { _id: 9, type: "food", item: "eee", ratings: [ 5, 9, 5 ] }
    ])

    创建多键索引
        db.inventory.createIndex( { ratings: 1 } )
        
    多键索引很容易与复合索引产生混淆，复合索引是多个字段的组合，而多键索引则仅仅是在一个字段上出现了多键（multi key）。而实质上，多键索引也可以出现在复合字段上
    
    # 创建复合多键索引
    db.inventory.createIndex( { item:1,ratings: 1} )
    注意： MongoDB并不支持一个复合索引中同时出现多个数组字段嵌入文档的索引数组
    db.inventory.insertMany([
    {
        _id: 1,
        item: "abc",
        stock: [
            { size: "S", color: "red", quantity: 25 },
            { size: "S", color: "blue", quantity: 10 },
            { size: "M", color: "blue", quantity: 50 }
        ]
    },
    {
        _id: 2,
        item: "def",
        stock: [
            { size: "S", color: "blue", quantity: 20 },
            { size: "M", color: "blue", quantity: 5 },
            { size: "M", color: "black", quantity: 10 },
            { size: "L", color: "red", quantity: 2 }
        ]
    },
    {
        _id: 3,
        item: "ijk",
        stock: [
            { size: "M", color: "blue", quantity: 15 },
            { size: "L", color: "blue", quantity: 100 },
            { size: "L", color: "red", quantity: 25 }
        ]
    }
    ])

    在包含嵌套对象的数组字段上创建多键索引
    db.inventory.createIndex( { "stock.size": 1, "stock.quantity": 1 } )

    地理空间索引（Geospatial Index）
    在移动互联网时代，基于地理位置的检索（LBS）功能几乎是所有应用系统的标配。MongoDB为地理空间检索提供了非常方便的功能。地理空间索引（2dsphereindex）就是专门用于实现位置检索的一种特
    殊索引。
    案例：MongoDB如何实现“查询附近商家"？
    假设商家的数据模型如下：
    db.restaurant.insert({
        restaurantId: 0,
        restaurantName:"兰州牛肉面",
        location : {
        type: "Point",
        coordinates: [ -73.97, 40.77 ]
        }
    })

    创建一个2dsphere索引
        db.restaurant.createIndex({location : "2dsphere"})
    
    查询附近10000米商家信息
    db.restaurant.find( {
        location:{
            $near :{
                $geometry :{
                    type : "Point" ,
                    coordinates : [ -73.88, 40.78 ]
                } ,
                $maxDistance:10000
             }
        }
    } )

    ·$near查询操作符，用于实现附近商家的检索，返回数据结果会按距离排序。
    ·$geometry操作符用于指定一个GeoJSON格式的地理空间对象，type=Point表示地理坐标点，coordinates则是用户当前所在的经纬度位置；$maxDistance限定了最大距离，单位是米。

    全文索引（Text Indexes）
    MongoDB支持全文检索功能，可通过建立文本索引来实现简易的分词检索。
    db.reviews.createIndex( { comments: "text" } )
    
    $text操作符可以在有text index的集合上执行文本检索。$text将会使用空格和标点符号作为分隔符对检索字符串进行分词， 并且对检索字符串中所有的分词结果进行一个逻辑上的 OR 操作。
    全文索引能解决快速文本查找的需求，比如有一个博客文章集合，需要根据博客的内容来快速查找，则可以针对博客内容建立文本索引。
    
    TTL索引（TTL Indexes）  
    在一般的应用系统中，并非所有的数据都需要永久存储。例如一些系统事件、用户消息等，这些数据随着时间的推移，其重要程度逐渐降低。更重要的是，存储这些大量的历史数据需要花费较高的成本，因此项目中通常会对过期且
    不再使用的数据进行老化处理。

    通常的做法如下：
    方案一：为每个数据记录一个时间戳，应用侧开启一个定时器，按时间戳定期删除过期的数据。
    方案二：数据按日期进行分表，同一天的数据归档到同一张表，同样使用定时器删除过期的表。
    
    对于数据老化，MongoDB提供了一种更加便捷的做法：TTL（Time To Live）索引。TTL索引需要声明在一个日期类型的字段中，TTL 索引是特殊的单字段索引，MongoDB 可以使用它在一定时间或特定时
    钟时间后自动从集合中删除文档。
    
    # 创建 TTL 索引，TTL 值为3600秒
    db.eventlog.createIndex( { "lastModifiedDate": 1 }, { expireAfterSeconds: 3600 }
    )
    对集合创建TTL索引之后，MongoDB会在周期性运行的后台线程中对该集合进行检查及数据清理工作。除了数据老化功能，TTL索引具有普通索引的功能，同样可以用于加速数据的查询。
    TTL 索引不保证过期数据会在过期后立即被删除。文档过期和 MongoDB 从数据库中删除文档的时间之    间可能存在延迟。删除过期文档的后台任务每 60 秒运行一次。因此，在文档到期和后台任务运行之间
    的时间段内，文档可能会保留在集合中。
    
    案例
    数据准备
    db.log_events.insertOne( {
    "createdAt": new Date(),
    "logEvent": 2,
    "logMessage": "Success!"
    } )

    创建TTL索引
        db.log_events.createIndex( { "createdAt": 1 }, { expireAfterSeconds: 20 } )

    可变的过期时间
    TTL索引在创建之后，仍然可以对过期时间进行修改。这需要使用collMod命令对索引的定义进行变更
    db.runCommand({
        collMod:"log_events",index:{keyPattern: {createdAt:1},expireAfterSeconds:600}
    })

    TTL索引的确可以减少开发的工作量，而且通过数据库自动清理的方式会更加高效、可靠，但是在使用
    TTL索引时需要注意以下的限制：
    TTL索引只能支持单个字段，并且必须是非_id字段。
    TTL索引不能用于固定集合。
    TTL索引无法保证及时的数据老化，MongoDB会通过后台的TTLMonitor定时器来清理老化数据，默认的间隔时间是1分钟。当然如果在数据库负载过高的情况下，TTL的行为则会进一步受到影响。
    TTL索引对于数据的清理仅仅使用了remove命令，这种方式并不是很高效。因此TTL Monitor在运行期间对系统CPU、磁盘都会造成一定的压力。相比之下，按日期分表的方式操作会更加高效。

