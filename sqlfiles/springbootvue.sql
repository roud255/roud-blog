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

 Date: 01/09/2022 23:28:29
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
-- Table structure for sv_product
-- ----------------------------
DROP TABLE IF EXISTS `sv_product`;
CREATE TABLE `sv_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `producingArea` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `groundingDate` datetime(0) NULL DEFAULT NULL,
  `goodsCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `introduction_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  CONSTRAINT `sv_product_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `bf_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sv_product
-- ----------------------------
INSERT INTO `sv_product` VALUES (1, '广西武鸣沃柑 单果约100g起 甜柑橘 新鲜水果', 3, '广西 南宁', '2022-01-27 17:14:48', 'BF0001', '皮薄肉厚，清甜多汁，香嫩可口', 1, './static/img/product/wogan.png', './static/img/product/introduction/wogan.png');
INSERT INTO `sv_product` VALUES (2, '云南大白菜 烧烤食材 产地直供 新鲜蔬菜', 2, '云南 玉溪', '2022-01-27 23:17:08', 'BF0002', '新鲜，美味，价格便宜', 1, './static/img/product/baicai.png', './static/img/product/introduction/baicai.png');
INSERT INTO `sv_product` VALUES (3, '越南进口白心火龙果 白肉中果 单果375-500g 新鲜水果', 12, '越南', '2022-01-27 23:20:59', 'BF0003', '健康轻食', 1, './static/img/product/huolongguo.png', './static/img/product/introduction/huolongguo.png');
INSERT INTO `sv_product` VALUES (4, '广西红心芭乐 番石榴水果 新鲜应季水果 产地直发包邮', 3, '广西 贵港', '2022-01-27 23:23:24', 'BF0004', '新鲜美味', 1, './static/img/product/fanshiliu.png', './static/img/product/introduction/fanshiliu.png');
INSERT INTO `sv_product` VALUES (5, '烟台红富士苹果 一级铂金大果 单果220g以上 新鲜水果', 6, '山东 烟台', '2022-01-27 23:28:43', 'BF0005', '新鲜美味', 1, './static/img/product/pingguo.png', './static/img/product/introduction/pingguo.png');
INSERT INTO `sv_product` VALUES (6, '国产黑土猪肉 农家生态 生鲜冷鲜肉 烧烤烤肉食材 黑土猪瘦腿肉', 16, '安徽 安庆', '2022-01-28 08:28:00', 'BF0006', '原生态，新鲜美味', 2, './static/img/product/zhurou.png', './static/img/product/introduction/zhurou.png');
INSERT INTO `sv_product` VALUES (7, '澳洲原切牛排 进口谷饲儿童生鲜牛肉 保乐肩-雪花牛排', 250, '澳洲', '2022-01-28 08:28:00', 'BF0007', '原生态，新鲜美味', 2, './static/img/product/niupai.png', './static/img/product/introduction/niupai.png');
INSERT INTO `sv_product` VALUES (8, '宁夏滩羊肉 精修小羔羊腿 烧烤食材 精修法式羊后腿', 50, '宁夏', '2022-01-28 08:28:00', 'BF0008', '原生态，新鲜美味', 2, './static/img/product/yangtui.png', './static/img/product/introduction/yangtui.png');
INSERT INTO `sv_product` VALUES (9, '松花蛋无铅工艺溏心皮蛋', 10, '江西 南昌', '2022-01-28 08:28:00', 'BF0009', '新鲜美味', 2, './static/img/product/pidan.png', './static/img/product/introduction/pidan.png');
INSERT INTO `sv_product` VALUES (10, '宁夏黄牛肉 生鲜国产牛里脊', 60, '宁夏', '2022-01-28 08:28:00', 'BF00010', '原生态，新鲜美味', 2, './static/img/product/niurou.png', './static/img/product/introduction/niurou.png');
INSERT INTO `sv_product` VALUES (11, '阳澄湖大闸蟹 鲜活生鲜螃蟹现货', 150, '江苏 苏州', '2022-01-28 08:28:00', 'BF00011', '原生态，新鲜美味', 3, './static/img/product/dazhaxie.png', './static/img/product/introduction/dazhaxie.png');
INSERT INTO `sv_product` VALUES (12, '三去开背深海鲈鱼（去鳞去鳃去内脏）', 30, '广东 佛山', '2022-01-28 08:28:00', 'BF00012', '原生态，新鲜美味', 3, './static/img/product/luyu.png', './static/img/product/introduction/luyu.png');
INSERT INTO `sv_product` VALUES (13, '鲜活海捕大号皮皮虾', 110, '广东 江门', '2022-01-28 08:28:00', 'BF00013', '新鲜美味', 3, './static/img/product/pipixia.png', './static/img/product/introduction/pipixia.png');
INSERT INTO `sv_product` VALUES (14, '乳山生蚝鲜活即食大牡蛎现捕海蛎子带壳生蚝肉贝类海鲜', 40, '广西 北海', '2022-01-28 08:28:00', 'BF00014', '原生态，新鲜美味', 3, './static/img/product/shenghao.png', './static/img/product/introduction/shenghao.png');
INSERT INTO `sv_product` VALUES (15, '鳕鱼', 30, '上海', '2022-01-28 08:28:00', 'BF00015', '原生态，新鲜美味', 3, './static/img/product/xunyu.png', './static/img/product/introduction/xunyu.png');
INSERT INTO `sv_product` VALUES (16, '饺子组合装早餐冷冻速食水饺王饺子', 30, '山东 青岛', '2022-01-28 08:28:00', 'BF00016', '新鲜美味', 4, './static/img/product/jiaozi.png', './static/img/product/introduction/jiaozi.png');
INSERT INTO `sv_product` VALUES (17, '湾仔码头 上汤小云吞 早餐 小馄饨 云吞面 火锅食材', 28, '广东 广州', '2022-01-28 08:28:00', 'BF00017', '新鲜美味', 4, './static/img/product/yuntun.png', './static/img/product/introduction/yuntun.png');
INSERT INTO `sv_product` VALUES (18, '钟薛高 有故事系列 冰淇淋雪糕', 120, '广东 深圳', '2022-01-28 08:28:00', 'BF00018', '新鲜美味', 4, './static/img/product/bingqilin.png', './static/img/product/introduction/bingqilin.png');
INSERT INTO `sv_product` VALUES (19, '老酸奶内蒙古低温风味酸牛奶', 60, '内蒙古 呼和浩特', '2022-01-28 08:28:00', 'BF00019', '新鲜美味', 4, './static/img/product/suannai.png', './static/img/product/introduction/suannai.png');
INSERT INTO `sv_product` VALUES (20, '可口可乐 Coca-Cola 汽水 碳酸饮料', 120, '上海', '2022-01-28 08:28:00', 'BF00020', '新鲜美味', 4, './static/img/product/kele.png', './static/img/product/introduction/kele.png');
INSERT INTO `sv_product` VALUES (21, '老妈厨房鸡蛋风味挂面', 20, '广东 广州', '2022-01-28 08:28:00', 'BF00021', '新鲜美味', 5, './static/img/product/miantiao.png', './static/img/product/introduction/miantiao.png');
INSERT INTO `sv_product` VALUES (22, '海天 酱油 生抽酱油 中华老字号', 18, '广东 广州', '2022-01-28 08:28:00', 'BF00022', '新鲜美味', 5, './static/img/product/jiangyou.png', './static/img/product/introduction/jiangyou.png');
INSERT INTO `sv_product` VALUES (23, '海天 醋 醇酿香醋 凉拌饺子食用醋 中华老字号', 18, '广东 广州', '2022-01-28 08:28:00', 'BF00023', '新鲜美味', 5, './static/img/product/cu.png', './static/img/product/introduction/cu.png');
INSERT INTO `sv_product` VALUES (24, '双汇王中王 袋装 速食香肠', 60, '河南 南阳', '2022-01-28 08:28:00', 'BF00024', '新鲜美味', 5, './static/img/product/huotuichang.png', './static/img/product/introduction/huotuichang.png');
INSERT INTO `sv_product` VALUES (25, '中盐 加碘精制盐 食盐 500g 中盐出品', 3.3, '上海', '2022-01-28 08:28:00', 'BF00025', '物美价廉', 5, './static/img/product/yan.png', './static/img/product/introduction/yan.png');

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
