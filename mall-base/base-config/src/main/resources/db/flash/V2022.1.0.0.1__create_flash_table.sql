CREATE TABLE `flash_act` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `act_name` varchar(64) NOT NULL COMMENT '活动名称',
  `approval_status` tinyint DEFAULT '1' COMMENT '审核状态 0未通过 1通过',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `option_status` int DEFAULT '1' COMMENT '操作状态(0、1、2)分别代表D、I、U',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀活动表';



CREATE TABLE `flash_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `act_id` bigint NOT NULL COMMENT '秒杀活动ID',
  `spu_sn` varchar(128) DEFAULT ''  COMMENT '商品编号',
  `name` varchar(128) DEFAULT ''  COMMENT '商品名称',
  `flash_image` varchar(255) DEFAULT '' COMMENT '秒杀图片',
  `flash_info` longtext  COMMENT '秒杀商品介绍',
  `price` decimal(12,0) NOT NULL COMMENT '价格',
  `flash_price` decimal(12,0) NOT NULL  COMMENT '秒杀价',
  `sku_id` bigint NOT NULL  COMMENT '产品ID',
  `flash_stock` int NOT NULL COMMENT '秒杀总库存',
  `limit_qty` int DEFAULT '10' COMMENT '每人限制购买数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `option_status` tinyint NOT NULL COMMENT '操作状态(0、1、2)分别代表D、I、U',
  PRIMARY KEY (`id`),
  KEY `idx_sku_id` (`sku_id`) USING BTREE,
  KEY `idx_act_id` (`act_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表';
