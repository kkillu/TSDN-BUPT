/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : tsdn

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2016-10-15 23:50:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for calendar
-- ----------------------------
DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(255) NOT NULL,
  `ingress` varchar(255) NOT NULL,
  `srcPort` varchar(255) NOT NULL,
  `egress` varchar(255) NOT NULL,
  `distPort` varchar(255) NOT NULL,
  `bandWidth` varchar(255) NOT NULL,
  `serviceType` varchar(255) NOT NULL,
  `start` varchar(255) NOT NULL,
  `end` varchar(255) NOT NULL,
  `uuid` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of calendar
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
