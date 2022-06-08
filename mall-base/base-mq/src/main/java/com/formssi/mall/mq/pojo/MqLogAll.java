package com.formssi.mall.mq.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息总记录表
 * @ApiModel(value="MqLogAll对象", description="消息总记录表")
 * </p>
 *
 * @author kongquan
 * @since 2022-05-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("mq_log_all")
public class MqLogAll implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @ApiModelProperty(value = "ID")
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *@ApiModelProperty(value = "消息模块")
     */
    private String msgModule;

    /**
     * @ApiModelProperty(value = "消息主题")
     */
    private String msgTopic;

    /**
     * @ApiModelProperty(value = "消息标签")
     */
    private String msgTags;

    /**
     * @ApiModelProperty(value = "消息键")
     */
    private String msgKeys;

    /**
     *@ApiModelProperty(value = "消息体")
     */
    private String msgBody;

    /**
     *@ApiModelProperty(value = "消息发送状态 0：消息草稿")
     */
    private String msgStatus;

    /**
     *@ApiModelProperty(value = "创建时间")
     */
    private Date createTime;

    /**
     *@ApiModelProperty(value = "修改时间")
     */
    private Date updateTime;


}
