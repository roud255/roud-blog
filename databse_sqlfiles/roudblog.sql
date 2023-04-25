/*
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : roudblog

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 25/04/2023 15:14:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rb_article
-- ----------------------------
DROP TABLE IF EXISTS `rb_article`;
CREATE TABLE `rb_article`  (
  `id` bigint(20) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `postbody` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `publishtime` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_article
-- ----------------------------
INSERT INTO `rb_article` VALUES (1662215555073, '今天天气今天天气今天天气很好今天天气很好今天天气很好', '胡', '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容', 'http://roud.top/img/20220904132503.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `rb_article` VALUES (1662229605202, '测666', '胡', '11', 'https://cdn.jsdelivr.net/gh/roud255/picbed/md/20220903010157.png', '##  测试', '2022-09-03 22:32:20');
INSERT INTO `rb_article` VALUES (1675071474940, '测试文章2023-01-30', 'roud', '测试', 'http://roud.top/img/20230129104937.png', '# 测试内容标题1\n测试商品如下：\n![阿加阿卡卡搭建打开的](http://roud.top/img/20230129104937.png)\n\n[商品](http://roud.top/img/20230129104937.png)', '2023-01-30 17:34:15');

-- ----------------------------
-- Table structure for rb_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `rb_article_tag`;
CREATE TABLE `rb_article_tag`  (
  `id` bigint(20) NOT NULL,
  `article_id` bigint(20) NULL DEFAULT NULL,
  `tag_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_article_tag
-- ----------------------------
INSERT INTO `rb_article_tag` VALUES (1662215555181, 1662215555073, 1662204241325);
INSERT INTO `rb_article_tag` VALUES (1662215555193, 1662215555073, 1662204241335);
INSERT INTO `rb_article_tag` VALUES (1662229605242, 1662229605202, 1662204241325);
INSERT INTO `rb_article_tag` VALUES (1662229605249, 1662229605202, 1662204241335);
INSERT INTO `rb_article_tag` VALUES (1662229605259, 1662229605202, 1662229605255);
INSERT INTO `rb_article_tag` VALUES (1675071474957, 1675071474940, 1675071474954);

-- ----------------------------
-- Table structure for rb_comment
-- ----------------------------
DROP TABLE IF EXISTS `rb_comment`;
CREATE TABLE `rb_comment`  (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `publishtime` datetime(0) NULL DEFAULT NULL,
  `comment_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_comment
-- ----------------------------

-- ----------------------------
-- Table structure for rb_forbidip
-- ----------------------------
DROP TABLE IF EXISTS `rb_forbidip`;
CREATE TABLE `rb_forbidip`  (
  `id` bigint(20) NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_forbidip
-- ----------------------------
INSERT INTO `rb_forbidip` VALUES (1665203632135, '12.212.12.21', '12121', '2022-10-08 12:33:29');
INSERT INTO `rb_forbidip` VALUES (1672828834792, '116.31.233.68', '111', '2023-01-04 18:40:33');

-- ----------------------------
-- Table structure for rb_msg
-- ----------------------------
DROP TABLE IF EXISTS `rb_msg`;
CREATE TABLE `rb_msg`  (
  `id` bigint(20) NOT NULL,
  `msg` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `send_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `send_time` datetime(0) NULL DEFAULT NULL,
  `send_man` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_msg
-- ----------------------------

-- ----------------------------
-- Table structure for rb_msg_copy1
-- ----------------------------
DROP TABLE IF EXISTS `rb_msg_copy1`;
CREATE TABLE `rb_msg_copy1`  (
  `id` bigint(20) NOT NULL,
  `msg` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `send_date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `send_time` datetime(0) NULL DEFAULT NULL,
  `send_man` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_msg_copy1
-- ----------------------------

-- ----------------------------
-- Table structure for rb_tag
-- ----------------------------
DROP TABLE IF EXISTS `rb_tag`;
CREATE TABLE `rb_tag`  (
  `id` bigint(20) NOT NULL,
  `tagname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `addtime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_tag
-- ----------------------------
INSERT INTO `rb_tag` VALUES (1662204241325, '入门', '', '2022-09-05 00:00:00');
INSERT INTO `rb_tag` VALUES (1662204241335, '进阶', '', '2022-09-05 00:00:00');
INSERT INTO `rb_tag` VALUES (1662229605255, '升华', '', '2022-09-03 22:32:20');
INSERT INTO `rb_tag` VALUES (1675071474954, '测试', '', '2023-01-30 17:34:15');

-- ----------------------------
-- Table structure for rb_user
-- ----------------------------
DROP TABLE IF EXISTS `rb_user`;
CREATE TABLE `rb_user`  (
  `id` bigint(20) NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phonenumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `registertime` datetime(0) NULL DEFAULT NULL,
  `type` int(10) NULL DEFAULT NULL,
  `power` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_user
-- ----------------------------
INSERT INTO `rb_user` VALUES (1657711859619, 'admin', 'admin@roud.top', '3E6C7D141E32189C917761138B026B74', '2022-07-13 19:31:00', 0, '0');

SET FOREIGN_KEY_CHECKS = 1;
