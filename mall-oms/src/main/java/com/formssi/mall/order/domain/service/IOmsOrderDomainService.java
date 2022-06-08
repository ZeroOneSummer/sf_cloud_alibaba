package com.formssi.mall.order.domain.service;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.order.domain.entity.OmsOrderDO;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;

/**
 * <p>
 * 訂單領域服務
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/11 17:16
 */
public interface IOmsOrderDomainService {
     /**
      * 下單
      * @param OmsOrderCmd
      * @return
      */
     public CommonResp saveOmsOrder(OmsOrderCmd OmsOrderCmd);
     
}
