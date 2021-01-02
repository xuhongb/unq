/*
Navicat MySQL Data Transfer

Source Server         : 121.199.10.167(测试)
Source Server Version : 50635
Source Host           : 121.199.10.167:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2021-01-02 20:40:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for event_list
-- ----------------------------
DROP TABLE IF EXISTS `event_list`;
CREATE TABLE `event_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` varchar(20) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for prize
-- ----------------------------
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for prize_list
-- ----------------------------
DROP TABLE IF EXISTS `prize_list`;
CREATE TABLE `prize_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `entry_time` varchar(50) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(50) DEFAULT NULL COMMENT '姓',
  `first_name` varchar(50) DEFAULT NULL COMMENT '名',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `first_dept` varchar(50) DEFAULT NULL COMMENT '一级部门',
  `second_dept` varchar(50) DEFAULT NULL COMMENT '二级部门',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `entry_time` varchar(50) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `status1` smallint(6) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=925 DEFAULT CHARSET=utf8;
