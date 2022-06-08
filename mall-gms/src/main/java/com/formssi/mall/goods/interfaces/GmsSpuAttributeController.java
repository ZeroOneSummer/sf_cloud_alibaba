package com.formssi.mall.goods.interfaces;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.gms.dto.GmsSpuAttributeDTO;
import com.formssi.mall.gms.dto.GmsSpuSpecDTO;
import com.formssi.mall.gms.query.GmsSpuAttributePageQry;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.application.GmsSpuAttributeService;
import com.formssi.mall.goods.application.GmsSpuSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gms")
public class GmsSpuAttributeController {

    @Autowired
    private GmsSpuAttributeService gmsSpuAttributeService;

    /**
     * 商品属性查询列表
     * @param gmsSpuAttributePageQry
     * @return
     */
    @PostMapping("/listSpuAttributePage")
    public CommonPage<GmsSpuAttributeDTO> listSpuAttributePage(@RequestBody GmsSpuAttributePageQry gmsSpuAttributePageQry) {
        return gmsSpuAttributeService.listSpuAttributePage(gmsSpuAttributePageQry);
    }

    /**
     * 新增一条商品属性
     * @param gmsSpuAttributeDTO
     * @return
     */
    @PostMapping("/saveSpuAttribute")
    public CommonResp saveSpuAttribute(@RequestBody GmsSpuAttributeDTO gmsSpuAttributeDTO) {
        gmsSpuAttributeService.saveSpuAttribute(gmsSpuAttributeDTO);
        return CommonResp.ok();
    }

    /**
     * 根据id删除一条商品属性
     * @param id
     * @return
     */
    @DeleteMapping("/deleteSpuAttribute/{id}")
    public CommonResp deleteSpuAttribute(@PathVariable("id") Long id) {
        gmsSpuAttributeService.deleteSpuAttribute(id);
        return CommonResp.ok();
    }

    /**
     * 修改一条商品属性
     * @param gmsSpuAttributeDTO
     * @return
     */
    @PutMapping("/updateSpuAttribute")
    public CommonResp updateSpuAttribute(@RequestBody GmsSpuAttributeDTO gmsSpuAttributeDTO) {
        gmsSpuAttributeService.updateSpuAttribute(gmsSpuAttributeDTO);
        return CommonResp.ok();
    }

    /**
     * 批量删除商品属性
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteSpuAttributeBatch")
    public CommonResp deleteSpuAttributeBatch(@RequestParam List<Long> ids) {
        gmsSpuAttributeService.deleteSpuAttributeBatch(ids);
        return CommonResp.ok();
    }
}
