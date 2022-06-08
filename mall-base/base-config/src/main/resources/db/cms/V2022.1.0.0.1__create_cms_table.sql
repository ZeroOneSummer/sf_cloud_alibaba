CREATE TABLE `cms_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '会员名称',
  `mobile` varchar(100) NOT NULL COMMENT '手机',
  `phone` varchar(100) DEFAULT NULL COMMENT '电话',
  `nick_name` varchar(120) DEFAULT NULL COMMENT '昵称',
  `open_id` varchar(255) DEFAULT NULL COMMENT 'openId',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮件',
  `gender` tinyint DEFAULT NULL COMMENT '性别 1：男，2：女',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `spent` decimal(12,0) NOT NULL DEFAULT '0' COMMENT '消费金额',
  `balance` decimal(12,0) NOT NULL DEFAULT '0' COMMENT '可用余额',
  `point` int NOT NULL DEFAULT '0' COMMENT '可用积分',
  `login_ip` varchar(255) DEFAULT NULL COMMENT '登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '登录时间',
  `login_failed_count` int NOT NULL DEFAULT '0' COMMENT '连续登录失败次数',
  `is_locked` tinyint DEFAULT NULL COMMENT '是否锁定 0：正常，1：锁定',
  `locked_date` datetime DEFAULT NULL COMMENT '锁定时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

CREATE TABLE `cms_sms_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mobile` varchar(100) NOT NULL COMMENT '手机号码',
  `content` varchar(255) NOT NULL COMMENT '短信内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sms_type` tinyint NOT NULL COMMENT '短信模版',
  `status` tinyint NOT NULL COMMENT '0：未发送，1：已发送',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信消息表';

CREATE TABLE `cms_area` (
  `code` varchar(32) NOT NULL COMMENT '区域编号',
  `name` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `level` tinyint DEFAULT NULL COMMENT '级别',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父级编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区域表';

CREATE TABLE `cms_member_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `receiver_name` varchar(255) DEFAULT NULL COMMENT '收货人',
  `receiver_area_name` varchar(255) DEFAULT NULL COMMENT '省市区',
  `receiver_address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `receiver_phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `is_default` tinyint NOT NULL COMMENT '是否默认 1：默认地址，0：普通地址',
  `area_id` varchar(32) NOT NULL COMMENT '区域ID',
  `member_id` bigint NOT NULL COMMENT '会员ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `idx_area_id` (`area_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员地址表';
