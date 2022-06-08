CREATE TABLE `mq_log_all`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `msg_module`  varchar(64)   NOT NULL COMMENT '消息模块',
    `msg_topic`   varchar(64)   NOT NULL COMMENT '消息主题',
    `msg_tags`    varchar(64) DEFAULT NULL COMMENT '消息标签',
    `msg_keys`    varchar(64) DEFAULT NULL COMMENT '消息键',
    `msg_body`    varchar(2000) NOT NULL COMMENT '消息体',
    `msg_status`  varchar(2)    NOT NULL COMMENT '消息发送状态 0：消息草稿',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_msg_module` (`msg_module`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='消息总记录表';

CREATE TABLE `mq_log_success`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `msg_id`      varchar(64)   NOT NULL COMMENT '消息id',
    `msg_topic`   varchar(64)   NOT NULL COMMENT '消息主题',
    `msg_tags`    varchar(64) DEFAULT NULL COMMENT '消息标签',
    `msg_keys`    varchar(64) DEFAULT NULL COMMENT '消息键',
    `msg_body`    varchar(2000) NOT NULL COMMENT '消息体',
    `msg_status`  varchar(2)    NOT NULL COMMENT '消息发送状态 1:发送成功  2：消费成功 3：消费失败',
    `msg_module`  varchar(40)   NOT NULL COMMENT '消息模块',
    `msg_offset`  varchar(64)  DEFAULT NULL COMMENT '消息偏移量',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_msg_id` (`msg_id`) USING BTREE,
    KEY `idx_msg_topic` (`msg_topic`) USING BTREE,
    KEY `idx_msg_tags` (`msg_tags`) USING BTREE,
    KEY `idx_msg_keys` (`msg_keys`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='消息发送成功表';

CREATE TABLE `mq_log_fail`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `msg_topic`   varchar(64)   NOT NULL COMMENT '消息主题',
    `msg_tags`    varchar(64) DEFAULT NULL COMMENT '消息标签',
    `msg_keys`    varchar(64) DEFAULT NULL COMMENT '消息键',
    `msg_body`    varchar(2000) NOT NULL COMMENT '消息体',
    `msg_status`  varchar(2)    NOT NULL COMMENT '消息发送状态 4：消息发送失败',
    `msg_module`  varchar(40)   NOT NULL COMMENT '消息模块',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_msg_topic` (`msg_topic`) USING BTREE,
    KEY `idx_msg_tags` (`msg_tags`) USING BTREE,
    KEY `idx_msg_keys` (`msg_keys`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='消息发送失败表';

