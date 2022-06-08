package com.formssi.mall.oms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 2022/4/13 14:46
 */
@Data
public class OmsOrderItemQuery extends PageQry {
    private String orderSn;
}
