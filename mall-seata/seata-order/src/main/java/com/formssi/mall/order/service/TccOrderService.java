package com.formssi.mall.order.service;

import com.formssi.mall.order.entity.TccOrderEntity;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;


@LocalTCC
public interface TccOrderService {

    @TwoPhaseBusinessAction(name = "TccOrderService",
                            commitMethod = "commit",
                            rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext actionContext,
                           @BusinessActionContextParameter(paramName = "mapper/order") TccOrderEntity tccOrderEntity);

    public boolean commit(BusinessActionContext actionContext);

    public boolean rollback(BusinessActionContext actionContext);
}
