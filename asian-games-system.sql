CREATE DATABASES IF NOT EXISTS `asian-games-system`;
USE `asian-games-system`;

CREATE TABLE
IF
	NOT EXISTS player (
		id BIGINT PRIMARY KEY,
		`name` VARCHAR ( 30 ) NOT NULL,
		`sex` INT NOT NULL,
		`photo` VARCHAR ( 100 ),
		`country` VARCHAR ( 30 ) NOT NULL,
		`competition_name` VARCHAR(20) NOT NULL,
-- 		`type` varchar(20) NOT NULL,
-- 		`competition_category_id` INT NOT NULL,
		`email` VARCHAR ( 50 ) NOT NULL,
		`create_time` DATE NOT NULL,
		`is_deleted` INT NOT NULL DEFAULT 0 
	);
CREATE TABLE
IF
	NOT EXISTS judge (
		id BIGINT PRIMARY KEY,
		`name` VARCHAR ( 30 ) NOT NULL,
		`sex` INT NOT NULL,
		`photo` VARCHAR ( 100 ),
		`country` VARCHAR ( 30 ) NOT NULL,
-- 		`competition_name_id` INT NOT NULL,
-- 		`competition_category_id` INT NOT NULL,
		`competition_name` VARCHAR(20) NOT NULL,
-- 		`type` VARCHAR(30) not null,
		`email` VARCHAR ( 50 ) NOT NULL,
		`create_time` DATE NOT NULL,
		`is_deleted` INT NOT NULL DEFAULT 0
	);
CREATE TABLE
IF
	NOT EXISTS admin (
		id BIGINT PRIMARY KEY,
		`username` VARCHAR ( 30 ) NOT NULL,
		`sex` INT NOT NULL,
		`password` VARCHAR ( 30 ) NOT NULL,
		`status` INT DEFAULT 0,
		`email` VARCHAR ( 50 ) NOT NULL,
		`create_time` DATE NOT NULL,
		`login_time` DATE NOT NULL 
	);
CREATE TABLE
IF
	NOT EXISTS competition_category (
		id INT PRIMARY KEY auto_increment,
		`name` VARCHAR ( 30 ) NOT NULL,
		`principal` VARCHAR ( 30 ) NOT NULL,
		`create_time` DATE NOT NULL,
		`update_time` DATE NOT NULL,
		`is_deleted` INT NOT NULL DEFAULT 0
	);
CREATE TABLE
IF
	NOT EXISTS competition_info (
		id INT PRIMARY KEY auto_increment,
		`name` VARCHAR ( 30 ) NOT NULL,
		`info` TEXT ,
		`picture` varchar(100) ,
		`principal` VARCHAR ( 30 ) NOT NULL,
-- 		`competition_category_id` INT NOT NULL,
		`type` VARCHAR(20) not NULL,
		`create_time` DATE NOT NULL,
		`update_time` DATE NOT NULL,
	`is_deleted` INT NOT NULL DEFAULT 0
	);