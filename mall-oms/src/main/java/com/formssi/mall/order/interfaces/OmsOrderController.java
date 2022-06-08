package com.formssi.mall.order.interfaces;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.oms.cmd.OmsOrderCmd;
import com.formssi.mall.oms.dto.OmsOrderDTO;
import com.formssi.mall.oms.query.OmsOrderQuery;
import com.formssi.mall.order.application.IOmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/12 16:54
 */
@RestController
@RequestMapping("oms")
public class OmsOrderController {
    @Autowired
    IOmsOrderService iomsOrderService;

    @RequestMapping(value = "/queryOrderStatusCount")
    public CommonResp queryOrderStatusCount(int memberId) {

        return CommonResp.ok(iomsOrderService.queryOrderStatusCount(memberId));
    }

    @RequestMapping(value = "/omsOrderQuery")
    public CommonPage<OmsOrderDTO> queryByOrderStatus(@RequestBody OmsOrderQuery omsOrderQuery) {
        return iomsOrderService.queryByOrderStatus(omsOrderQuery);
    }
    @RequestMapping(value = "/saveOrder")
    public CommonResp saveOrder(OmsOrderCmd omsOrderCmd) {
        return iomsOrderService.saveOrder(omsOrderCmd);
    }

}
