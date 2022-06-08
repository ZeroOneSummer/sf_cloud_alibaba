package com.formssi.mall.ums.domain.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class MPObjectHandler implements MetaObjectHandler {

    private String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("MP Handler insert executor ....");
        this.openInsertFill();
        this.setFieldValByName("createTime", getDate(), metaObject);
        this.setFieldValByName("updateTime", getDate(), metaObject);
        //this.setFieldValByName("kid", String.valueOf(UUID.randomUUID()).replaceAll("-","").substring(0,18), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("MP Handler update executor ....");
        this.setFieldValByName("updateTime", getDate(), metaObject);
    }
}
