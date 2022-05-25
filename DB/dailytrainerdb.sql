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
-- Table `account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `account` ;

CREATE TABLE IF NOT EXISTS `account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `balance` DECIMAL(10,2) NULL,
  `margin_enable` TINYINT NULL,
  `margin_amount` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(200) NULL,
  `enabled` TINYINT NULL,
  `role` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `active` TINYINT NULL,
  `profile_picture` VARCHAR(200) NULL,
  `account_id` INT NOT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_account1_idx` (`account_id` ASC),
  INDEX `fk_user_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `holding`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `holding` ;

CREATE TABLE IF NOT EXISTS `holding` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` INT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_holding_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_holding_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `stock` ;

CREATE TABLE IF NOT EXISTS `stock` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `symbol` VARCHAR(45) NULL,
  `holding_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_stock_holding1_idx` (`holding_id` ASC),
  CONSTRAINT `fk_stock_holding1`
    FOREIGN KEY (`holding_id`)
    REFERENCES `holding` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `transaction` ;

CREATE TABLE IF NOT EXISTS `transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total_price` DECIMAL(10,2) NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transaction_account1_idx` (`account_id` ASC),
  CONSTRAINT `fk_transaction_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `trade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `trade` ;

CREATE TABLE IF NOT EXISTS `trade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(10,2) NULL,
  `quantity` INT NULL,
  `created` DATETIME NULL,
  `order_type` VARCHAR(45) NULL,
  `trade_type` VARCHAR(45) NULL,
  `has_executed` TINYINT NULL,
  `user_id` INT NOT NULL,
  `stock_id` INT NOT NULL,
  `transaction_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Trade_user_idx` (`user_id` ASC),
  INDEX `fk_trade_stock1_idx` (`stock_id` ASC),
  INDEX `fk_trade_transaction1_idx` (`transaction_id` ASC),
  CONSTRAINT `fk_Trade_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_stock1`
    FOREIGN KEY (`stock_id`)
    REFERENCES `stock` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trade_transaction1`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `transaction` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `message` ;

CREATE TABLE IF NOT EXISTS `message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(300) NULL,
  `read` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_has_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_has_message` ;

CREATE TABLE IF NOT EXISTS `user_has_message` (
  `user_id` INT NOT NULL,
  `message_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `message_id`),
  INDEX `fk_user_has_message_message1_idx` (`message_id` ASC),
  INDEX `fk_user_has_message_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_message_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_message_message1`
    FOREIGN KEY (`message_id`)
    REFERENCES `message` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comment` ;

CREATE TABLE IF NOT EXISTS `comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(300) NULL,
  `trade_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `created` DATE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_trade1_idx` (`trade_id` ASC),
  INDEX `fk_comment_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_comment_trade1`
    FOREIGN KEY (`trade_id`)
    REFERENCES `trade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
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
-- Data for table `account`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `account` (`id`, `balance`, `margin_enable`, `margin_amount`) VALUES (1, 1000.00, 1, 500);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `email`, `active`, `profile_picture`, `account_id`, `user_id`) VALUES (1, 'admin', '$2a$10$U4sc5g1WCoNJyEoTzpQaH.ZFCNQEykwdcU4ita9U0LH.MP4FCqvjq', 1, 'admin', 'email@domain.com', 1, NULL, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `holding`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `holding` (`id`, `amount`, `user_id`) VALUES (1, 100, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `stock`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `stock` (`id`, `name`, `symbol`, `holding_id`) VALUES (1, 'Apple Inc.', 'AAPL', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `transaction`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `transaction` (`id`, `total_price`, `account_id`) VALUES (1, 3000, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `trade`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `trade` (`id`, `price`, `quantity`, `created`, `order_type`, `trade_type`, `has_executed`, `user_id`, `stock_id`, `transaction_id`) VALUES (1, 30, 100, '2021-01-01 01:01:01', 'market', 'buy', 1, 1, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `message`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `message` (`id`, `content`, `read`) VALUES (1, 'Hey i saw that you needed some money', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_has_message`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `user_has_message` (`user_id`, `message_id`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `comment` (`id`, `content`, `trade_id`, `user_id`, `created`) VALUES (1, 'Nice trade!', 1, 1, NULL);
INSERT INTO `comment` (`id`, `content`, `trade_id`, `user_id`, `created`) VALUES (2, 'I wish i had made that trade', 1, 1, NULL);
INSERT INTO `comment` (`id`, `content`, `trade_id`, `user_id`, `created`) VALUES (3, 'Too the moon', 1, 1, NULL);

COMMIT;

