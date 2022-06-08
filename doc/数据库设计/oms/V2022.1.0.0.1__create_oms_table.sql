CREATE TABLE `oms_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cart_key` varchar(255) NOT NULL COMMENT '购物车Key',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `expire_time` datetime NOT NULL COMMENT '到期时间',
  `member_id` bigint DEFAULT NULL COMMENT '会员ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车';

CREATE TABLE `oms_cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cart_id` bigint NOT NULL COMMENT '购物车ID',
  `spu_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint NOT NULL COMMENT '产品ID',
  `quantity` int NOT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车详情';

CREATE TABLE `oms_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(255) NOT NULL COMMENT '订单编号',
  `receiver_name` varchar(255) DEFAULT NULL COMMENT '收货人',
  `receiver_area_name` varchar(255) DEFAULT NULL COMMENT '省市区',
  `receiver_address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `receiver_phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `total_amount` decimal(12,0) NOT NULL COMMENT '总金额',
  `pay_amount` decimal(12,0) DEFAULT NULL COMMENT '实际支付金额',
  `freight_amount` decimal(12,0) DEFAULT NULL COMMENT '运费金额',
  `integration_amount` decimal(12,0) DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(12,0) DEFAULT NULL COMMENT '优惠券抵扣金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `integration` bigint NOT NULL COMMENT '可获取的积分',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int NOT NULL COMMENT '订单状态，1：待付款，2：待发货，3：待收货，4：已完成',
  `member_id` bigint NOT NULL COMMENT '会员ID',
  `bill_type` int DEFAULT NULL COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',
  `bill_header` varchar(200) DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(200) DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) DEFAULT NULL COMMENT '收票人邮箱',
  `order_type` int DEFAULT NULL COMMENT '订单类型：0->正常订单；1->秒杀订单',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_sn` (`order_sn`) USING BTREE,
  KEY `idx_member_id` (`member_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE `oms_order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `spu_sn` varchar(64) DEFAULT NULL COMMENT '商品编号',
  `spu_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `spu_image` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `sku_id` varchar(255) DEFAULT NULL COMMENT '产品ID',
  `price` decimal(12,0) NOT NULL COMMENT '价格',
  `quantity` decimal(12,0) NOT NULL COMMENT '数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情';

CREATE TABLE `oms_pay` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_sn` varchar(64) NOT NULL COMMENT '订单编号',
  `receiver_account` varchar(255) NOT NULL COMMENT '收款账户',
  `payer_account` varchar(255) DEFAULT NULL COMMENT '付款账户',
  `pay_amount` decimal(12,0) NOT NULL COMMENT '付款金额',
  `fee` decimal(12,0) NOT NULL COMMENT '支付手续费',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `pay_type` int DEFAULT NULL COMMENT '支付方式 0->未支付，1支付宝，2微信，3银行卡支付',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付表';

