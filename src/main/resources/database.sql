-- user
	-- id
	-- name
	-- password
	-- salt
	-- gender
	-- birth
	-- head_url
	-- vocation
	-- introduction
	-- register_date
SELECT * FROM USER;
CREATE TABLE `user`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(64) NOT NULL DEFAULT '',
    `password` VARCHAR(128) NOT NULL DEFAULT '',
    `salt` VARCHAR(32) NOT NULL DEFAULT '',
    `head_url` VARCHAR(256) NOT NULL DEFAULT '',
    gender TINYINT NOT NULL DEFAULT 0,
    birth DATE,
    vocation VARCHAR(100),
    introduction TEXT,
    register_date DATETIME
);
-- message
	-- id
	-- fromid
	-- toid
	-- content
	-- conversation_id
	-- create_date
	-- has_read
SELECT * FROM USER;	
CREATE TABLE `message` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `from_id` INT NULL,
    `to_id` INT NULL,
    `content` TEXT NULL,
    `created_date` DATETIME NULL,
    `has_read` INT NULL,
    `conversation_id` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `conversation_index` (`conversation_id` ASC),
    INDEX `created_date` (`created_date` ASC))
  ENGINE = INNODB
  DEFAULT CHARACTER SET = utf8;
SELECT
  `id`,
  `from_id`,
  `to_id`,
  `content`,
  `created_date`,
  `has_read`,
  `conversation_id`
FROM
  `ncublog`.`message`
LIMIT 0, 1000;

  SELECT * FROM message;
  SELECT conversation_id,COUNT(conversation_id) FROM message ORDER BY created_date DESC GROUP BY conversation_id;
  
  SELECT * FROM (SELECT * FROM message ORDER BY created_date DESC) AS java GROUP BY conversation_id;
  
  SELECT tmp.id AS id, from_id, to_id, content, created_date, has_read, conversation_id 
  FROM
	message,
	(SELECT conversation_id AS conver_id,MAX(created_date) AS max_date, COUNT(id) AS id FROM message  GROUP BY conversation_id ORDER BY max_date DESC) 
  AS 	tmp
  WHERE conversation_id=tmp.conver_id AND created_date=tmp.max_date  ORDER BY created_date DESC;
SELECT * FROM message WHERE conversation_id='1_9' ORDER BY created_date DESC;
SET sql_mode='ONLY_FULL_GROUP_BY';
select  count(id) from message group by conversation_id;
select a.id,a.content,a.`created_date`,a.`conversation_id` from message a left join message b on
 a.conversation_id = b.conversation_id and a.id = b.id and a.`created_date` < b.`created_date` 
 where b.created_date is null;
 select max(created_date), conversation_id from message	group by conversation_id;
-- login_ticket

CREATE TABLE `login_ticket` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `ticket` VARCHAR(45) NOT NULL,
    `expired` DATETIME NOT NULL,
    `status` INT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `ticket_UNIQUE` (`ticket` ASC)
    ) ENGINE=INNODB DEFAULT CHARSET=utf8;
    
-- blog
	-- id
	-- title
	-- content
	-- user_id
	-- create_date
	-- comment_count
CREATE TABLE `blog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NULL,
  `user_id` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `comment_count` INT NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `date_index` (`created_date` ASC));
-- comment
	-- id
	-- content
	-- user_id
	-- create_date
	-- entity_id
	-- entity_type
CREATE TABLE `comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `user_id` INT NOT NULL,
  `entity_id` INT NOT NULL,
  `entity_type` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `entity_index` (`entity_id` ASC, `entity_type` ASC)
  ) ENGINE=INNODB DEFAULT CHARSET=utf8;

	
	
INSERT INTO `ncublog`.`comment` (
  
  `content`,
  `user_id`,
  `entity_id`,
  `entity_type`,
  `created_date`,
  `status`
)
VALUES
  (
    'content',
    3,
    3 ,
    'entity_type',
    'created_date',
    'status'
  );

-- feed
CREATE TABLE `feed` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `created_date` DATETIME NULL,
    `user_id` INT NULL,
    `data` TINYTEXT NULL,
    `type` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_index` (`user_id` ASC))
  ENGINE = INNODB
  DEFAULT CHARACTER SET = utf8;
  
  SELECT * FROM feed WHERE id IN(1,3,4) ORDER BY created_date DESC LIMIT #{;
  
  UPDATE `user` SET register_date=NOW();
    UPDATE `user` SET birth=NOW();

  SELECT  id, NAME, PASSWORD, salt, gender, head_url,  birth, vocation, introduction, register_date FROM `user`;
  SELECT * FROM `user`;
select id,count(id) from 
TRUNCATE feed;

select @@session.sql_mode;