/**
 *  Navicat MySQL Data Transfer
 *
 * @Author Xg
 * @Date 2016/9/28 17:58
 */
-- ----------------------------
-- Table structure for `act_id_group`
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_group
-- ----------------------------
INSERT INTO `act_id_group` VALUES ('PL', '1', '项目lead', 'assignment');
INSERT INTO `act_id_group` VALUES ('PM', '1', '项目经理', 'assignment');
INSERT INTO `act_id_group` VALUES ('DM', '1', '域经理', 'assignment');
INSERT INTO `act_id_group` VALUES ('USER', '1', '申请人', 'assignment');

-- ----------------------------
-- Table structure for `act_id_membership`
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------
INSERT INTO `act_id_membership` VALUES ('pl1', 'PL');
INSERT INTO `act_id_membership` VALUES ('pl2', 'PL');
INSERT INTO `act_id_membership` VALUES ('pm1', 'PM');
INSERT INTO `act_id_membership` VALUES ('pm2', 'PM');
INSERT INTO `act_id_membership` VALUES ('dm', 'DM');
INSERT INTO `act_id_membership` VALUES ('user1', 'USER');
INSERT INTO `act_id_membership` VALUES ('user2', 'USER');

-- ----------------------------
-- Table structure for `act_id_user`
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of act_id_user
-- ----------------------------
INSERT INTO `act_id_user` VALUES ('pl1', '1', '1', 'pl', 'pl1@gmail.com', '000000', '');
INSERT INTO `act_id_user` VALUES ('pl2', '1', '2', 'pl', 'pl2@gmail.com', '000000', '');
INSERT INTO `act_id_user` VALUES ('pm1', '1', '1', 'pm', 'pm1@gmail.com', '000000', '');
INSERT INTO `act_id_user` VALUES ('pm2', '1', '2', 'pm', 'pm2@gmail.com', '000000', '');
INSERT INTO `act_id_user` VALUES ('dm', '1', '1', 'dm', 'dm@gmail.com', '000000', '');
INSERT INTO `act_id_user` VALUES ('user1', '1', '1', 'user', 'user1@gmail.com', '000000', '');
INSERT INTO `act_id_user` VALUES ('user2', '1', '2', 'user', 'user2@gmail.com', '000000', '');
