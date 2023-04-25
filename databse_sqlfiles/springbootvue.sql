/*
 Navicat Premium Data Transfer

 Source Server         : roud-mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : springbootvue

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 04/09/2022 22:52:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sv_article
-- ----------------------------
DROP TABLE IF EXISTS `sv_article`;
CREATE TABLE `sv_article`  (
  `id` bigint(20) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `postbody` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `publishtime` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sv_article
-- ----------------------------
INSERT INTO `sv_article` VALUES (1662215555073, '今天天气今天天气今天天气很好今天天气很好今天天气很好', '胡', '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容', 'http://roud.top/img/20220904132503.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215555850, '测2', '胡', '11', 'http://roud.top/img/20220904132503.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215557483, '测2', '胡', '11', 'http://roud.top/img/20220904132503.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215558187, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215559280, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215559821, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215560991, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215561589, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215562570, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215562921, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215564125, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215564888, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215565989, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215566723, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215568105, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215568774, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215569881, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215570525, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662215571631, '测2', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `sv_article` VALUES (1662229605202, '测666', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');

-- ----------------------------
-- Table structure for sv_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `sv_article_tag`;
CREATE TABLE `sv_article_tag`  (
  `id` bigint(20) NOT NULL,
  `article_id` bigint(20) NULL DEFAULT NULL,
  `tag_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sv_article_tag
-- ----------------------------
INSERT INTO `sv_article_tag` VALUES (1662215555181, 1662215555073, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215555193, 1662215555073, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215555858, 1662215555850, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215555867, 1662215555850, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215557496, 1662215557483, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215557503, 1662215557483, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215558196, 1662215558187, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215558202, 1662215558187, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215559292, 1662215559280, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215559298, 1662215559280, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215559834, 1662215559821, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215559839, 1662215559821, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215561000, 1662215560991, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215561005, 1662215560991, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215561597, 1662215561589, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215561603, 1662215561589, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215562579, 1662215562570, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215562585, 1662215562570, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215562927, 1662215562921, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215562933, 1662215562921, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215564141, 1662215564125, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215564148, 1662215564125, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215564895, 1662215564888, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215564900, 1662215564888, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215565999, 1662215565989, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215566009, 1662215565989, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215566731, 1662215566723, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215566736, 1662215566723, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215568112, 1662215568105, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215568117, 1662215568105, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215568781, 1662215568774, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215568787, 1662215568774, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215569895, 1662215569881, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215569903, 1662215569881, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215570531, 1662215570525, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215570536, 1662215570525, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662215571637, 1662215571631, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662215571643, 1662215571631, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662229605242, 1662229605202, 1662204241325);
INSERT INTO `sv_article_tag` VALUES (1662229605249, 1662229605202, 1662204241335);
INSERT INTO `sv_article_tag` VALUES (1662229605259, 1662229605202, 1662229605255);

-- ----------------------------
-- Table structure for sv_comment
-- ----------------------------
DROP TABLE IF EXISTS `sv_comment`;
CREATE TABLE `sv_comment`  (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `publishtime` datetime(0) NULL DEFAULT NULL,
  `comment_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sv_tag
-- ----------------------------
DROP TABLE IF EXISTS `sv_tag`;
CREATE TABLE `sv_tag`  (
  `id` bigint(20) NOT NULL,
  `tagname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `addtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sv_tag
-- ----------------------------
INSERT INTO `sv_tag` VALUES (1662204241325, '入门', '', '2022-09-05 00:00:00');
INSERT INTO `sv_tag` VALUES (1662204241335, '进阶', '', '2022-09-05 00:00:00');
INSERT INTO `sv_tag` VALUES (1662229605255, '升华', '', '2022-09-03 22:32:20');

-- ----------------------------
-- Table structure for sv_user
-- ----------------------------
DROP TABLE IF EXISTS `sv_user`;
CREATE TABLE `sv_user`  (
  `id` bigint(20) NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phonenumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `registertime` datetime(0) NULL DEFAULT NULL,
  `type` int(10) NULL DEFAULT NULL,
  `power` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sv_user
-- ----------------------------
INSERT INTO `sv_user` VALUES (1654836360756, 'roud', 'roud244@163.com', 'Aa12345678q', '2022-06-10 12:46:00', 1, '0');
INSERT INTO `sv_user` VALUES (1654836475778, '123_121', '11111@163.com', '12345As1', '2022-06-10 12:47:55', 1, '0');
INSERT INTO `sv_user` VALUES (1654843311714, 'tete123', '255311@qq.com', '11232AdAS12', '2022-06-10 14:41:51', 1, '1');
INSERT INTO `sv_user` VALUES (1654843714513, '1212aa', '23232@aa.com', '129192193As', '2022-06-10 14:48:34', 0, '1');
INSERT INTO `sv_user` VALUES (1654843742436, 'aasa12', '121212@qq.com', 'aaa1213A', '2022-06-10 14:49:02', 0, '1');
INSERT INTO `sv_user` VALUES (1655402499677, 'roud', '2553112258@qq.com', '123456789As', '2022-06-17 02:01:40', 1, '1');
INSERT INTO `sv_user` VALUES (1657711859619, 'abcqwe', '2581665906@qq.com', '123Qwer11', '2022-07-13 19:31:00', 1, '0');

SET FOREIGN_KEY_CHECKS = 1;
