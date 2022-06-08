package com.formssi.mall.minio.service.impl;

import com.formssi.mall.common.entity.resp.CommonResp;
import com.formssi.mall.common.util.ExceptionUtils;
import com.formssi.mall.exception.define.ResultCodeEnum;
import com.formssi.mall.exception.exception.BusinessException;
import com.formssi.mall.minio.config.MinioProperties;
import com.formssi.mall.minio.entity.UploadDTO;
import com.formssi.mall.minio.service.MinioService;

import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public CommonResp upload(MultipartFile file) {
        try {
            log.info("minioProperties {}", minioProperties);
            MinioClient minioClient = getMinioClient();
            //创建一个MinIO的Java客户端
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (isExist) {
                log.info("存储桶已经存在！");
            } else {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }
            String filename = UUID.randomUUID().toString().replace("-", "");
            // 使用putObject上传一个文件到存储桶中
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .object(filename)
                    .bucket(minioProperties.getBucketName())
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build();
            minioClient.putObject(objectArgs);
            log.info("文件上传成功!");
            UploadDTO uploadDTO = new UploadDTO();
            uploadDTO.setName(filename);
            uploadDTO.setUrl(minioProperties.getConsole() + filename);
            return CommonResp.ok(uploadDTO);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTraceAsString(e));
            throw new BusinessException("文件上传异常");
        }
    }

    @Override
    public void preview(String objectName, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        MinioClient minioClient = getMinioClient();
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build())) {
            IOUtils.copy(stream, response.getOutputStream());
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTraceAsString(e));
        }
    }

    @Override
    public ResponseEntity download(String objectName) {
        MinioClient minioClient = getMinioClient();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept-Ranges", "bytes");
        httpHeaders.add("Content-disposition", "attachment; filename=" + objectName);
        httpHeaders.add("Content-Type", "text/plain;charset=utf-8");
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build())) {
            if (stream == null) {
                log.warn("文件不存在");
                return new ResponseEntity<>(new byte[0], httpHeaders, HttpStatus.CREATED);
            }
            byte[] bytes = IOUtils.toByteArray(stream);
            httpHeaders.add("Content-Length", String.valueOf(bytes.length));
            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTraceAsString(e));
            return new ResponseEntity<>(new byte[0], httpHeaders, HttpStatus.CREATED);
        }
    }

    @Override
    public CommonResp delete(String objectName) {
        MinioClient minioClient = getMinioClient();
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build());
            return CommonResp.ok();
        } catch (Exception e) {
            log.warn(ExceptionUtils.getStackTraceAsString(e));
            throw new BusinessException("文件删除异常");
        }
    }

    private MinioClient getMinioClient() {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

}
