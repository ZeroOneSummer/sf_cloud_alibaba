package com.formssi.mall.order.domain.vo;

import com.formssi.mall.order.infrastructure.es.annotation.EsEquals;
import com.formssi.mall.order.infrastructure.es.annotation.EsHighlight;
import com.formssi.mall.order.infrastructure.es.annotation.EsHighlightStyled;
import com.formssi.mall.order.infrastructure.es.annotation.EsRange;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EsHighlightStyled(postTags = "</font>",preTags = "<font color='red'>")
@Document(indexName="oms",shards=5,replicas=1,indexStoreType="fs",refreshInterval="-1")
@Data
public class ElasticsearchOrderQueryVo implements Serializable {

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
    @EsHighlight
    private String orderSn;

    /**
     * 收货人
     */
    @EsHighlight
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
    @EsHighlight
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

    /**
     * 发票抬头
     */
    @EsEquals
    @EsHighlight
    private String billHeader;


    /**
     * 游标：用全局唯一字段就行，也可以多个，我用orderID+createTime组合定义一个游标
     */
    private List<String> sortValues;
}
