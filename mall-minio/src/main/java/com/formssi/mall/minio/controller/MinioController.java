package com.formssi.mall.minio.controller;


import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.minio.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioService minioService;

    /**
     * 上传文件
     */
    @PostMapping(value = "/upload")
    public Object upload(@RequestParam("file") MultipartFile file) {
        return minioService.upload(file);
    }


    /**
     * 在线预览
     */
    @GetMapping("/preview/{objectName}")
    public void preview(@PathVariable("objectName") String objectName, HttpServletResponse response) {
        minioService.preview(objectName, response);
    }

    /**
     * 下载文件
     */
    @GetMapping("/download/{objectName}")
    public ResponseEntity download(@PathVariable("objectName") String objectName) {
        return minioService.download(objectName);
    }

    /**
     * 删除文件
     */
    @GetMapping("/delete/{objectName}")
    public CommonResp delete(@PathVariable("objectName") String objectName) {
        return minioService.delete(objectName);
    }

}