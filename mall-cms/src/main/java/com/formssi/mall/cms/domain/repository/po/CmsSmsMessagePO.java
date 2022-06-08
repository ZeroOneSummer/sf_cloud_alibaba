package com.formssi.mall.cms.domain.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@TableName("cms_sms_message")
public class CmsSmsMessagePO implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 短信内容
     */
    @TableField("content")
    private String content;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 短信模版
     */
    @TableField("sms_type")
    private String smsType;

    /**
     * 0：未发送，1：已发送
     */
    @TableField("status")
    private String status;


}