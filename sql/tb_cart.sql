CREATE TABLE `tb_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `item_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `item_title` varchar(100) DEFAULT NULL COMMENT '商品标题',
  `item_image` varchar(200) DEFAULT NULL COMMENT '商品主图',
  `item_price` bigint(20) DEFAULT NULL COMMENT '商品价格，单位为：分',
  `num` int(10) DEFAULT NULL COMMENT '购买数量',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId_itemId` (`user_id`,`item_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='购物车模块';
