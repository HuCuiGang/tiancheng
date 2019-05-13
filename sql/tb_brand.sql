/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : sport

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-24 18:54:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_brand
-- ----------------------------
DROP TABLE IF EXISTS `tb_brand`;
CREATE TABLE `tb_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(40) NOT NULL COMMENT '名称',
  `description` varchar(80) DEFAULT NULL COMMENT '描述',
  `img_url` varchar(80) DEFAULT NULL COMMENT '图片Url',
  `web_site` varchar(80) DEFAULT NULL COMMENT '品牌网址',
  `sort` int(11) DEFAULT NULL COMMENT '排序:最大最排前',
  `is_display` tinyint(1) DEFAULT NULL COMMENT '是否可见 1:可见 0:不可见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='品牌';

-- ----------------------------
-- Records of tb_brand
-- ----------------------------
INSERT INTO `tb_brand` VALUES ('1', '依琦莲', null, null, null, '99', '1');
INSERT INTO `tb_brand` VALUES ('2', '凯速（KANSOON）', null, null, null, null, '1');
INSERT INTO `tb_brand` VALUES ('3', '梵歌纳（vangona）', null, null, null, null, '1');
INSERT INTO `tb_brand` VALUES ('4', '伊朵莲', null, null, null, null, '1');
INSERT INTO `tb_brand` VALUES ('5', '菩媞', null, null, null, null, '1');
INSERT INTO `tb_brand` VALUES ('6', '丹璐斯', null, null, null, null, '1');
INSERT INTO `tb_brand` VALUES ('7', '喜悦瑜伽', null, null, null, null, '0');
INSERT INTO `tb_brand` VALUES ('8', '金乐乐', '金乐乐', 'upload/20141201101326051272.jpg', null, '44', '1');
