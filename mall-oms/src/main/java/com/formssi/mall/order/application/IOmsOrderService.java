package com.formssi.mall.order.application;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.oms.dto.OmsOrderDTO;
import com.formssi.mall.oms.dto.OmsOrderStatusCountDTO;
import com.formssi.mall.oms.query.OmsOrderQuery;

import java.util.List;

/**
 * <p>
 * 訂單應用服務
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/12 9:16
 */
public interface IOmsOrderService {
      /**
       * 根據訂單狀態查詢
       * @param omsOrderQuery
       * @return
       */
      public CommonPage<OmsOrderDTO> queryByOrderStatus(OmsOrderQuery omsOrderQuery);

      /**
       * 根據會員統計訂單
       * @param memberId
       * @return
       */
      public OmsOrderStatusCountDTO   queryOrderStatusCount(int memberId);

      public CommonResp  saveOrder(OmsOrderCmd omsOrderCmd);
}
