### SpringBoot整合Redisson,如何使用RedissonLockService工具类

### Redisson已整合redis配置文件使用,详情查看相关代码及ppt<Redisson介绍与使用.pptx>
### 项目结构-> com.formssi.mall.redis
    annotation : 自定义注解使用分布式锁
    config : redisson自动化配置
    service : redisson使用工具类
    strategy : 实例化策略(默认为单机部署,集群跟哨兵未测试)
    
    

### 分布式锁的使用
    下面示例中，是定义一个接口，如果直接在工程内调用使用redissonLockService工具类直接调用
    进行相关业务场景操作，如下订单时,可把库存相关操作锁住,操作完再释放锁,避免超卖问题。
    具体业务场景具体分析
    @GetMapping("mall-lock-decrease-stock")
    public String lockDecreaseStock() throws InterruptedException {
        redissonLockService.lock("lock", 10L);
        if (TOTAL > 0) {
            TOTAL--;
        }
        Thread.sleep(50);
        log.info("===lock===减完库存后,当前库存===" + TOTAL);
        //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
        if (redissonLockService.isHeldByCurrentThread("lock")) {
            redissonLockService.unlock("lock");
        }
    }
    上锁：redissonLockService.lock("lock", 10L);
    解锁：redissonLockService.unlock("lock");
  
 
### 获取分布式ID(商品编号,订单编号,库存编号)
    分布式ID组成部分：
    单号前缀（固定前缀+时间戳） + redis自增序号 + 随机数 
    
    接口定义
    @GetMapping("distributed-id")
    public String getDistributedId() throws InterruptedException {
        return redisService.getDistributedId(NumberTypeEnum.ORDER_NUM);
    }
    
    工具类直接获取：
    String orderNum = redisService.getDistributedId(NumberTypeEnum.ORDER_NUM);
    
    NumberTypeEnum枚举类支持三种单号类型：商品编号，订单编号，库存编号
    详情可查看工具类代码：RedisService.java
 
