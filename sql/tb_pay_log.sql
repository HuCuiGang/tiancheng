/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : pinyougoudb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-01-05 14:50:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_pay_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_log`;
CREATE TABLE `tb_pay_log` (
  `out_trade_no` varchar(30) NOT NULL COMMENT '支付订单号',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` bigint(20) DEFAULT NULL COMMENT '支付金额（分）',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `transaction_id` varchar(30) DEFAULT NULL COMMENT '交易号码',
  `trade_state` varchar(1) DEFAULT NULL COMMENT '交易状态',
  `order_list` varchar(200) DEFAULT NULL COMMENT '订单编号列表',
  `pay_type` varchar(1) DEFAULT NULL COMMENT '支付类型',
  PRIMARY KEY (`out_trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
