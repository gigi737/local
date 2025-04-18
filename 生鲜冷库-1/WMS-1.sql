/*
 Navicat Premium Data Transfer

 Source Server         : 定制
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : wms

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 09/12/2024 11:28:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appuser
-- ----------------------------
DROP TABLE IF EXISTS `appuser`;
CREATE TABLE `appuser`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `Email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `UserName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `Name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `RoleType` int(11) NULL DEFAULT NULL COMMENT '角色',
  `ImageUrls` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `Birth` datetime(0) NULL DEFAULT NULL COMMENT '出生年月',
  `PhoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appuser
-- ----------------------------
INSERT INTO `appuser` VALUES (1, '2024-11-30 12:48:15', 0, 'admin', NULL, 'admin', '管理员', 1, NULL, NULL, NULL);
INSERT INTO `appuser` VALUES (2, '2024-11-30 13:59:54', 1, 'test0001', '123456@qq.com', 'test0001', '王志恒', 2, 'http://localhost:7245/1732946393324/用户.png', '2024-11-05 00:00:00', '13000000001');
INSERT INTO `appuser` VALUES (3, '2024-12-09 10:44:57', 1, 'test002', '123123@qq.com', 'test006', '傅垣毓', 2, 'http://localhost:7245/1733712296630/用户信息.png', '2024-12-01 00:00:00', '13000000002');

-- ----------------------------
-- Table structure for cargo
-- ----------------------------
DROP TABLE IF EXISTS `cargo`;
CREATE TABLE `cargo`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `CustomerId` int(11) NULL DEFAULT NULL COMMENT '货主',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `SKU` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '条码',
  `Brand` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '品牌',
  `Specification` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '规格型号',
  `Unit` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单位',
  `MinStockAlert` double(18, 6) NULL DEFAULT NULL COMMENT '最低库存预警',
  `MaxStockLimit` double(18, 6) NULL DEFAULT NULL COMMENT '最高库存限制',
  `CargoTypeId` int(11) NULL DEFAULT NULL COMMENT '商品分类',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `CargoTypeId`(`CargoTypeId`) USING BTREE,
  INDEX `CustomerId`(`CustomerId`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  CONSTRAINT `cargo_ibfk_1` FOREIGN KEY (`CargoTypeId`) REFERENCES `cargotype` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `cargo_ibfk_2` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `cargo_ibfk_3` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cargo
-- ----------------------------
INSERT INTO `cargo` VALUES (1, '2024-01-15 09:30:00', 1, 1, 7, '进口车厘子', 'CHERRY-20240115', '智利', '果径 30mm 以上，色泽红润', '千克', 150.000000, 1500.000000, 31);
INSERT INTO `cargo` VALUES (2, '2024-02-20 14:45:00', 2, 1, 2, '新鲜草莓', 'STRAWBERRY-20240220', '丹东', '个头饱满，香甜多汁', '千克', 80.000000, 800.000000, 11);
INSERT INTO `cargo` VALUES (3, '2024-03-10 08:15:00', 3, 1, 3, '三文鱼', 'SALMON-20240310', '挪威', '冰鲜，肉质鲜嫩', '千克', 200.000000, 2000.000000, 4);
INSERT INTO `cargo` VALUES (4, '2024-04-05 11:20:00', 4, 1, 4, '鲜牛奶', 'MILK-20240405', '光明', '全脂，250ml*12 盒', '箱', 50.000000, 500.000000, 30);
INSERT INTO `cargo` VALUES (5, '2024-05-18 16:35:00', 5, 1, 5, '精品五花肉', 'PORK-20240518', '本地', '肥瘦相间，肉质新鲜', '千克', 60.000000, 600.000000, 22);

-- ----------------------------
-- Table structure for cargotype
-- ----------------------------
DROP TABLE IF EXISTS `cargotype`;
CREATE TABLE `cargotype`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品分类主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  CONSTRAINT `cargotype_ibfk_1` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cargotype
-- ----------------------------
INSERT INTO `cargotype` VALUES (2, '2024-01-15 09:30:00', 1, '水果', 1);
INSERT INTO `cargotype` VALUES (3, '2024-02-20 14:45:00', 2, '蔬菜', 1);
INSERT INTO `cargotype` VALUES (4, '2024-03-10 08:15:00', 3, '肉类', 1);
INSERT INTO `cargotype` VALUES (5, '2024-04-05 11:20:00', 4, '海鲜', 1);
INSERT INTO `cargotype` VALUES (6, '2024-05-18 16:35:00', 5, '奶制品', 1);
INSERT INTO `cargotype` VALUES (7, '2024-06-22 09:50:00', 6, '禽蛋', 1);
INSERT INTO `cargotype` VALUES (8, '2024-07-12 13:05:00', 7, '速冻食品', 1);
INSERT INTO `cargotype` VALUES (9, '2024-08-03 10:40:00', 8, '干货', 1);
INSERT INTO `cargotype` VALUES (10, '2024-09-19 15:25:00', 9, '粮油副食', 1);
INSERT INTO `cargotype` VALUES (11, '2024-10-08 09:10:00', 10, '生鲜礼盒', 1);
INSERT INTO `cargotype` VALUES (12, '2024-11-01 11:30:00', 1, '有机生鲜', 1);
INSERT INTO `cargotype` VALUES (13, '2024-11-15 14:00:00', 2, '进口生鲜', 1);
INSERT INTO `cargotype` VALUES (14, '2024-12-05 09:25:00', 3, '预制菜', 1);
INSERT INTO `cargotype` VALUES (15, '2024-01-20 10:55:00', 4, '菌类', 1);
INSERT INTO `cargotype` VALUES (16, '2024-02-14 12:40:00', 5, '豆制品', 1);
INSERT INTO `cargotype` VALUES (17, '2024-03-28 14:10:00', 6, '调味品', 1);
INSERT INTO `cargotype` VALUES (18, '2024-04-11 09:45:00', 7, '腌制食品', 1);
INSERT INTO `cargotype` VALUES (19, '2024-05-05 11:00:00', 8, '烘焙原料', 1);
INSERT INTO `cargotype` VALUES (20, '2024-06-30 13:30:00', 9, '生鲜零食', 1);
INSERT INTO `cargotype` VALUES (21, '2024-07-25 15:50:00', 10, '冰鲜产品', 1);
INSERT INTO `cargotype` VALUES (22, '2024-08-10 09:00:00', 1, '冷冻水果', 1);
INSERT INTO `cargotype` VALUES (23, '2024-09-15 10:30:00', 2, '冷冻蔬菜', 1);
INSERT INTO `cargotype` VALUES (24, '2024-10-20 11:45:00', 3, '冷冻肉类', 1);
INSERT INTO `cargotype` VALUES (25, '2024-11-25 13:15:00', 4, '冷冻海鲜', 1);
INSERT INTO `cargotype` VALUES (26, '2024-12-30 14:20:00', 5, '冷藏饮品', 1);
INSERT INTO `cargotype` VALUES (27, '2025-01-10 09:45:00', 6, '鲜花生鲜', 1);
INSERT INTO `cargotype` VALUES (28, '2025-02-15 11:00:00', 7, '特色生鲜', 1);
INSERT INTO `cargotype` VALUES (29, '2025-03-20 13:30:00', 8, '生鲜配料', 1);
INSERT INTO `cargotype` VALUES (30, '2025-04-25 15:00:00', 9, '水产制品', 1);
INSERT INTO `cargotype` VALUES (31, '2025-05-30 16:30:00', 10, '肉类制品', 1);
INSERT INTO `cargotype` VALUES (32, '2025-06-15 09:15:00', 1, '水果制品', 1);
INSERT INTO `cargotype` VALUES (33, '2025-07-20 10:45:00', 2, '蔬菜制品', 1);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '货主主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `OwnerName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '货主名称',
  `ContactPerson` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人',
  `Phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `Email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `Address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '货主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '2024-11-30 15:18:26', 1, '鑫源水果批发商行', '刘刚', '18646215125', 'liugang@xyfruit.com', '广东省广州市白云区增槎路江南果菜批发市场 X 区 12 号', 1);
INSERT INTO `customer` VALUES (2, '2024-01-15 09:30:00', 1, '远洋水产贸易有限公司', '陈海洋', '+86 10 1234 5678', 'chenhaiyang@oceanfish.com', '浙江省宁波市北仑区新碶街道明州路 188 号', 1);
INSERT INTO `customer` VALUES (3, '2024-02-20 14:45:00', 2, '佳鲜生鲜超市连锁', '王芳', '+86 21 8765 4321', 'wangfang@jxfreshmarket.com', '上海市闵行区都市路 5001 号仲盛世界商城 B1 层', 1);
INSERT INTO `customer` VALUES (4, '2024-03-10 08:15:00', 3, '食味轩餐饮集团', '张宇', '+86 20 5678 9012', 'zhangyu@tasteinn.com', '北京市朝阳区三里屯太古里南区 S4 - 30', 1);
INSERT INTO `customer` VALUES (5, '2024-04-05 11:20:00', 4, '草原盛景肉类加工厂', '赵建国', '+86 27 1234 5678', 'zhaojianguo@grasslandmeat.com', '内蒙古自治区锡林郭勒盟锡林浩特市宝力根苏木 23 号', 1);
INSERT INTO `customer` VALUES (6, '2024-05-18 16:35:00', 5, '绿鲜蔬菜配送中心', '孙丽', '+86 28 8765 4321', 'sunli@greenvegedelivery.com', '四川省成都市郫都区安德镇中国川菜产业化园区 3 栋', 1);
INSERT INTO `customer` VALUES (7, '2024-06-22 09:50:00', 6, '冰爽速冻食品有限公司', '周鹏', '+86 29 5678 9012', 'zhoupeng@icefrozenfood.com', '陕西省西安市经开区草滩生态产业园尚苑路 3699 号', 1);

-- ----------------------------
-- Table structure for inventorycheck
-- ----------------------------
DROP TABLE IF EXISTS `inventorycheck`;
CREATE TABLE `inventorycheck`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '盘点单主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `No` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单号',
  `CheckUserId` int(11) NULL DEFAULT NULL COMMENT '盘点人',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `CustomerId` int(11) NULL DEFAULT NULL COMMENT '货主',
  `InventoryCheckStatus` int(11) NULL DEFAULT NULL COMMENT '盘点状态',
  `IsExcuteInventoryChange` tinyint(1) NULL DEFAULT NULL COMMENT '是否执行库存调整',
  `BeginCheckTime` datetime(0) NULL DEFAULT NULL COMMENT '起始盘点时间',
  `EndCheckTime` datetime(0) NULL DEFAULT NULL COMMENT '截至盘点时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `CheckUserId`(`CheckUserId`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  INDEX `CustomerId`(`CustomerId`) USING BTREE,
  CONSTRAINT `inventorycheck_ibfk_1` FOREIGN KEY (`CheckUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventorycheck_ibfk_2` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventorycheck_ibfk_3` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '盘点单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventorycheck
-- ----------------------------
INSERT INTO `inventorycheck` VALUES (1, '2024-11-30 21:38:10', 1, 'P202411302138', 1, 1, 1, 3, 1, '2024-11-30 00:00:00', '2024-12-01 00:00:00');
INSERT INTO `inventorycheck` VALUES (2, '2024-12-09 11:03:04', 1, 'P202412-01103', 1, 1, 5, 3, 1, '2024-12-09 00:00:00', '2024-12-11 00:00:00');
INSERT INTO `inventorycheck` VALUES (3, '2024-12-09 11:05:46', 1, 'P202412091105', 1, 1, 5, 3, 1, '2024-12-09 00:00:00', '2024-12-10 00:00:00');
INSERT INTO `inventorycheck` VALUES (4, '2024-12-09 11:18:31', 1, 'P202412091118', 1, 1, 5, 3, 1, '2024-12-09 00:00:00', '2024-12-10 00:00:00');
INSERT INTO `inventorycheck` VALUES (5, '2025-01-15 09:20:00', 1, 'P202501150920', 1, 1, 1, 3, 1, '2025-01-15 00:00:00', '2025-01-16 00:00:00');
INSERT INTO `inventorycheck` VALUES (6, '2025-01-20 14:30:00', 1, 'P202501201430', 1, 1, 2, 3, 1, '2025-01-20 00:00:00', '2025-01-21 00:00:00');
INSERT INTO `inventorycheck` VALUES (7, '2025-02-05 11:45:00', 1, 'P202502051145', 1, 1, 3, 3, 1, '2025-02-05 00:00:00', '2025-02-06 00:00:00');
INSERT INTO `inventorycheck` VALUES (8, '2025-02-12 16:10:00', 1, 'P202502121610', 1, 1, 4, 3, 1, '2025-02-12 00:00:00', '2025-02-13 00:00:00');
INSERT INTO `inventorycheck` VALUES (9, '2025-03-01 08:35:00', 1, 'P202503010835', 1, 1, 5, 3, 1, '2025-03-01 00:00:00', '2025-03-02 00:00:00');
INSERT INTO `inventorycheck` VALUES (10, '2025-03-18 13:20:00', 1, 'P202503181320', 1, 1, 6, 3, 1, '2025-03-18 00:00:00', '2025-03-19 00:00:00');
INSERT INTO `inventorycheck` VALUES (11, '2025-04-05 10:50:00', 1, 'P202504051050', 1, 1, 7, 3, 1, '2025-04-05 00:00:00', '2025-04-06 00:00:00');

-- ----------------------------
-- Table structure for inventorycheckdet
-- ----------------------------
DROP TABLE IF EXISTS `inventorycheckdet`;
CREATE TABLE `inventorycheckdet`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '盘点明细主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `InventoryCheckId` int(11) NULL DEFAULT NULL COMMENT '盘点单',
  `WarehouseBinId` int(11) NULL DEFAULT NULL COMMENT '库位',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `CargoId` int(11) NULL DEFAULT NULL COMMENT '商品',
  `Qty` double(20, 5) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `CargoId`(`CargoId`) USING BTREE,
  INDEX `WarehouseBinId`(`WarehouseBinId`) USING BTREE,
  INDEX `InventoryCheckId`(`InventoryCheckId`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  CONSTRAINT `inventorycheckdet_ibfk_1` FOREIGN KEY (`CargoId`) REFERENCES `cargo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventorycheckdet_ibfk_2` FOREIGN KEY (`WarehouseBinId`) REFERENCES `warehousebin` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventorycheckdet_ibfk_3` FOREIGN KEY (`InventoryCheckId`) REFERENCES `inventorycheck` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventorycheckdet_ibfk_4` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '盘点明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventorycheckdet
-- ----------------------------
INSERT INTO `inventorycheckdet` VALUES (1, '2024-11-30 21:42:43', 1, 1, 1, 1, 1, 25.00000);
INSERT INTO `inventorycheckdet` VALUES (2, '2024-12-09 11:04:16', 1, 2, 1, 1, 5, 18.00000);
INSERT INTO `inventorycheckdet` VALUES (3, '2024-12-09 11:05:51', 1, 3, 1, 1, 5, 22.00000);
INSERT INTO `inventorycheckdet` VALUES (4, '2024-12-09 11:18:40', 1, 4, 1, 1, 5, 20.00000);
INSERT INTO `inventorycheckdet` VALUES (5, '2025-01-15 09:22:00', 1, 5, 1, 1, 1, 30.00000);
INSERT INTO `inventorycheckdet` VALUES (6, '2025-01-20 14:32:00', 1, 6, 1, 1, 2, 15.00000);
INSERT INTO `inventorycheckdet` VALUES (7, '2025-02-05 11:47:00', 1, 7, 1, 1, 3, 35.00000);
INSERT INTO `inventorycheckdet` VALUES (8, '2025-02-12 16:12:00', 1, 8, 1, 1, 4, 12.00000);
INSERT INTO `inventorycheckdet` VALUES (9, '2025-03-01 08:37:00', 1, 9, 1, 1, 5, 26.00000);
INSERT INTO `inventorycheckdet` VALUES (10, '2025-03-18 13:22:00', 1, 10, 1, 1, 6, 38.00000);
INSERT INTO `inventorycheckdet` VALUES (11, '2025-04-05 10:52:00', 1, 11, 1, 1, 7, 22.00000);

-- ----------------------------
-- Table structure for inventoryrecord
-- ----------------------------
DROP TABLE IF EXISTS `inventoryrecord`;
CREATE TABLE `inventoryrecord`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '库存记录主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `CustomerId` int(11) NULL DEFAULT NULL COMMENT '货主',
  `CargoId` int(11) NULL DEFAULT NULL COMMENT '商品',
  `WarehouseBinId` int(11) NULL DEFAULT NULL COMMENT '库位',
  `StockQty` double(20, 5) NULL DEFAULT NULL COMMENT '库存数量',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseBinId`(`WarehouseBinId`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  INDEX `CustomerId`(`CustomerId`) USING BTREE,
  INDEX `CargoId`(`CargoId`) USING BTREE,
  CONSTRAINT `inventoryrecord_ibfk_1` FOREIGN KEY (`WarehouseBinId`) REFERENCES `warehousebin` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventoryrecord_ibfk_2` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventoryrecord_ibfk_3` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventoryrecord_ibfk_4` FOREIGN KEY (`CargoId`) REFERENCES `cargo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '库存记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventoryrecord
-- ----------------------------
INSERT INTO `inventoryrecord` VALUES (1, '2024-12-05 14:20:00', 1, 1, 1, 1, 1, 150.00000);
INSERT INTO `inventoryrecord` VALUES (2, '2024-12-15 16:15:00', 1, 1, 2, 2, 1, 120.00000);
INSERT INTO `inventoryrecord` VALUES (3, '2025-01-08 12:30:00', 1, 1, 3, 3, 1, 220.00000);
INSERT INTO `inventoryrecord` VALUES (4, '2025-01-22 14:40:00', 1, 1, 4, 4, 1, 100.00000);
INSERT INTO `inventoryrecord` VALUES (5, '2025-02-08 13:30:00', 1, 1, 5, 5, 1, 190.00000);


-- ----------------------------
-- Table structure for inventoryruningrecord
-- ----------------------------
DROP TABLE IF EXISTS `inventoryruningrecord`;
CREATE TABLE `inventoryruningrecord`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '库存流水记录主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `CargoId` int(11) NULL DEFAULT NULL COMMENT '商品',
  `CustomerId` int(11) NULL DEFAULT NULL COMMENT '货主',
  `Type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `RelativeNo` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关联单号',
  `Qty` double(20, 5) NULL DEFAULT NULL COMMENT '变动数量',
  `WarehouseBinId` int(11) NULL DEFAULT NULL COMMENT '库位',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  INDEX `CargoId`(`CargoId`) USING BTREE,
  INDEX `CustomerId`(`CustomerId`) USING BTREE,
  CONSTRAINT `inventoryruningrecord_ibfk_1` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventoryruningrecord_ibfk_2` FOREIGN KEY (`CargoId`) REFERENCES `cargo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `inventoryruningrecord_ibfk_3` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '库存流水记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventoryruningrecord
-- ----------------------------
INSERT INTO `inventoryruningrecord` VALUES (1, '2024-11-30 16:48:11', 1, 1, 1, 1, '入库完成', 'IN202411301648', 200.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (2, '2024-12-05 14:20:00', 1, 1, 1, 1, '出库完成', 'OUT202412051420', -50.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (3, '2024-12-10 10:30:00', 1, 1, 2, 2, '入库完成', 'IN202412101030', 150.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (4, '2024-12-15 16:15:00', 1, 1, 2, 2, '出库完成', 'OUT202412151615', -30.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (5, '2025-01-02 09:45:00', 1, 1, 3, 3, '入库完成', 'IN202501020945', 300.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (6, '2025-01-08 12:30:00', 1, 1, 3, 3, '出库完成', 'OUT202501081230', -80.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (7, '2025-01-15 11:10:00', 1, 1, 4, 4, '入库完成', 'IN202501151110', 120.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (8, '2025-01-22 14:40:00', 1, 1, 4, 4, '出库完成', 'OUT202501221440', -20.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (9, '2025-02-01 08:20:00', 1, 1, 5, 5, '入库完成', 'IN202502010820', 250.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (10, '2025-02-08 13:30:00', 1, 1, 5, 5, '出库完成', 'OUT202502081330', -60.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (11, '2025-02-15 10:00:00', 1, 1, 3, 6, '入库完成', 'IN202502151000', 400.00000, 1);
INSERT INTO `inventoryruningrecord` VALUES (12, '2025-02-22 15:10:00', 1, 1, 4, 6, '出库完成', 'OUT202502221510', -100.00000, 1);

-- ----------------------------
-- Table structure for stockin
-- ----------------------------
DROP TABLE IF EXISTS `stockin`;
CREATE TABLE `stockin`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '入库单主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `No` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '入库单号',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `CustomerId` int(11) NULL DEFAULT NULL COMMENT '货主',
  `StockInType` int(11) NULL DEFAULT NULL COMMENT '入库类型枚举',
  `StockInStatus` int(11) NULL DEFAULT NULL COMMENT '入库状态枚举',
  `InTime` datetime(0) NULL DEFAULT NULL COMMENT '入库时间',
  `Remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `PlanInTime` datetime(0) NULL DEFAULT NULL COMMENT '计划入库时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  INDEX `CustomerId`(`CustomerId`) USING BTREE,
  CONSTRAINT `stockin_ibfk_1` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockin_ibfk_2` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 220 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '入库单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stockin
-- ----------------------------
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-11-30 16:10:40', 2, 'IN202411301610', 1, 1, 1, 1, '2024-11-30 16:48:11', '正常入库', '2024-11-30 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-10 10:30:00', 2, 'IN202412101030', 1, 2, 1, 1, '2024-12-10 10:30:00', '正常入库', '2024-12-10 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-02 09:45:00', 3, 'IN202501020945', 1, 3, 1, 1, '2025-01-02 09:45:00', '正常入库', '2025-01-02 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-15 11:10:00', 4, 'IN202501151110', 1, 4, 1, 1, '2025-01-15 11:10:00', '正常入库', '2025-01-15 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-02-01 08:20:00', 5, 'IN202502010820', 1, 5, 1, 1, '2025-02-01 08:20:00', '正常入库', '2025-02-01 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-02-15 10:00:00', 6, 'IN202502151000', 1, 6, 1, 1, '2025-02-15 10:00:00', '正常入库', '2025-02-15 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-09 11:00:35', 1, 'IN2024120911', 1, 5, 2, 1, '2024-12-09 11:01:09', '测试入库', '2024-12-09 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-11-25 14:30:00', 2, 'IN202411251430', 1, 1, 1, 1, '2024-11-25 14:45:00', '正常入库', '2024-11-25 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-03 10:15:00', 2, 'IN202412031015', 1, 2, 1, 1, '2024-12-03 10:25:00', '正常入库', '2024-12-03 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-18 16:20:00', 3, 'IN202412181620', 1, 3, 1, 1, '2024-12-18 16:30:00', '正常入库', '2024-12-18 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-09 09:30:00', 4, 'IN202501090930', 1, 4, 1, 1, '2025-01-09 09:40:00', '正常入库', '2025-01-09 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-20 11:45:00', 5, 'IN202501201145', 1, 5, 1, 1, '2025-01-20 11:55:00', '正常入库', '2025-01-20 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-02-05 13:10:00', 6, 'IN202502051310', 1, 6, 1, 1, '2025-02-05 13:20:00', '正常入库', '2025-02-05 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-11-28 15:40:00', 2, 'IN202411281540', 1, 1, 1, 1, '2024-11-28 15:50:00', '正常入库', '2024-11-28 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-06 12:20:00', 2, 'IN202412061220', 1, 2, 1, 1, '2024-12-06 12:30:00', '正常入库', '2024-12-06 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-22 14:35:00', 3, 'IN202412221435', 1, 3, 1, 1, '2024-12-22 14:45:00', '正常入库', '2024-12-22 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-12 10:05:00', 4, 'IN202501121005', 1, 4, 1, 1, '2025-01-12 10:15:00', '正常入库', '2025-01-12 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-23 12:50:00', 5, 'IN202501231250', 1, 5, 1, 1, '2025-01-23 13:00:00', '正常入库', '2025-01-23 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-02-08 14:20:00', 6, 'IN202502081420', 1, 6, 1, 1, '2025-02-08 14:30:00', '正常入库', '2025-02-08 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-01 11:30:00', 2, 'IN202412011130', 1, 1, 1, 1, '2024-12-01 11:40:00', '正常入库', '2024-12-01 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-09 13:45:00', 2, 'IN202412091345', 1, 2, 1, 1, '2024-12-09 13:55:00', '正常入库', '2024-12-09 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-25 15:20:00', 3, 'IN202412251520', 1, 3, 1, 1, '2024-12-25 15:30:00', '正常入库', '2024-12-25 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-15 09:50:00', 4, 'IN202501150950', 1, 4, 1, 1, '2025-01-15 10:00:00', '正常入库', '2025-01-15 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-01-26 11:10:00', 5, 'IN202501261110', 1, 5, 1, 1, '2025-01-26 11:20:00', '正常入库', '2025-01-26 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2025-02-11 13:35:00', 6, 'IN202502111335', 1, 6, 1, 1, '2025-02-11 13:45:00', '正常入库', '2025-02-11 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-04 12:40:00', 2, 'IN202412041240', 1, 1, 1, 1, '2024-12-04 12:50:00', '正常入库', '2024-12-04 00:00:00');
INSERT INTO `stockin` (`CreationTime`, `CreatorId`, `No`, `WarehouseId`, `CustomerId`, `StockInType`, `StockInStatus`, `InTime`, `Remark`, `PlanInTime`)
VALUES ('2024-12-12 14:05:00', 2, 'IN202412121405', 1, 2, 1, 1, '2024-12-12 14:15:00', '正常入库', '2024-12-12 00:00:00');
-- ----------------------------
-- Table structure for stockindet
-- ----------------------------
DROP TABLE IF EXISTS `stockindet`;
CREATE TABLE `stockindet`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '入库明细主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `StockInId` int(11) NULL DEFAULT NULL COMMENT '入库单',
  `WarehouseBinId` int(11) NULL DEFAULT NULL COMMENT '库位',
  `CargoId` int(11) NULL DEFAULT NULL COMMENT '商品',
  `Qty` double(20, 5) NULL DEFAULT NULL COMMENT '数量',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  INDEX `StockInId`(`StockInId`) USING BTREE,
  INDEX `WarehouseBinId`(`WarehouseBinId`) USING BTREE,
  INDEX `CargoId`(`CargoId`) USING BTREE,
  CONSTRAINT `stockindet_ibfk_1` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockindet_ibfk_2` FOREIGN KEY (`StockInId`) REFERENCES `stockin` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockindet_ibfk_3` FOREIGN KEY (`WarehouseBinId`) REFERENCES `warehousebin` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockindet_ibfk_4` FOREIGN KEY (`CargoId`) REFERENCES `cargo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '入库明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stockindet
-- ----------------------------
INSERT INTO `stockindet` (`CreationTime`, `CreatorId`, `StockInId`, `WarehouseBinId`, `CargoId`, `Qty`, `WarehouseId`)
VALUES ('2024-12-10 14:20:00', 2, 8, 2, 1, 20.00000, 1);
INSERT INTO `stockindet` (`CreationTime`, `CreatorId`, `StockInId`, `WarehouseBinId`, `CargoId`, `Qty`, `WarehouseId`)
VALUES ('2024-12-12 10:30:00', 3, 9, 3, 2, 12.50000, 1);
INSERT INTO `stockindet` (`CreationTime`, `CreatorId`, `StockInId`, `WarehouseBinId`, `CargoId`, `Qty`, `WarehouseId`)
VALUES ('2024-12-15 15:45:00', 2, 10, 4, 3, 35.00000, 1);
INSERT INTO `stockindet` (`CreationTime`, `CreatorId`, `StockInId`, `WarehouseBinId`, `CargoId`, `Qty`, `WarehouseId`)
VALUES ('2024-12-18 11:15:00', 4, 11, 5, 4, 18.00000, 1);
INSERT INTO `stockindet` (`CreationTime`, `CreatorId`, `StockInId`, `WarehouseBinId`, `CargoId`, `Qty`, `WarehouseId`)
VALUES ('2024-12-20 09:20:00', 5, 12, 6, 5, 22.00000, 1);
INSERT INTO `stockindet` (`CreationTime`, `CreatorId`, `StockInId`, `WarehouseBinId`, `CargoId`, `Qty`, `WarehouseId`)
VALUES ('2024-12-22 13:30:00', 6, 13, 7, 6, 45.00000, 1);
-- ----------------------------
-- Table structure for stockout
-- ----------------------------
DROP TABLE IF EXISTS `stockout`;
CREATE TABLE `stockout`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '出库单主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `No` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单号',
  `CustomerId` int(11) NULL DEFAULT NULL COMMENT '货主',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `OutTime` datetime(0) NULL DEFAULT NULL COMMENT '出库时间',
  `PlanOutTime` datetime(0) NULL DEFAULT NULL COMMENT '计划出库时间',
  `Remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `StockOutType` int(11) NULL DEFAULT NULL COMMENT '出库类型枚举',
  `StockOutStatus` int(11) NULL DEFAULT NULL COMMENT '出库状态枚举',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  INDEX `CustomerId`(`CustomerId`) USING BTREE,
  CONSTRAINT `stockout_ibfk_1` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockout_ibfk_2` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '出库单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stockout
-- ----------------------------
INSERT INTO `stockout` (`CreationTime`, `CreatorId`, `No`, `CustomerId`, `WarehouseId`, `OutTime`, `PlanOutTime`, `Remark`, `StockOutType`, `StockOutStatus`)
VALUES ('2025-01-05 10:30:00', 2, 'OUT202501051030', 1, 1, '2025-01-05 11:00:00', '2025-01-05 10:00:00', '销售车厘子给零售商', 1, 5);
INSERT INTO `stockout` (`CreationTime`, `CreatorId`, `No`, `CustomerId`, `WarehouseId`, `OutTime`, `PlanOutTime`, `Remark`, `StockOutType`, `StockOutStatus`)
VALUES ('2025-01-10 14:15:00', 3, 'OUT202501101415', 2, 1, NULL, '2025-01-12 00:00:00', '客户退货三文鱼', 2, 1);
INSERT INTO `stockout` (`CreationTime`, `CreatorId`, `No`, `CustomerId`, `WarehouseId`, `OutTime`, `PlanOutTime`, `Remark`, `StockOutType`, `StockOutStatus`)
VALUES ('2025-01-15 16:20:00', 2, 'OUT202501151620', 3, 1, '2025-01-15 17:00:00', '2025-01-15 16:00:00', '部分草莓损坏损耗', 3, 5);
INSERT INTO `stockout` (`CreationTime`, `CreatorId`, `No`, `CustomerId`, `WarehouseId`, `OutTime`, `PlanOutTime`, `Remark`, `StockOutType`, `StockOutStatus`)
VALUES ('2025-01-20 09:45:00', 4, 'OUT202501200945', 4, 1, '2025-01-20 10:15:00', '2025-01-20 09:00:00', '销售精品五花肉给餐厅', 1, 5);
INSERT INTO `stockout` (`CreationTime`, `CreatorId`, `No`, `CustomerId`, `WarehouseId`, `OutTime`, `PlanOutTime`, `Remark`, `StockOutType`, `StockOutStatus`)
VALUES ('2025-01-25 11:30:00', 5, 'OUT202501251130', 5, 1, '2025-01-25 12:00:00', '2025-01-25 11:00:00', '速冻牛排包装破损损耗', 3, 5);
INSERT INTO `stockout` (`CreationTime`, `CreatorId`, `No`, `CustomerId`, `WarehouseId`, `OutTime`, `PlanOutTime`, `Remark`, `StockOutType`, `StockOutStatus`)
VALUES ('2025-02-01 13:15:00', 6, 'OUT202502011315', 6, 1, '2025-02-01 13:45:00', '2025-02-01 13:00:00', '销售菠菜给超市', 1, 5);
-- ----------------------------
-- Table structure for stockoutdet
-- ----------------------------
DROP TABLE IF EXISTS `stockoutdet`;
CREATE TABLE `stockoutdet`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '出库明细主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `StockOutId` int(11) NULL DEFAULT NULL COMMENT '出库单',
  `CargoId` int(11) NULL DEFAULT NULL COMMENT '商品',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  `Qty` double(20, 5) NULL DEFAULT NULL COMMENT '数量',
  `WarehouseBinId` int(11) NULL DEFAULT NULL COMMENT '库位',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `CargoId`(`CargoId`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  INDEX `WarehouseBinId`(`WarehouseBinId`) USING BTREE,
  INDEX `StockOutId`(`StockOutId`) USING BTREE,
  CONSTRAINT `stockoutdet_ibfk_1` FOREIGN KEY (`CargoId`) REFERENCES `cargo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockoutdet_ibfk_2` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockoutdet_ibfk_3` FOREIGN KEY (`WarehouseBinId`) REFERENCES `warehousebin` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `stockoutdet_ibfk_4` FOREIGN KEY (`StockOutId`) REFERENCES `stockout` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '出库明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stockoutdet
-- ----------------------------
INSERT INTO `stockoutdet` (`CreationTime`, `CreatorId`, `StockOutId`, `CargoId`, `WarehouseId`, `Qty`, `WarehouseBinId`)
VALUES ('2024-12-10 15:30:00', 2, 6, 1, 1, 10.00000, 2);
INSERT INTO `stockoutdet` (`CreationTime`, `CreatorId`, `StockOutId`, `CargoId`, `WarehouseId`, `Qty`, `WarehouseBinId`)
VALUES ('2024-12-12 11:45:00', 3, 7, 2, 1, 8.00000, 3);
INSERT INTO `stockoutdet` (`CreationTime`, `CreatorId`, `StockOutId`, `CargoId`, `WarehouseId`, `Qty`, `WarehouseBinId`)
VALUES ('2024-12-15 16:50:00', 2, 8, 3, 1, 15.00000, 4);
INSERT INTO `stockoutdet` (`CreationTime`, `CreatorId`, `StockOutId`, `CargoId`, `WarehouseId`, `Qty`, `WarehouseBinId`)
VALUES ('2024-12-18 12:20:00', 4, 9, 4, 1, 20.00000, 5);
INSERT INTO `stockoutdet` (`CreationTime`, `CreatorId`, `StockOutId`, `CargoId`, `WarehouseId`, `Qty`, `WarehouseBinId`)
VALUES ('2024-12-20 10:10:00', 5, 10, 5, 1, 12.00000, 6);
INSERT INTO `stockoutdet` (`CreationTime`, `CreatorId`, `StockOutId`, `CargoId`, `WarehouseId`, `Qty`, `WarehouseBinId`)
VALUES ('2024-12-22 14:30:00', 6, 11, 6, 1, 25.00000, 7);
-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '冷库主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '冷库名称',
  `Address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `City` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `Country` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '国家',
  `ContactPerson` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人',
  `Area` double(20, 5) NULL DEFAULT NULL COMMENT '面积',
  `Type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '冷库类型',
  `EnvironmentalConditions` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '环境条件',
  `Phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '冷库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES (1,'2024-05-16 01:30:00', 1, '北京冷库一号', '北京市朝阳区XX路1号', '北京', '中国', '张三', 5000.00000, '大型库', '偏低', '13800138001');
INSERT INTO `warehouse` VALUES (2,'2024-02-15 09:20:00', 2, '上海冷库二号', '上海市浦东新区XX路2号', '上海', '中国', '李四', 7500.00000, '中型库', '正常', '13800138002');
INSERT INTO `warehouse` VALUES (3,'2024-03-15 07:16:00', 3, '广州冷库三号', '广州市天河区XX路3号', '广州', '中国', '王五', 3000.00000, '大型库', '正常', '13800138003');
INSERT INTO `warehouse` VALUES (4,'2025-01-15 09:30:00', 4, '深圳冷库四号', '深圳市南山区XX路4号', '深圳', '中国', '赵六', 4500.00000, '中型库', '正常', '13800138004');
INSERT INTO `warehouse` VALUES (5,'2024-01-25 09:22:00', 5, '成都冷库五号', '成都市武侯区XX路5号', '成都', '中国', '孙七', 6000.00000, '大型库', '正常', '13800138005');
INSERT INTO `warehouse` VALUES (6,'2024-05-25 08:33:00', 6, '杭州冷库六号', '杭州市西湖区XX路6号', '杭州', '中国', '周八', 2500.00000, '中型库', '正常', '13800138006');
INSERT INTO `warehouse` VALUES (7,'2024-02-10 06:46:00', 7, '南京冷库七号', '南京市玄武区XX路7号', '南京', '中国', '吴九', 4000.00000, '大型库', '正常', '13800138007');
INSERT INTO `warehouse` VALUES (8,'2024-01-28 05:49:00', 8, '武汉冷库八号', '武汉市江汉区XX路8号', '武汉', '中国', '郑十', 5500.00000, '超大型库', '正常', '13800138008');
INSERT INTO `warehouse` VALUES (9,'2024-07-19 10:23:00', 9, '西安冷库九号', '西安市雁塔区XX路9号', '西安', '中国', '王十一', 3500.00000, '大型库', '正常', '13800138009');
INSERT INTO `warehouse` VALUES (10,'2024-11-23 01:32:00', 10, '重庆冷库十号', '重庆市渝中区XX路10号', '重庆', '中国', '李十二', 7000.00000, '中型库', '正常', '13800138010');

-- ----------------------------
-- Table structure for warehousebin
-- ----------------------------
DROP TABLE IF EXISTS `warehousebin`;
CREATE TABLE `warehousebin`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '库位主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '所属冷库',
  `BinCode` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '库位编码',
  `Type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `Level` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '层',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  CONSTRAINT `warehousebin_ibfk_1` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '库位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehousebin
-- ----------------------------
INSERT INTO `warehousebin` VALUES (1, '2024-11-30 14:24:52', 2, 1, 'A1-01-03', '存储库位', '1');
INSERT INTO `warehousebin` VALUES (2, '2024-01-15 09:30:00', 1, 1, 'A1-01-01', '存储库位', '1');
INSERT INTO `warehousebin` VALUES (3, '2024-02-20 14:45:00', 2, 1, 'A1-01-02', '存储库位', '2');
INSERT INTO `warehousebin` VALUES (4, '2024-03-10 08:15:00', 3, 1, 'A1-02-01', '存储库位', '1');
INSERT INTO `warehousebin` VALUES (5, '2024-04-05 11:20:00', 4, 1, 'A1-02-02', '存储库位', '2');
INSERT INTO `warehousebin` VALUES (6, '2024-05-18 16:35:00', 5, 1, 'B1-01-01', '拣货库位', '1');
INSERT INTO `warehousebin` VALUES (7, '2024-06-22 09:50:00', 6, 1, 'B1-01-02', '拣货库位', '2');
INSERT INTO `warehousebin` VALUES (8, '2024-07-12 13:05:00', 7, 1, 'B1-02-01', '拣货库位', '1');
INSERT INTO `warehousebin` VALUES (9, '2024-08-03 10:40:00', 8, 1, 'B1-02-02', '拣货库位', '2');
INSERT INTO `warehousebin` VALUES (10, '2024-09-19 15:25:00', 9, 1, 'C1-01-01', '存储库位', '1');
INSERT INTO `warehousebin` VALUES (11, '2024-10-08 09:10:00', 10, 1, 'C1-01-02', '存储库位', '2');

-- ----------------------------
-- Table structure for warehouserelativeuser
-- ----------------------------
DROP TABLE IF EXISTS `warehouserelativeuser`;
CREATE TABLE `warehouserelativeuser`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '冷库用户主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `UserId` int(11) NULL DEFAULT NULL COMMENT '用户',
  `WarehouseId` int(11) NULL DEFAULT NULL COMMENT '冷库',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `UserId`(`UserId`) USING BTREE,
  INDEX `WarehouseId`(`WarehouseId`) USING BTREE,
  CONSTRAINT `warehouserelativeuser_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `warehouserelativeuser_ibfk_2` FOREIGN KEY (`WarehouseId`) REFERENCES `warehouse` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '冷库用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehouserelativeuser
-- ----------------------------
INSERT INTO `warehouserelativeuser` VALUES (1, '2024-11-30 14:12:02', 2, 2, 5);
INSERT INTO `warehouserelativeuser` VALUES (2, '2024-11-30 14:12:03', 2, 1, 1);
INSERT INTO `warehouserelativeuser` VALUES (3, '2024-11-30 14:14:07', 2, 1, 8);
INSERT INTO `warehouserelativeuser` VALUES (4, '2024-11-30 14:14:12', 2, 1, 9);

SET FOREIGN_KEY_CHECKS = 1;
