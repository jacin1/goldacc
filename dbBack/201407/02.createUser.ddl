CREATE TABLE `t_user` (
	`userId` VARCHAR(20) NOT NULL,
	`roleId` VARCHAR(20) NOT NULL,
	`userName` VARCHAR(50) NOT NULL,
	`enName` VARCHAR(50)  DEFAULT NULL,
	`createTime` varchar(25) default null
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
