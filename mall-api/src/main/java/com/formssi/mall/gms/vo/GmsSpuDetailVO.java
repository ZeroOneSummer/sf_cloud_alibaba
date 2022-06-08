package com.formssi.mall.gms.vo;

import com.formssi.mall.gms.dto.GmsSkuDto;
import com.formssi.mall.gms.dto.GmsSpuAttributeDTO;
import com.formssi.mall.gms.dto.GmsSpuDTO;
import lombok.Data;

import java.util.List;

/**
 * @author:prms
 * @create: 2022-04-20 15:01
 * @version: 1.0
 */
@Data
public class GmsSpuDetailVO {

    private GmsSpuDTO gmsSpuDTO;

    private List<GmsSpuAttributeDTO> attrList;

    private List<GmsSkuDto> skuList;

}
