package com.formssi.mall.cms.interfaces;


import com.formssi.mall.common.entity.req.CommonReq;
import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.cms.application.CmsAreaService;
import com.formssi.mall.cms.cmd.CmsAreaCmd;
import com.formssi.mall.cms.domain.entity.CmsArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cmsArea")
@Slf4j
public class CmsAreaController {
    @Autowired
    private CmsAreaService cmsAreaService;
    @GetMapping("/list")
    public CommonResp getList(){
        return CommonResp.ok(cmsAreaService.getList());
    }

    @PostMapping("/getOne")
    public CommonResp getOne(@RequestBody @Valid CmsAreaCmd cmsAreaCmd){
        return CommonResp.ok(cmsAreaService.getone(cmsAreaCmd.getCode()));
    }
    @PostMapping("/saveOne")
    public CommonResp saveOne(@RequestBody @Valid CmsAreaCmd cmsAreaCmd){
        cmsAreaService.saveone(cmsAreaCmd);
        return CommonResp.ok();
    }
    @PostMapping("/saveList")
    public CommonResp saveList(@RequestBody List<CmsArea> commonReq){
        cmsAreaService.savelist(commonReq);
        return CommonResp.ok();
    }
    @PostMapping("/deleteOne")
    public CommonResp deleteOne(@RequestBody @Valid CmsAreaCmd cmsAreaCmd){
        cmsAreaService.deleteone(cmsAreaCmd);
        return CommonResp.ok();
    }
    @PostMapping("/deleteList")
    public CommonResp deleteList(@RequestBody CommonReq<List<CmsArea>> commonReq){
        cmsAreaService.deletelist(commonReq.getBody());
        return CommonResp.ok();
    }
    @PostMapping("/updateOne")
    public CommonResp updateOne(@RequestBody @Valid CmsAreaCmd cmsAreaCmd){
        cmsAreaService.updateone(cmsAreaCmd);
        return CommonResp.ok();
    }
    @PostMapping("/updateList")
    public CommonResp updateList(@RequestBody CommonReq<List<CmsArea>> commonReq){
        cmsAreaService.updatelist(commonReq.getBody());
        return CommonResp.ok();
    }
    @PostMapping("/getAreas")
    public CommonResp getAreas(@RequestBody @Valid CmsAreaCmd cmsAreaCmd){
        return CommonResp.ok(cmsAreaService.getOnebyCode(cmsAreaCmd.getCode()));
    }
}