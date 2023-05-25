/*
 Navicat Premium Data Transfer

 Source Server         : mysql_roud
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : roudblog

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 25/05/2023 18:28:41
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
INSERT INTO `rb_article` VALUES (1683686058593, '测试', 'roud', 'test', 'https://img2.baidu.com/it/u=3081590981,1708125283&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500', '# 测试', '2023-05-10 10:33:58');
INSERT INTO `rb_article` VALUES (1683688315714, '111', '111', '11', '111', '111', '2023-05-10 11:11:50');
INSERT INTO `rb_article` VALUES (1684488724481, '232', '2424', '4242', '4242', '1', '2023-05-19 17:32:01');
INSERT INTO `rb_article` VALUES (1684488735069, '13131', '313131', '3131', '3131', '313131', '2023-05-19 17:32:12');
INSERT INTO `rb_article` VALUES (1684488743859, '3131', '31', '31', '13', '31313', '2023-05-19 17:32:21');
INSERT INTO `rb_article` VALUES (1684488752293, '3131', '3131', '3131', '3131', '3131', '2023-05-19 17:32:30');
INSERT INTO `rb_article` VALUES (1684488759742, '3131', '313', '31', '13', '313', '2023-05-19 17:32:38');
INSERT INTO `rb_article` VALUES (1684488768318, '3131', '3131', '13', '31', '1331', '2023-05-19 17:32:46');

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
INSERT INTO `rb_article_tag` VALUES (1683686058608, 1683686058593, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1683686058613, 1683686058593, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1683688315723, 1683688315714, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1684488724502, 1684488724481, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1684488735077, 1684488735069, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1684488743867, 1684488743859, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1684488752300, 1684488752293, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1684488759748, 1684488759742, 1683686058606);
INSERT INTO `rb_article_tag` VALUES (1684488768324, 1684488768318, 1683686058606);

-- ----------------------------
-- Table structure for rb_comment
-- ----------------------------
DROP TABLE IF EXISTS `rb_comment`;
CREATE TABLE `rb_comment`  (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `from_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `op_time` datetime(0) NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `article_id` bigint(20) NULL DEFAULT NULL,
  `to_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rb_comment
-- ----------------------------
INSERT INTO `rb_comment` VALUES (1683686070562, '测试', 'admin', '2023-05-10 10:34:31', -1, 1683686058593, NULL);
INSERT INTO `rb_comment` VALUES (1683686079993, '测试', 'admin', '2023-05-10 10:34:40', -1, 1683686058593, NULL);
INSERT INTO `rb_comment` VALUES (1683688292989, '1111', 'admin', '2023-05-10 11:11:33', -1, 1683686058593, NULL);
INSERT INTO `rb_comment` VALUES (1683704811625, '测试', 'admin', '2023-05-10 15:46:52', -1, 1683686058593, NULL);
INSERT INTO `rb_comment` VALUES (1684910350558, '子评论', 'admin', '2023-05-24 14:39:11', 1683704811625, 1683686058593, 'admin');

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
INSERT INTO `rb_tag` VALUES (1683686058606, '测试', '', '2023-05-10 10:33:58');

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
INSERT INTO `rb_user` VALUES (1684809247353, 'admin', 'admin@roud.top', '3E6C7D141E32189C917761138B026B75', '2023-05-10 10:33:03', 0, '0');
INSERT INTO `rb_user` VALUES (1684914793219, 'demo', 'demo@roud.top', 'EFB5B25EF2D37BCB483D628576520691', '2023-05-24 15:53:13', 2, '1');

-- ----------------------------
-- Table structure for rb_user_extends
-- ----------------------------
DROP TABLE IF EXISTS `rb_user_extends`;
CREATE TABLE `rb_user_extends`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `img_id` bigint(20) NULL DEFAULT NULL,
  `sex` int(255) NULL DEFAULT NULL,
  `motto` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `recentlyip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rb_user_extends
-- ----------------------------
INSERT INTO `rb_user_extends` VALUES (1684809247366, 1684809247353, 1685008869409, 0, '哈哈哈哈', '10.240.21.107');
INSERT INTO `rb_user_extends` VALUES (1684914793591, 1684914793219, 1685003440540, 0, NULL, '10.240.21.107');

SET FOREIGN_KEY_CHECKS = 1;
