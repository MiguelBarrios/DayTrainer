-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema dailytrainerdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `dailytrainerdb` ;

-- -----------------------------------------------------
-- Schema dailytrainerdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dailytrainerdb` DEFAULT CHARACTER SET utf8 ;
USE `dailytrainerdb` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  `role` VARCHAR(45) NULL,
  `email` VARCHAR(125) NULL,
  `profile_picture` VARCHAR(1000) NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `biography` TEXT NULL,
  `created_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `order_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order_type` ;

CREATE TABLE IF NOT EXISTS `order_type` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `stock` ;

CREATE TABLE IF NOT EXISTS `stock` (
  `name` VARCHAR(45) NULL,
  `symbol` VARCHAR(10) NOT NULL,
  `exchange_name` VARCHAR(45) NULL,
  PRIMARY KEY (`symbol`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trade` ;

CREATE TABLE IF NOT EXISTS `trade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `price_per_share` DECIMAL(10,2) NULL,
  `quantity` INT NULL,
  `created_at` DATETIME NULL,
  `buy` TINYINT NOT NULL,
  `completion_date` DATETIME NULL,
  `user_id` INT NOT NULL,
  `order_type_id` INT NOT NULL,
  `notes` VARCHAR(200) NULL,
  `stock_symbol` VARCHAR(10) NOT NULL,
  `strike_price` DECIMAL(5,2) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Trade_user_idx` (`user_id` ASC),
  INDEX `fk_trade_order_types1_idx` (`order_type_id` ASC),
  INDEX `fk_trade_stock1_idx` (`stock_symbol` ASC),
  CONSTRAINT `fk_Trade_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_order_types1`
    FOREIGN KEY (`order_type_id`)
    REFERENCES `order_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_stock1`
    FOREIGN KEY (`stock_symbol`)
    REFERENCES `stock` (`symbol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `message` ;

CREATE TABLE IF NOT EXISTS `message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NULL,
  `read` TINYINT NOT NULL DEFAULT 0,
  `sender_id` INT NOT NULL,
  `recipent_id` INT NOT NULL,
  `reply_to_id` INT NULL,
  `created_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user1_idx` (`sender_id` ASC),
  INDEX `fk_message_user2_idx` (`recipent_id` ASC),
  INDEX `fk_message_message1_idx` (`reply_to_id` ASC),
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`sender_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_user2`
    FOREIGN KEY (`recipent_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_message1`
    FOREIGN KEY (`reply_to_id`)
    REFERENCES `message` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `account` ;

CREATE TABLE IF NOT EXISTS `account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `balance` DECIMAL(15,2) NULL,
  `margin_enable` TINYINT NOT NULL DEFAULT 0,
  `margin_amount` DECIMAL(15,2) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_account_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_account_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comment` ;

CREATE TABLE IF NOT EXISTS `comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NULL,
  `trade_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `created_at` DATE NULL,
  `reply_to_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_trade1_idx` (`trade_id` ASC),
  INDEX `fk_comment_user1_idx` (`user_id` ASC),
  INDEX `fk_comment_comment1_idx` (`reply_to_id` ASC),
  CONSTRAINT `fk_comment_trade1`
    FOREIGN KEY (`trade_id`)
    REFERENCES `trade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_comment1`
    FOREIGN KEY (`reply_to_id`)
    REFERENCES `comment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `followed_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `followed_user` ;

CREATE TABLE IF NOT EXISTS `followed_user` (
  `user_id` INT NOT NULL,
  `followed_user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `followed_user_id`),
  INDEX `fk_user_has_user_user2_idx` (`followed_user_id` ASC),
  INDEX `fk_user_has_user_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`followed_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS trainer@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'trainer'@'localhost' IDENTIFIED BY 'trainer';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'trainer'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `email`, `profile_picture`, `first_name`, `last_name`, `biography`, `created_at`) VALUES (1, 'admin', '$2a$10$U4sc5g1WCoNJyEoTzpQaH.ZFCNQEykwdcU4ita9U0LH.MP4FCqvjq', 1, 'admin', 'email@domain.com', NULL, 'ad', 'min', 'bio', '2021-01-01 01:01:01');

COMMIT;


-- -----------------------------------------------------
-- Data for table `order_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `order_type` (`id`, `name`, `description`) VALUES (1, 'Market', 'an order to buy or sell a security immediately');
INSERT INTO `order_type` (`id`, `name`, `description`) VALUES (2, 'Limit', 'is an order to buy or sell a security at a specific price or better');

COMMIT;


-- -----------------------------------------------------
-- Data for table `stock`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `stock` (`name`, `symbol`, `exchange_name`) VALUES ('Apple Inc.', 'AAPL', 'NASDAQ');
INSERT INTO `stock` (`name`, `symbol`, `exchange_name`) VALUES ('Oracle Corp.', 'ORCL', 'NASDAQ');

COMMIT;


-- -----------------------------------------------------
-- Data for table `trade`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `trade` (`id`, `price_per_share`, `quantity`, `created_at`, `buy`, `completion_date`, `user_id`, `order_type_id`, `notes`, `stock_symbol`, `strike_price`) VALUES (1, 30, 100, '2021-01-01 01:01:01', buy, '2021-01-01 01:01:01', 1, 1, NULL, 'ORCL', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `message`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `message` (`id`, `content`, `read`, `sender_id`, `recipent_id`, `reply_to_id`, `created_at`) VALUES (1, 'Hey i saw that you needed some money', 0, 1, 1, 1, '2021-01-01 01:01:01');

COMMIT;


-- -----------------------------------------------------
-- Data for table `account`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `account` (`id`, `balance`, `margin_enable`, `margin_amount`, `user_id`) VALUES (1, 1000.00, 1, 500, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `comment` (`id`, `content`, `trade_id`, `user_id`, `created_at`, `reply_to_id`) VALUES (1, 'Nice trade!', 1, 1, '2021-01-01 01:01:01', 1);
INSERT INTO `comment` (`id`, `content`, `trade_id`, `user_id`, `created_at`, `reply_to_id`) VALUES (2, 'I wish i had made that trade', 1, 1, '2021-01-01 01:01:01', 1);
INSERT INTO `comment` (`id`, `content`, `trade_id`, `user_id`, `created_at`, `reply_to_id`) VALUES (3, 'Too the moon', 1, 1, '2021-01-01 01:01:01', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `followed_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `followed_user` (`user_id`, `followed_user_id`) VALUES (1, 1);

COMMIT;

