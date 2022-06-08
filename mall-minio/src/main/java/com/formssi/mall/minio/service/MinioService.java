package com.formssi.mall.minio.service;


import com.formssi.mall.common.entity.resp.CommonResp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface MinioService {

    CommonResp upload(MultipartFile file);

    void preview(String objectName, HttpServletResponse response);

    ResponseEntity download(String objectName);

    CommonResp delete(String objectName);

}
