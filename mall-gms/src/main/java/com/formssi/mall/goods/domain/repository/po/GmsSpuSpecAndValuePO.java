package com.formssi.mall.goods.domain.repository.po;

import com.formssi.mall.gms.dto.GmsSpuSpecValueDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jiangyaoting
 */
@Data
public class GmsSpuSpecAndValuePO implements Serializable {

    private Long id;

    private Long catalogId;

    private String specName;

    private Integer status;

    private Long vid;

    private Long specId;

    private String specValue;

    private String specImage;

    public GmsSpuSpecValueDTO getGmsSpuSpecValueDTO() {
        GmsSpuSpecValueDTO gmsSpuSpecValueDTO = new GmsSpuSpecValueDTO();
        gmsSpuSpecValueDTO.setId(vid);
        gmsSpuSpecValueDTO.setSpecId(specId);
        gmsSpuSpecValueDTO.setSpecValue(specValue);
        gmsSpuSpecValueDTO.setSpecImage(specImage);
        return gmsSpuSpecValueDTO;
    }
}
