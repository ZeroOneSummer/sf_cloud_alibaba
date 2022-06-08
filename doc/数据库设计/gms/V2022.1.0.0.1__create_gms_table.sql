CREATE TABLE `gms_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) NOT NULL COMMENT '上级编号',
  `image` varchar(255) DEFAULT NULL COMMENT '分类图片',
  `level` int NOT NULL DEFAULT '0' COMMENT '层级',
  `priority` int NOT NULL COMMENT '优先级',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `option_status` int NOT NULL COMMENT '操作状态(0、1、2)分别代表D、I、U',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`) USING BTREE,
  KEY `idx_priority` (`priority`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类';

CREATE TABLE `gms_spu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `catalog_id` bigint NOT NULL COMMENT '分类ID',
  `spu_sn` varchar(128) NOT NULL COMMENT '商品编号',
  `name` varchar(64) NOT NULL COMMENT '商品名称',
  `sub_title` varchar(128) DEFAULT NULL COMMENT '副标题',
  `image` varchar(255) NOT NULL COMMENT '展示图片',
  `introduce` longtext DEFAULT NULL COMMENT '商品介绍',
  `price` decimal(12,0) NOT NULL COMMENT '价格',
  `market_price` decimal(12,0) DEFAULT NULL COMMENT '市场价',
  `total_sales` bigint NOT NULL DEFAULT '0' COMMENT '总销量',
  `total_score` float NOT NULL COMMENT '总评分',
  `total_comment` bigint NOT NULL DEFAULT '0' COMMENT '总评论',
  `priority` int NOT NULL COMMENT '优先级',
  `spu_images` text COMMENT '商品图片',
  `virtual_stock` int NOT NULL COMMENT '虚拟库存',
  `enable_integral` int NOT NULL COMMENT '可用积分',
  `spu_status` tinyint NOT NULL COMMENT '商品状态 1：上架，2：下架',
  `approval_status` tinyint NOT NULL COMMENT '审批状态 1: 通过，2拒绝',
  `refuse_reason` varchar(255) DEFAULT NULL COMMENT '拒绝原因',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键字',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `option_status` tinyint NOT NULL COMMENT '操作状态(0、1、2)分别代表D、I、U',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_spu_sn` (`spu_sn`) USING BTREE,
  KEY `idx_priority` (`priority`) USING BTREE,
  KEY `idx_price` (`price`) USING BTREE,
  KEY `idx_total_sales` (`total_sales`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

CREATE TABLE `gms_spu_spec` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `catalog_id` bigint NOT NULL COMMENT '分类ID',
  `spec_name` varchar(64) DEFAULT NULL COMMENT '规格名',
  `status` int DEFAULT NULL COMMENT '规格类型 0：官方，1：自定义',
  PRIMARY KEY (`id`),
  KEY `idx_catalog_id` (`catalog_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格名';

CREATE TABLE `gms_spu_spec_value` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `spec_id` bigint DEFAULT NULL COMMENT '规格ID',
  `spec_value` varchar(64) NOT NULL COMMENT '规格值',
  `spec_image` varchar(255) DEFAULT NULL COMMENT '规格图片',
  PRIMARY KEY (`id`),
  KEY `idx_spec_id` (`spec_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格值';

CREATE TABLE `gms_spu_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `spu_id` bigint NOT NULL COMMENT '商品ID',
  `attribute_key` bigint(20) DEFAULT NULL COMMENT '属性key',
  `attribute_value` varchar(255) NOT NULL COMMENT '自定义属性值',
  PRIMARY KEY (`id`),
  KEY `idx_spu_id` (`spu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性表';

CREATE TABLE `gms_sku` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `spu_id` bigint NOT NULL COMMENT '商品ID',
  `sku_sn` varchar(255) NOT NULL COMMENT '产品编号',
  `price` decimal(12,0) NOT NULL COMMENT '销售价',
  `market_price` decimal(12,0) DEFAULT NULL COMMENT '市场价',
  `stock` int NOT NULL COMMENT '库存',
  `lock_stock` int NOT NULL DEFAULT '0' COMMENT '锁定库存',
  `spec_data` varchar(500) NOT NULL COMMENT '规格名Id和规格值Id组合，可使用JSON格式',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sku_sn` (`sku_sn`) USING BTREE,
  KEY `idx_spu_id` (`spu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='sku库存表';