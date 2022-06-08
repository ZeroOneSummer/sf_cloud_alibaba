package com.formssi.mall.oms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author eirc-ye
 * @since 20224/12 15:47
 */
@Data
public class OmsOrderQuery extends PageQry {
    private int status;
}
