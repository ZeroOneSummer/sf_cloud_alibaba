package com.formssi.mall.goods.interfaces;

import com.formssi.mall.common.entity.resp.CommonPage;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.gms.cmd.GmsSpuSpecCmd;
import com.formssi.mall.gms.cmd.GmsSpuSpecValueCmd;
import com.formssi.mall.gms.dto.GmsSpuSpecDTO;
import com.formssi.mall.gms.query.GmsSpuSpecPageQry;
import com.formssi.mall.goods.application.GmsSpuSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gms")
public class GmsSpecController {

    @Autowired
    private GmsSpuSpecService gmsSpuSpecService;


    /**
     * 查询商品规格列表
     * @param gmsSpuSpecPageQry
     * @return
     */
    @PostMapping("/listGmsSpecPage")
    public CommonPage<GmsSpuSpecDTO> listGmsSpecPage(@RequestBody GmsSpuSpecPageQry gmsSpuSpecPageQry) {
        return gmsSpuSpecService.listGmsSpecPage(gmsSpuSpecPageQry);
    }

    /**
     * 新增一条规格
     * @param gmsSpuSpecCmd
     * @return
     */
    @PostMapping("/saveGmsSpec")
    public CommonResp saveGmsSpec(@RequestBody GmsSpuSpecCmd gmsSpuSpecCmd) {
        gmsSpuSpecService.saveGmsSpec(gmsSpuSpecCmd);
        return CommonResp.ok();
    }

    /**
     * 新增一条规格值
     * @param gmsSpuSpecValueCmd
     * @return
     */
    @PostMapping("/savaGmsSpecValue")
    public CommonResp savaGmsSpecValue(@RequestBody GmsSpuSpecValueCmd gmsSpuSpecValueCmd){
        gmsSpuSpecService.savaGmsSpecValue(gmsSpuSpecValueCmd);
        return CommonResp.ok();
    }

    /**
     * 根据id删除一条规格
     * @param id
     * @return
     */
    @DeleteMapping("/deleteGmsSpec/{id}")
    public CommonResp deleteGmsSpec(@PathVariable("id") Long id) {
        gmsSpuSpecService.deleteGmsSpec(id);
        return CommonResp.ok();
    }

    /**
     * 修改一条规格
     * @param gmsSpuSpecCmd
     * @return
     */
    @PutMapping("/updateGmsSpec")
    public CommonResp updateGmsSpec(@RequestBody GmsSpuSpecCmd gmsSpuSpecCmd) {
        gmsSpuSpecService.updateGmsSpec(gmsSpuSpecCmd);
        return CommonResp.ok();
    }

    /**
     * 批量删除规格
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteGmsSpecBatch")
    public CommonResp deleteGmsSpecBatch(@RequestParam List<Long> ids) {
        gmsSpuSpecService.deleteGmsSpecBatch(ids);
        return CommonResp.ok();
    }
}
