package com.formssi.mall.goods.interfaces;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.gms.query.GmsCategoryPageQry;
import com.formssi.mall.gms.query.GmsSpuPageByCateQry;
import com.formssi.mall.goods.application.GmsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 商品分类查询
 *
 * @author:prms
 * @create: 2022-04-19 15:16
 * @version: 1.0
 */
@RestController
@RequestMapping("/gms")
@Api(tags = "gms-商品分类")
public class GmsCategoryController {

    @Resource
    GmsCategoryService gmsCategoryService;

    /**
     * 查询商品分类
     * @param req
     * @return
     */
    @GetMapping("/cate")
    @ApiOperation("查询商品分类树")
    public CommonResp gmsCategoryTreeNode(GmsCategoryPageQry req) {
        return CommonResp.ok(gmsCategoryService.cateTreeNode(req));
    }

    /**
     * 查询分类下商品列表
     * @param req
     * @return
     */
    @GetMapping("/cate/spu")
    @ApiOperation("分类查询商品列表")
    public CommonResp gmsListSpuByCateId(@Valid GmsSpuPageByCateQry req) {
        return CommonResp.ok(gmsCategoryService.gmsListSpuByCateId(req));
    }







}
