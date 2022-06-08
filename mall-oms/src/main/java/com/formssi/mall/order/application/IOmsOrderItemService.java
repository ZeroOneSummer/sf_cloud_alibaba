package com.formssi.mall.order.application;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.oms.dto.OmsOrderDTO;
import com.formssi.mall.oms.dto.OmsOrderItemDTO;
import com.formssi.mall.oms.query.OmsOrderItemQuery;
import com.formssi.mall.oms.query.OmsOrderQuery;
import com.formssi.mall.order.domain.vo.ElasticsearchOrderQueryVo;

import javax.websocket.server.PathParam;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/13 14:42
 */
public interface IOmsOrderItemService {
    public OmsOrderItemDTO queryByOmsOrderSn(String orderSn);


    CommonResp sendSms();

    CommonResp sendSms2();

    CommonResp download(String userId, String loginType);

    CommonResp es();

    CommonResp save();

    CommonResp findElasticsearchOrderVoList(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo);

    CommonResp findOrderVoListBySearchAfter(ElasticsearchOrderQueryVo elasticsearchOrderQueryVo);
}
