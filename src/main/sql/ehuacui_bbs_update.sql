/**
*--- start---增加主键----v_1.0.1
*/

ALTER TABLE `tb_role_permission` ADD COLUMN `id` INT NOT NULL AUTO_INCREMENT FIRST,
 ADD PRIMARY KEY (`id`);

ALTER TABLE `tb_user_role` ADD COLUMN `id` INT NOT NULL AUTO_INCREMENT FIRST,
 ADD PRIMARY KEY (`id`);

/**
*--- end---增加主键----v_1.0.1
*/


/**
*--- start---修改字段名称----v_1.0.2
*/

ALTER TABLE `tb_notification`
CHANGE COLUMN `read` `is_read` TINYINT (1) NOT NULL COMMENT '是否已读：0默认 1已读' AFTER `id`;

ALTER TABLE `tb_topic`
CHANGE COLUMN `view` `view_count` TINYINT (1) NOT NULL COMMENT '1置顶 0默认' AFTER `last_reply_author`,
CHANGE COLUMN `top` `is_top` TINYINT (1) NOT NULL COMMENT '1置顶 0默认' AFTER `author`,
CHANGE COLUMN `good` `is_good` TINYINT (1) NOT NULL COMMENT '1精华 0默认' AFTER `is_top`;

/**
*--- end---修改字段名称----v_1.0.2
*/

