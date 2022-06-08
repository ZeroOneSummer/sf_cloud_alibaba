package com.formssi.mall.gms.query;

import com.formssi.mall.common.entity.resp.PageQry;
import lombok.Data;

/**
 * <p>
 * 商品管理查询对象
 * </p>
 *
 * @author hudemin
 * @since 2022/4/14 16:00
 */
@Data
public class GmsSpuQry extends PageQry {

    private String spuName;

}
