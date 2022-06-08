package com.formssi.mall.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.formssi.mall.sharding.mapper.*;
import com.formssi.mall.sharding.pojo.*;
import com.formssi.mall.sharding.util.IdWorke;
import com.formssi.mall.sharding.util.SnowFlakeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("sharding")
@Api(tags = "sharding分库分表")
public class ShardingCotroller {

    @Autowired
    private OmsCartTMapper omsCartTMapper;

    @Autowired
    private OmsOrderDetailMapper omsOrderDetailMapper;

    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;

    @Autowired
    private OmsOrderBaseMapper omsOrderBaseMapper;

    @Autowired
    private SnowFlakeUtil snowFlakeUtil;

    @GetMapping("/order/insert")
    @ApiOperation(value = "订单分库分表表插入")
    public String insertOrder() {
        System.out.println("------------------------------插入ds0库--------------------------");
        OmsOrderItemDO omsOrderItemDO = new OmsOrderItemDO();
        int random = new Random().nextInt( 10 ) + 1;
        /*SnowFlakeUtil worker = new SnowFlakeUtil(random, 1, random);
        long snowID = worker.nextId();*/
        IdWorke IdWorke = new IdWorke(random,random);
        long snowID = IdWorke.nextId()+random;
        omsOrderItemDO.setOrderId(snowID);
        String setOrderSn = "SKT_"+ snowID;
        omsOrderItemDO.setOrderSn(setOrderSn);
        omsOrderItemDO.setSkuId("121212");
        omsOrderItemDO.setSpuSn("245566");
        long userID = 8888888888L; // ds0库
        omsOrderItemDO.setUserId(userID);
        omsOrderItemDO.setPrice(10L);
        omsOrderItemDO.setQuantity(2L);
        omsOrderItemDO.setCreateTime(new Date());
        omsOrderItemMapper.insert(omsOrderItemDO);

        OmsOrderDetailDO omsOrderDetailDO = OmsOrderDetailDO.builder().orderId(snowID).orderSn(setOrderSn).totalAmount(100000L).createTime(new Date()).expireTime(new Date())
                .integration(100L).status(1).memberId(999999999L).userId(userID).build();

        omsOrderDetailMapper.insert(omsOrderDetailDO);

        System.out.println("------------------------------插入ds1库--------------------------");
        OmsOrderItemDO omsOrderItemDO1 = new OmsOrderItemDO();
        /*SnowFlakeUtil worker1 = new SnowFlakeUtil(1, 1, 1);
        long snowID1 = worker.nextId();*/
        IdWorke IdWorke1 = new IdWorke(random,random);
        long snowID1 = IdWorke1.nextId()+random;
        omsOrderItemDO1.setOrderId(snowID);
        String setOrderSn1 = "SKT_"+ snowID;
        omsOrderItemDO1.setOrderSn(setOrderSn1);
        omsOrderItemDO1.setSkuId("121212");
        omsOrderItemDO1.setSpuSn("245566");
        long userID1 = 8888888889L; // ds1库
        omsOrderItemDO1.setUserId(userID1);
        omsOrderItemDO1.setPrice(10L);
        omsOrderItemDO1.setQuantity(2L);
        omsOrderItemDO1.setCreateTime(new Date());
        omsOrderItemMapper.insert(omsOrderItemDO1);

        OmsOrderDetailDO omsOrderDetailDO1 = OmsOrderDetailDO.builder().orderId(snowID1).orderSn(setOrderSn).totalAmount(100000L).createTime(new Date()).expireTime(new Date())
                .integration(100L).status(1).memberId(999999999L).userId(userID1).build();

        omsOrderDetailMapper.insert(omsOrderDetailDO1);
        return "success";
    }


    @GetMapping("/order/select")
    @ApiOperation(value = "订单查询")
    public String selectOrder() {
        System.out.println("------------------------------------------------------单个查询-------------------------------------");
        // 分片键都不指定 全局路由+拼接
        List<OmsOrderItemDO> orderList1 = omsOrderItemMapper.selectList(null);
        List<OmsOrderDetailDO> orderDetailList1 = omsOrderDetailMapper.selectList(null);

        // 指定user_id 只会确定从ds0库查询 表的话全查   、、 指定订单order_id  库不明确，表明确 、、 指定order_id、user_id库明确、表明确
        QueryWrapper<OmsOrderItemDO> queryItemWrapper1 = new QueryWrapper();
        queryItemWrapper1.eq("user_id", 8888888888L);
        List<OmsOrderItemDO> orderList2 = omsOrderItemMapper.selectList(queryItemWrapper1);

        QueryWrapper<OmsOrderDetailDO> queryDetailWrapper1 = new QueryWrapper();
        queryDetailWrapper1.eq("user_id", 8888888888L);
        List<OmsOrderDetailDO> orderDetailList2 = omsOrderDetailMapper.selectList(queryDetailWrapper1);
        System.out.println("------------------------------------------------------关联查询-------------------------------------");
        // 关联查询
        // 指定user_id  都去ds0库查，4X4个表做关联 = 16 个语句
        omsOrderItemMapper.findOrderAndDetailByUserId(8888888888L);
        // 指定order_id 明确了表oms_order_item_t0  但不明确库 不明确oms_order_detail 4X2 =8个语句
        omsOrderItemMapper.findOrderAndDetailByOrderId(1531469396088852480L);

        // 指定user_id  指定order_id  明确了库ds0 明确了表oms_order_item_t0  没明确oms_order_detail   1X4 =4 个语句
        omsOrderItemMapper.findOrderAndDetailByuserIDAndOrderId(8888888888L,1531469396088852480L);

        // 不指定user_id 、不指定order_id 2X4X4 =32 条语句
        omsOrderItemMapper.findOrderAndDetailAll();

        // TODO 注意分库分表的分页 limit
        // 简单单表场景下limit a，b ，在分库分表场景下，如果不更改这个范围，无论怎么处理得出的结果都不可能正确
        // 事实上：对于limit a，b ，在分库分表场景中，使用极值法，很容知道查询的数据覆盖范围是limit 0，a+b
        /*将 LIMIT 10000000, 10 改写为 LIMIT 0, 10000010，才能保证其数据的正确性。 用户非常容易产生
        ShardingSphere 会将大量无意义的数据加载至内存中，造成内存溢出风险的错觉。
        事实上：ShardingSphere 会通过结果集的 next 方法将无需取出的数据全部跳过，
        并不会将其存入内存，这种在多个流上进行归并的操作称为【流式归并】，
        由【执行引擎】决定，并由【归并引擎】做具体数据归并操作。【流式归并】对应的策略为【内存归并】。
        归并策略的选择依赖maxConnectionSizePerQuery的配置，当单个库的线程数>=该库中执行的执行的条数，则优先使用【流式归并】*/
        // 分页
        // 不指定分分库、分片键 分页 映射成 limit ?,? ::: [0, 3] 结果正确  4X4X2==32 条语句 搞两条数据出来
        omsOrderItemMapper.findOrderAndDetailPage(1,2);
        // 指定了分库键 ds0库中 4X4 ==16 语句 搞两条数据出来  limit ?,? ::: [8888888888, 0, 3]  结果正确
        omsOrderItemMapper.findOrderAndDetailByUserIdPage(8888888888L,1,2);
        return "success";
    }

    @GetMapping("/order/base")
    @ApiOperation(value = "测试广播表")
    public String insertBase() {
        // ds0\ds1两个库都会又同一条数据
        OmsOrderBaseDO omsOrderBaseDO = OmsOrderBaseDO.builder().attr1("1111").attr2("222").attr3("3333").attr4("444").attr5("555")
                .name("kongquan").pwd("qweqwe").build();
        omsOrderBaseMapper.insert(omsOrderBaseDO);
        return "success";
    }

    @GetMapping("/order/cart")
    @ApiOperation(value = "自定义分片")
    public String insertCart() {
        // ds0 oms_cart_t2
        OmsCartT omsCartT1 = OmsCartT.builder().userId(66666666L).createTime("2022-5").skuId(123456L).spuId(789778L).quantity(1).build();
        omsCartTMapper.insert(omsCartT1);

        // ds1 oms_cart_t3
        OmsCartT omsCartT2 = OmsCartT.builder().userId(66666667L).createTime("2022-5").skuId(5555555L).spuId(789778L).quantity(1).build();
        omsCartTMapper.insert(omsCartT2);
        return "success";
    }

    @GetMapping("/order/snow")
    @ApiOperation(value = "自定义分片")
    public String snow() {
        System.out.println("雪花："+snowFlakeUtil.nextId());
        return "success";
    }
}
