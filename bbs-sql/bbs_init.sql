/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50626
Source Host           : 127.0.0.1:3306
Source Database       : bbs

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-08-14 21:52:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_collect`;
CREATE TABLE `tb_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `in_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_collect
-- ----------------------------

-- ----------------------------
-- Table structure for tb_notification
-- ----------------------------
DROP TABLE IF EXISTS `tb_notification`;
CREATE TABLE `tb_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `read` tinyint(1) NOT NULL COMMENT '是否已读：0默认 1已读',
  `author` varchar(50) NOT NULL DEFAULT '' COMMENT '发起通知用户昵称',
  `target_author` varchar(50) NOT NULL COMMENT '要通知用户的昵称',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `action` varchar(255) NOT NULL DEFAULT '' COMMENT '通知动作',
  `tid` int(11) NOT NULL COMMENT '话题id',
  `content` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_notification
-- ----------------------------

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '权限名称',
  `url` varchar(255) DEFAULT NULL COMMENT '授权路径',
  `description` varchar(255) NOT NULL COMMENT '权限描述',
  `pid` int(11) NOT NULL COMMENT '父节点0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
INSERT INTO `tb_permission` VALUES ('55', 'section', '', '板块节点', '0');
INSERT INTO `tb_permission` VALUES ('56', 'system', '', '系统设置', '0');
INSERT INTO `tb_permission` VALUES ('57', 'topic', '', '话题节点', '0');
INSERT INTO `tb_permission` VALUES ('58', 'reply', '', '回复节点', '0');
INSERT INTO `tb_permission` VALUES ('59', 'system:users', '/manage/users', '用户列表', '56');
INSERT INTO `tb_permission` VALUES ('60', 'system:roles', '/manage/roles', '角色列表', '56');
INSERT INTO `tb_permission` VALUES ('61', 'system:permissions', '/manage/permissions', '权限列表', '56');
INSERT INTO `tb_permission` VALUES ('62', 'system:user:role', '/manage/userrole', '用户角色关联', '56');
INSERT INTO `tb_permission` VALUES ('63', 'system:role:permission', '/manage/rolepermission', '角色权限关联', '56');
INSERT INTO `tb_permission` VALUES ('64', 'system:add:permission', '/manage/addpermission', '添加权限', '56');
INSERT INTO `tb_permission` VALUES ('65', 'system:edit:permission', '/manage/editpermission', '编辑权限', '56');
INSERT INTO `tb_permission` VALUES ('66', 'system:add:role', '/manage/addrole', '添加角色', '56');
INSERT INTO `tb_permission` VALUES ('67', 'system:add:role', '/manage/addrole', '添加角色', '56');
INSERT INTO `tb_permission` VALUES ('68', 'system:delete:user', '/manage/deleteuser', '删除用户', '56');
INSERT INTO `tb_permission` VALUES ('69', 'system:delete:role', '/manage/deleterole', '删除角色', '56');
INSERT INTO `tb_permission` VALUES ('70', 'system:delete:permission', '/manage/deletepermission', '删除权限', '56');
INSERT INTO `tb_permission` VALUES ('71', 'topic:delete', '/topic/delete', '删除话题', '57');
INSERT INTO `tb_permission` VALUES ('73', 'reply:delete', '/reply/delete', '删除回复', '58');
INSERT INTO `tb_permission` VALUES ('74', 'reply:edit', '/reply/edit', '编辑回复', '58');
INSERT INTO `tb_permission` VALUES ('75', 'topic:edit', '/topic/edit', '话题编辑', '57');
INSERT INTO `tb_permission` VALUES ('76', 'topic:append:edit', '/topic/appendedit', '追加编辑', '57');
INSERT INTO `tb_permission` VALUES ('77', 'topic:top', '/topic/top', '话题置顶', '57');
INSERT INTO `tb_permission` VALUES ('78', 'topic:good', '/topic/good', '话题加精', '57');
INSERT INTO `tb_permission` VALUES ('79', 'system:clear:cache', '/clear', '删除所有缓存', '56');
INSERT INTO `tb_permission` VALUES ('80', 'system:user:block', '/manage/userblock', '禁用用户', '56');
INSERT INTO `tb_permission` VALUES ('81', 'section:list', '/section/list', '板块列表', '55');
INSERT INTO `tb_permission` VALUES ('82', 'section:change:show:status', '/section/changeshowstatus', '改变板块显示状态', '55');
INSERT INTO `tb_permission` VALUES ('83', 'section:delete', '/section/delete', '删除板块', '55');
INSERT INTO `tb_permission` VALUES ('84', 'section:add', '/section/add', '添加板块', '55');
INSERT INTO `tb_permission` VALUES ('85', 'section:edit', '/section/edit', '编辑板块', '55');
INSERT INTO `tb_permission` VALUES ('86', 'reply:list', '/reply/list', '回复列表', '58');
INSERT INTO `tb_permission` VALUES ('87', 'system:solr', '/solr', '索引所有话题(慎用)', '56');
INSERT INTO `tb_permission` VALUES ('88', 'system:delete:all:index', '/deleteallindex', '删除所有索引', '56');

-- ----------------------------
-- Table structure for tb_reply
-- ----------------------------
DROP TABLE IF EXISTS `tb_reply`;
CREATE TABLE `tb_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL COMMENT '话题id',
  `content` longtext NOT NULL COMMENT '回复内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `author` varchar(32) NOT NULL COMMENT '当前回复用户id',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除0 默认 1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_reply
-- ----------------------------

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', 'admin', '超级管理员');
INSERT INTO `tb_role` VALUES ('2', 'banzhu', '版主');
INSERT INTO `tb_role` VALUES ('3', 'user', '普通用户');

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  KEY `fk_role_permission` (`rid`),
  KEY `fk_permission_role` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
INSERT INTO `tb_role_permission` VALUES ('2', '71');
INSERT INTO `tb_role_permission` VALUES ('2', '75');
INSERT INTO `tb_role_permission` VALUES ('2', '76');
INSERT INTO `tb_role_permission` VALUES ('2', '77');
INSERT INTO `tb_role_permission` VALUES ('2', '78');
INSERT INTO `tb_role_permission` VALUES ('2', '73');
INSERT INTO `tb_role_permission` VALUES ('2', '74');
INSERT INTO `tb_role_permission` VALUES ('1', '59');
INSERT INTO `tb_role_permission` VALUES ('1', '60');
INSERT INTO `tb_role_permission` VALUES ('1', '61');
INSERT INTO `tb_role_permission` VALUES ('1', '62');
INSERT INTO `tb_role_permission` VALUES ('1', '63');
INSERT INTO `tb_role_permission` VALUES ('1', '64');
INSERT INTO `tb_role_permission` VALUES ('1', '65');
INSERT INTO `tb_role_permission` VALUES ('1', '66');
INSERT INTO `tb_role_permission` VALUES ('1', '67');
INSERT INTO `tb_role_permission` VALUES ('1', '68');
INSERT INTO `tb_role_permission` VALUES ('1', '69');
INSERT INTO `tb_role_permission` VALUES ('1', '70');
INSERT INTO `tb_role_permission` VALUES ('1', '79');
INSERT INTO `tb_role_permission` VALUES ('1', '87');
INSERT INTO `tb_role_permission` VALUES ('1', '88');
INSERT INTO `tb_role_permission` VALUES ('1', '89');
INSERT INTO `tb_role_permission` VALUES ('1', '71');
INSERT INTO `tb_role_permission` VALUES ('1', '75');
INSERT INTO `tb_role_permission` VALUES ('1', '76');
INSERT INTO `tb_role_permission` VALUES ('1', '77');
INSERT INTO `tb_role_permission` VALUES ('1', '78');
INSERT INTO `tb_role_permission` VALUES ('1', '73');
INSERT INTO `tb_role_permission` VALUES ('1', '74');
INSERT INTO `tb_role_permission` VALUES ('1', '86');
INSERT INTO `tb_role_permission` VALUES ('1', '81');
INSERT INTO `tb_role_permission` VALUES ('1', '82');
INSERT INTO `tb_role_permission` VALUES ('1', '83');
INSERT INTO `tb_role_permission` VALUES ('1', '84');
INSERT INTO `tb_role_permission` VALUES ('1', '85');

-- ----------------------------
-- Table structure for tb_section
-- ----------------------------
DROP TABLE IF EXISTS `tb_section`;
CREATE TABLE `tb_section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '板块名称',
  `tab` varchar(45) NOT NULL DEFAULT '' COMMENT '板块标签',
  `show_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否显示，0不显示1显示',
  `display_index` int(11) NOT NULL COMMENT '板块排序',
  `default_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认显示板块 0默认，1显示',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '模块父节点',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tabunique` (`tab`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_section
-- ----------------------------
INSERT INTO `tb_section` VALUES ('1', '问答', 'ask', '1', '4', '0', '0');
INSERT INTO `tb_section` VALUES ('2', '博客', 'blog', '1', '5', '0', '0');
INSERT INTO `tb_section` VALUES ('3', '资讯', 'news', '1', '2', '0', '0');
INSERT INTO `tb_section` VALUES ('4', '分享', 'share', '1', '3', '1', '0');

-- ----------------------------
-- Table structure for tb_topic
-- ----------------------------
DROP TABLE IF EXISTS `tb_topic`;
CREATE TABLE `tb_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tab` varchar(50) NOT NULL COMMENT '版块标识',
  `title` varchar(128) NOT NULL COMMENT '话题标题',
  `tag` varchar(255) DEFAULT NULL COMMENT '话题内容标签',
  `content` longtext COMMENT '话题内容',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_reply_time` datetime DEFAULT NULL COMMENT '最后回复话题时间，用于排序',
  `last_reply_author` varchar(50) DEFAULT '' COMMENT '最后回复话题的用户id',
  `view` int(11) NOT NULL COMMENT '浏览量',
  `author` varchar(50) NOT NULL COMMENT '话题作者id',
  `top` tinyint(1) NOT NULL COMMENT '1置顶 0默认',
  `good` tinyint(1) NOT NULL COMMENT '1精华 0默认',
  `show_status` tinyint(1) NOT NULL COMMENT '1显示0不显示',
  `reply_count` int(11) NOT NULL DEFAULT '0' COMMENT '回复数量',
  `is_delete` tinyint(1) NOT NULL COMMENT '1删除0默认',
  `tag_is_count` tinyint(1) DEFAULT '0' COMMENT '话题内容标签是否被统计过1是0否默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_topic
-- ----------------------------

-- ----------------------------
-- Table structure for tb_topic_append
-- ----------------------------
DROP TABLE IF EXISTS `tb_topic_append`;
CREATE TABLE `tb_topic_append` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `content` longtext NOT NULL,
  `in_time` datetime NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_topic_append
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `score` int(11) NOT NULL COMMENT '积分',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `url` varchar(255) DEFAULT NULL COMMENT '个人主页',
  `signature` varchar(1000) DEFAULT NULL COMMENT '个性签名',
  `third_id` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方账户id',
  `access_token` varchar(45) NOT NULL,
  `receive_msg` tinyint(1) NOT NULL COMMENT '邮箱是否接收社区消息',
  `in_time` datetime NOT NULL COMMENT '录入时间',
  `expire_time` datetime NOT NULL,
  `channel` varchar(50) NOT NULL,
  `is_block` tinyint(1) NOT NULL COMMENT '禁用0默认 1禁用',
  `third_access_token` varchar(50) DEFAULT NULL COMMENT '第三方登录获取的access_token',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NICKNAME_UNIQUE` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_user
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  KEY `fk_user_role` (`uid`),
  KEY `fk_role_user` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('1', '1');
