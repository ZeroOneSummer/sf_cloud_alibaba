package com.formssi.mall.oms.dto;

import lombok.Data;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/12 9:50
 */
@Data
public class OmsOrderStatusCountDTO {
    /**
     * 已完成訂單
     */
    private Long successOrder;
    /**
     * 待付款
     */
    private Long nonPaymentOrder;
    /**
     * 待发货
     */
    private Long deliveryOrder;
    /**
     * 已发货
     */
    private Long ShippedOrder;
}
