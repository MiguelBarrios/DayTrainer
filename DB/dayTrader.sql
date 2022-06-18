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
  `symbol` VARCHAR(10) NOT NULL,
  `name` VARCHAR(200) NULL,
  `sector` VARCHAR(200) NULL,
  PRIMARY KEY (`symbol`),
  UNIQUE INDEX `symbol_UNIQUE` (`symbol` ASC))
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
  `strike_price` DECIMAL(5,2) NULL,
  `stock_symbol` VARCHAR(10) NOT NULL,
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
  `deposit` DECIMAL(15,2) NULL,
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
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `email`, `profile_picture`, `first_name`, `last_name`, `biography`, `created_at`) VALUES (1, 'admin', '$2a$10$U4sc5g1WCoNJyEoTzpQaH.ZFCNQEykwdcU4ita9U0LH.MP4FCqvjq', 1, 'admin', 'email@domain.com', 'https://k5k8z6h5.stackpathcdn.com/wp-content/uploads/2021/06/90-Day-Fiance-Ed-Brown-6329-800x445.jpg', 'ad', 'min', 'bio', '2021-01-01 01:01:01');
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `email`, `profile_picture`, `first_name`, `last_name`, `biography`, `created_at`) VALUES (2, 'miguel', '$2a$10$U4sc5g1WCoNJyEoTzpQaH.ZFCNQEykwdcU4ita9U0LH.MP4FCqvjq', 1, 'standard', NULL, 'https://jenworley.com/wp-content/uploads/2020/04/Pittsburgh-Headshot-Photographer-1203-min.jpg', 'Miguel', 'Davila', NULL, '2021-01-01 01:01:01');
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `email`, `profile_picture`, `first_name`, `last_name`, `biography`, `created_at`) VALUES (3, 'anthony', '$2a$10$U4sc5g1WCoNJyEoTzpQaH.ZFCNQEykwdcU4ita9U0LH.MP4FCqvjq', 1, 'standard', NULL, 'https://www.bethesdaheadshots.com/wp-content/uploads/2020/02/Jonathan-Business-Headshot.jpg', 'Anthony', 'Butler', NULL, '2021-01-01 01:01:01');
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `email`, `profile_picture`, `first_name`, `last_name`, `biography`, `created_at`) VALUES (4, 'cece', '$2a$10$U4sc5g1WCoNJyEoTzpQaH.ZFCNQEykwdcU4ita9U0LH.MP4FCqvjq', 1, 'standard', NULL, 'https://josephanzalone.com/images/p35.jpg', 'Cecelia', 'Guerrero', NULL, '2021-01-01 01:01:01');
INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `role`, `email`, `profile_picture`, `first_name`, `last_name`, `biography`, `created_at`) VALUES (5, 'danial', '$2a$10$U4sc5g1WCoNJyEoTzpQaH.ZFCNQEykwdcU4ita9U0LH.MP4FCqvjq', 1, 'standard', NULL, 'https://paloaltoheadshots.com/wp-content/uploads/2020/11/formal-business-headshot.jpg', 'Daniel', 'Kregstein', NULL, '2021-01-01 01:01:01');

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
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AAPL', 'Apple Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MSFT', 'Microsoft Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GOOG', 'Alphabet Inc Class C', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GOOGL', 'Alphabet Inc Class A', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AMZN', 'Amazon.Com Inc.', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TSLA', 'Tesla Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BRK.B', 'Berkshire Hathaway Inc. Class B', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FB', 'Facebook Inc Class A', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('V', 'Visa Inc Class A', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NVDA', 'Nvidia Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('JNJ', 'Johnson & Johnson', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UNH', 'Unitedhealth Group Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('XOM', 'Exxon Mobil Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('JPM', 'JP Morgan Chase & Co', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PG', 'Procter & Gamble', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CVX', 'Chevron Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MA', 'Mastercard Inc Class A', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WMT', 'Walmart Stores Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HD', 'Home Depot Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PFE', 'Pfizer Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BAC', 'Bank of America Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LLY', 'Eli Lilly', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KO', 'Coca-Cola', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ABBV', 'Abbvie Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AVGO', 'Broadcom Inc.', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PEP', 'Pepsico Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MRK', 'Merck & Co Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TMO', 'Thermo Fisher Scientific Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VZ', 'Verizon Communications Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('COST', 'Costco Wholesale Corp', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ABT', 'Abbott Laboratories', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ADBE', 'Adobe Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ACN', 'Accenture Plc Class A', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DIS', 'Walt Disney', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DHR', 'Danaher Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CMCSA', 'Comcast A Corp', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ORCL', 'Oracle Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NKE', 'Nike Inc Class B', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CSCO', 'Cisco Systems Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CRM', 'Salesforce.Com Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MCD', 'Mcdonalds Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('INTC', 'Intel Corporation Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AMD', 'Advanced Micro Devices Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TMUS', 'T Mobile US Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WFC', 'Wells Fargo', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LIN', 'Linde Plc', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PM', 'Philip Morris International Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UPS', 'United Parcel Service Inc Class B', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BMY', 'Bristol Myers Squibb', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TXN', 'Texas Instrument Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('QCOM', 'Qualcomm Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NEE', 'Nextera Energy Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('COP', 'ConocoPhillips', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('T', 'AT&T Inc.', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MS', 'Morgan Stanley', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RTX', 'Raytheon Technologies Corporation', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UNP', 'Union Pacific Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AMGN', 'Amgen Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HON', 'Honeywell International Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SCHW', 'Charles Schwab Corporation', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MDT', 'Medtronic Plc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IBM', 'International Business Machines Co', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AXP', 'American Express', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LOW', 'Lowes Companies Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CVS', 'CVS Health Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AMT', 'American Tower Corporation', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ANTM', 'Anthem Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CAT', 'Caterpillar Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LMT', 'Lockheed Martin Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SPGI', 'S&P Global Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('INTU', 'Intuit Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DE', 'Deere & Company', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GS', 'Goldman Sachs Group Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BLK', 'Blackrock Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('C', 'Citigroup Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PYPL', 'Paypal Holdings Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AMAT', 'Applied Material Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NOW', 'Servicenow Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MO', 'Altria Group Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EL', 'Estee Lauder Inc Class A', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BKNG', 'Booking Holdings Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PLD', 'Prologis, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ADP', 'Automatic Data Processing Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SBUX', 'Starbucks Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CB', 'Chubb Ltd', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NFLX', 'Netflix Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SYK', 'Stryker Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MDLZ', 'Mondelez International Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ADI', 'Analog Devices Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DUK', 'Duke Energy Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GE', 'General Electric', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EOG', 'EOG Resources Inc', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MMM', '3M', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CHTR', 'Charter Communications Inc Class A', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BA', 'Boeing', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CI', 'Cigna Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SO', 'Southern Co', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GILD', 'Gilead Sciences Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ZTS', 'Zoetis Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CCI', 'Crown Castle International Corp.', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MMC', 'Marsh & McLennan Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ISRG', 'Intuitive Surgical Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MU', 'Micron Technology Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('USB', 'US Bancorp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TGT', 'Target Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NOC', 'Northrop Grumman Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TJX', 'TJX Companies, Inc.', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BDX', 'Becton Dickinson', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CME', 'CME Group Inc Class A', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PNC', 'PNC Financial Services Group, Inc.', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LRCX', 'Lam Research Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SHW', 'Sherwin Williams', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CSX', 'CSX Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PGR', 'Progressive Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VRTX', 'Vertex Pharmaceuticals Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('REGN', 'Regeneron Pharmaceuticals Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PXD', 'Pioneer Natural Resource', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('D', 'Dominion Energy Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SLB', 'Schlumberger Nv', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CL', 'Colgate-Palmolive', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WM', 'Waste Management Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('OXY', 'Occidental Petroleum Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TFC', 'Truist Financial Corporation', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FISV', 'Fiserv Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ITW', 'Illinois Tool Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GD', 'General Dynamics Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FIS', 'Fidelity National Information Services', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NSC', 'Norfolk Southern Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EQIX', 'Equinix, Inc.', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HCA', 'HCA Healthcare, Inc.', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ATVI', 'Activision Blizzard Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EW', 'Edwards Lifesciences Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FCX', 'Freeport-McMoRan, Inc.', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PSA', 'Public Storage', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MAR', 'Marriott International Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MPC', 'Marathon Petroleum Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BSX', 'Boston Scientific Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AON', 'Aon Plc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FDX', 'FedEx Corporation', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ICE', 'Intercontinental Exchange Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ETN', 'Eaton Corporation, PLC', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HUM', 'Humana Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('APD', 'Air Products And Chemicals Inc', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VLO', 'Valero Energy Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GM', 'General Motors', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MRNA', 'Moderna, Inc.', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KLAC', 'KLA-Tencor Corporation', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MET', 'Metlife Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NEM', 'Newmont Corporation', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EMR', 'Emerson Electric', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('F', 'Ford Motor Company', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MCO', 'Moodys Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AEP', 'American Electric Power Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DG', 'Dollar General Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DVN', 'Devon Energy Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SRE', 'Sempra Energy', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PSX', 'Phillips 66', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SNPS', 'Synopsys Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ADM', 'Archer-Daniels-Midland Company', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DOW', 'Dow Inc.', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('COF', 'Capital One Financial Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ECL', 'Ecolab Inc', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CNC', 'Centene Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NXPI', 'NXP Semiconductors Nv', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EXC', 'Exelon Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FTNT', 'Fortinet Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MNST', 'Monster Beverage Corp', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LHX', 'L3Harris Technologies, Inc.', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MCK', 'Mckesson Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ROP', 'Roper Technologies Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WMB', 'Williams Inc', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('STZ', 'Constellation Brands Class A', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PAYX', 'Paychex Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AIG', 'American International Group Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ADSK', 'Autodesk Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KMI', 'Kinder Morgan Inc', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CTVA', 'Corteva, Inc.', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KHC', 'Kraft Heinz', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KMB', 'Kimberly Clark Corp', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CDNS', 'Cadence Design Systems Inc', 'Information technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HSY', 'Hershey Foods', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SYY', 'Sysco Corp', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WBD', 'Warner Bros. Discovery, Inc. - Series A', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RSG', 'Republic Services Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('APH', 'Amphenol Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HPQ', 'HP Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TEL', 'TE Connectivity Ltd', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TRV', 'Travelers Companies Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IQV', 'IQVIA Holdings, Inc.', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ORLY', 'Oreilly Automotive Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GIS', 'General Mills Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AZO', 'Autozone Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('XEL', 'Xcel Energy Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CTAS', 'Cintas Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('O', 'Realty Income Corporation', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WELL', 'Welltower Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HES', 'Hess Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HLT', 'Hilton Worldwide Holdings Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MCHP', 'Microchip Technology Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EA', 'Electronic Arts Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BKR', 'Baker Hughes Company Class A', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PRU', 'Prudential Financial Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CMG', 'Chipotle Mexican Grill Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('A', 'Agilent Technologies Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CTSH', 'Cognizant Technology Solutions', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DLR', 'Digital Realty Trust, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AFL', 'Aflac Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KR', 'Kroger', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('JCI', 'Johnson Controls International Plc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HAL', 'Halliburton', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MSI', 'Motorola Solutions Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WBA', 'Walgreen Boots Alliance Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BK', 'Bank Of New York Mellon Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ILMN', 'Illumina Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BAX', 'Baxter International Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GPN', 'Global Payments Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ALL', 'Allstate Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SPG', 'Simon Property Group, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DLTR', 'Dollar Tree Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MSCI', 'MSCI Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PH', 'Parker-Hannifin Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SBAC', 'SBA Communications Corporation', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LYB', 'Lyondellbasell Industries NV', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ED', 'Consolidated Edison Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NUE', 'Nucor Corp', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PEG', 'Public Service Enterprise Group Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AJG', 'Arthur J Gallagher', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DD', 'DuPont de Nemours, Inc.', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('YUM', 'Yum Brands Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IFF', 'International Flavors & Fragrances', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TDG', 'Transdigm Group Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CARR', 'Carrier Global Corporation', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WEC', 'WEC Energy Group Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TT', 'Trane Technologies plc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MTB', 'M&T Bank Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ES', 'Eversource Energy', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TSN', 'Tyson Foods Inc Class A', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ANET', 'Arista Networks Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('OTIS', 'Otis Worldwide Corporation', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FAST', 'Fastenal', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IDXX', 'Idexx Laboratories Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BF.B', 'Brown Forman Inc Class B', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DFS', 'Discover Financial Services', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ABC', 'AmerisourceBergen Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PCAR', 'Paccar Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TWTR', 'Twitter Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RMD', 'Resmed Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ODFL', 'Old Dominion Freight Line Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GLW', 'Corning Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('OKE', 'Oneok Inc', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AMP', 'Ameriprise Finance Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CMI', 'Cummins Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PPG', 'PPG Industries Inc', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MTD', 'Mettler Toledo Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BIIB', 'Biogen Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ALB', 'Albemarle Corp', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ROST', 'Ross Stores Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('APTV', 'Aptiv Plc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DXCM', 'Dexcom Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AVB', 'AvalonBay Communities, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EQR', 'Equity Residential', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WY', 'Weyerhaeuser Company', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TROW', 'T Rowe Price Group Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CTRA', 'Coterra Energy Inc.', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AME', 'Ametek Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SIVB', 'SVB Financial Group', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AWK', 'American Water Works Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CERN', 'Cerner Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FANG', 'Diamondback Energy Inc', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VRSK', 'Verisk Analytics Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CPRT', 'Copart Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DHI', 'D R Horton Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LVS', 'Las Vegas Sands Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ARE', 'Alexandria Real Estate Equities, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FITB', 'Fifth Third Bancorp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EIX', 'Edison International', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ENPH', 'Enphase Energy Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FRC', 'First Republic Bank', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KEYS', 'Keysight Technologies Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EBAY', 'Ebay Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('STT', 'State Street Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CBRE', 'CBRE Group Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LUV', 'Southwest Airlines', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DTE', 'DTE Energy', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ROK', 'Rockwell Automation Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GWW', 'W.W. Grainger Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ZBH', 'Zimmer Biomet Holdings Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DAL', 'Delta Air Lines Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HRL', 'Hormel Foods Corp', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EFX', 'Equifax Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ETR', 'Entergy Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AEE', 'Ameren Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MKC', 'McCormick & Co  Non-voting', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EXR', 'Extra Space Storage Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FE', 'Firstenergy Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CDW', 'CDW Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HIG', 'Hartford Financial Services Group', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BALL', 'Ball Corporation', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MTCH', 'Match Group Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LEN', 'Lennar Corporation Class A', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WTW', 'Willis Towers Watson Public Limited Company', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WST', 'West Pharmaceutical Services Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NTRS', 'Northern Trust Corporation', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('K', 'Kellogg', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LH', 'Laboratory Corporation Of America', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ANSS', 'Ansys Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VTR', 'Ventas, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FTV', 'Fortive Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MRO', 'Marathon Oil Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('STE', 'STERIS PLC', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VMC', 'Vulcan Materials', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PPL', 'PPL Corporation', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TSCO', 'Tractor Supply', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LYV', 'Live Nation Entertainment Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MOS', 'Mosaic', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('URI', 'United Rentals Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ALGN', 'Align Technology Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MLM', 'Martin Marietta Materials Inc', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CHD', 'Church And Dwight Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IT', 'Gartner Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MPWR', 'Monolithic Power Systems Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CEG', 'Constellation Energy Corporation', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ULTA', 'Ulta Beauty Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MAA', 'Mid-America Apartment Communities, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CMS', 'CMS Energy Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DRE', 'Duke Realty Corporation', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WAT', 'Waters Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RF', 'Regions Financial Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CINF', 'Cincinnati Financial Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GRMN', 'Garmin Ltd', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AMCR', 'Amcor plc', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PARA', 'Paramount Global - Class B', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CFG', 'Citizens Financial Group Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CF', 'CF Industries Holdings Inc', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RJF', 'Raymond James Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EXPE', 'Expedia Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CNP', 'Centerpoint Energy Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CTLT', 'Catalent Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IR', 'Ingersoll Rand Plc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HPE', 'Hewlett Packard Enterprise', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HBAN', 'Huntington Bancshares Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VRSN', 'Verisign Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VFC', 'VF Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GPC', 'Genuine Parts', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DOV', 'Dover Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EPAM', 'Epam Systems Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TDY', 'Teledyne Technologies Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FLT', 'Fleetcor Technologies Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WDC', 'Western Digital Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('JBHT', 'JB Hunt Transport Services Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HOLX', 'Hologic Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WRB', 'WR Berkley Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PKI', 'Perkinelmer Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PWR', 'Quanta Services Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ESS', 'Essex Property Trust, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EXPD', 'Expeditors International Of Washington', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FOX', 'Fox Corporation Class B', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FOXA', 'Fox Corporation Class A', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KEY', 'Keycorp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('COO', 'Cooper Companies, Inc.', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BBY', 'Best Buy Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PAYC', 'Paycom Software Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PFG', 'Principal Financial Group Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SYF', 'Synchrony Financial', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('STX', 'Seagate Technology Plc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('J', 'Jacobs Engineering Group Inc.', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ZBRA', 'Zebra Technologies Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IP', 'International Paper', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WAB', 'Westinghouse Air Brake Technologies', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TER', 'Teradyne Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SWKS', 'Skyworks Solutions Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SWK', 'Stanley Black & Decker Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ROL', 'Rollins Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TRMB', 'Trimble Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GNRC', 'Generac Holdings Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BR', 'Broadridge Financial Solutions Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CLX', 'Clorox', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CE', 'Celanese Corporation', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BXP', 'Boston Properties, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BRO', 'Brown & Brown Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MOH', 'Molina Healthcare Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SEDG', 'SolarEdge Technologies Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('INCY', 'Incyte Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('APA', 'Apache Corp', 'Energy');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('POOL', 'Pool Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AKAM', 'Akamai Technologies Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ATO', 'Atmos Energy Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EVRG', 'Evergy Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BIO', 'Bio Rad Laboratories Inc Class A', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DGX', 'Quest Diagnostics Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('L', 'Loews Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NTAP', 'NetApp Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DRI', 'Darden Restaurants Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LNT', 'Alliant Energy Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IRM', 'Iron Mountain Incorporated', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PEAK', 'Healthpeak Properties, Inc.', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KMX', 'Carmax Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('XYL', 'Xylem Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CAG', 'Conagra Brands Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HWM', 'Howmet Aerospace Inc.', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CPT', 'Camden Property Trust', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FMC', 'FMC Corp', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UDR', 'UDR, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CCL', 'Carnival Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PKG', 'Packaging Corp Of America', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AES', 'AES Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IEX', 'IDEX Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TYL', 'Tyler Technologies Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MGM', 'MGM Resorts International', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('OMC', 'Omnicom Group Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NDAQ', 'Nasdaq Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TTWO', 'Take Two Interactive Software Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HST', 'Host Hotels & Resorts, Inc', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CAH', 'Cardinal Health Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NVR', 'NVR, Inc.', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UAL', 'United Continental Holdings Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AVY', 'Avery Dennison Corp', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LDOS', 'Leidos Holdings Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FDS', 'Factset Research Systems Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RCL', 'Royal Caribbean Cruises Ltd', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LKQ', 'LKQ Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TXT', 'Textron Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NLOK', 'NortonLifeLock Inc.', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TECH', 'Bio-Techne Corp', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('EMN', 'Eastman Chemical', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VTRS', 'Viatris Inc.', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('KIM', 'Kimco Realty Corporation', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PTC', 'PTC Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CPB', 'Campbell Soup', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CHRW', 'C.H. Robinson Worldwide, Inc.', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('JKHY', 'Jack Henry & Associates, Inc.', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SJM', 'J.M. Smucker', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MAS', 'Masco Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DPZ', 'Dominos Pizza Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BEN', 'Franklin Resources Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SBNY', 'Signature Bank', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TFX', 'Teleflex Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CTXS', 'Citrix Systems Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NI', 'Nisource Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NDSN', 'Nordson Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WRK', 'Westrock', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HAS', 'Hasbro Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CRL', 'Charles River Laboratories International', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IPG', 'Interpublic Group Of Companies Inc', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CBOE', 'Cboe Global Markets, Inc.', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LUMN', 'CenturyLink, Inc.', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ABMD', 'Abiomed Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SNA', 'Snap On Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TAP', 'Molson Coors Brewing Class B', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HSIC', 'Henry Schein Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AAP', 'Advance Auto Parts Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('QRVO', 'Qorvo, Inc.', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('REG', 'Regency Centers Corporation', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DISH', 'Dish Network Corp Class A', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RE', 'Everest Re Group Ltd', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CZR', 'Caesars Entertainment Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NRG', 'NRG Energy Inc', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CMA', 'Comerica Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PHM', 'Pultegroup Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AAL', 'American Airlines Group Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MKTX', 'Marketaxess Holdings Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UHS', 'Universal Health Services Inc.', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ETSY', 'Etsy Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NWS', 'News Corp Class B', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NWSA', 'News Corp Class A', 'Communication Services');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FFIV', 'F5 Networks Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('JNPR', 'Juniper Networks Inc', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RHI', 'Robert Half', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WHR', 'Whirlpool Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LW', 'Lamb Weston Holdings Inc', 'Consumer Staples');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AOS', 'A O Smith Corp', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ALLE', 'Allegion PLC', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('AIZ', 'Assurant Inc', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('LNC', 'Lincoln National Corp', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('GL', 'Globe Life Inc.', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('OGN', 'Organon & Co.', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BWA', 'BorgWarner Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FBHS', 'Fortune Brands Home And Security', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('SEE', 'Sealed Air Corp', 'Materials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DVA', 'Davita Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NLSN', 'Nielsen Holdings Plc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('FRT', 'Federal Realty Investment Trust', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('MHK', 'Mohawk Industries Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('TPR', 'Tapestry', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PNW', 'Pinnacle West Corp', 'Utilities');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NWL', 'Newell Brands Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('XRAY', 'Dentsply Sirona Inc', 'Health Care');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('BBWI', 'Bath & Body Works, Inc.', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ZION', 'Zions Bancorporation', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('HII', 'Huntington Ingalls Industries Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IVZ', 'Invesco Ltd', 'Financials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('CDAY', 'Ceridian HCM Holding Inc.', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PNR', 'Pentair', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('DXC', 'DXC Technology Company', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('RL', 'Ralph Lauren Corp Class A', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('WYNN', 'Wynn Resorts Ltd', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('VNO', 'Vornado Realty Trust', 'Real Estate');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('ALK', 'Alaska Air Group Inc', 'Industrials');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PENN', 'Penn National Gaming Inc', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('IPGP', 'IPG Photonics Corp', 'Information Technology');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('PVH', 'PVH Corp', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UA', 'Under Armour Inc Class C', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('UAA', 'Under Armour Inc Class A', 'Consumer Discretionary');
INSERT INTO `stock` (`symbol`, `name`, `sector`) VALUES ('NCLH', 'Norwegian Cruise Line Holdings Ltd', 'Consumer Discretionary');

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
INSERT INTO `account` (`id`, `balance`, `margin_enable`, `margin_amount`, `user_id`, `deposit`) VALUES (1, 1000.00, 1, 500, 1, 1000);
INSERT INTO `account` (`id`, `balance`, `margin_enable`, `margin_amount`, `user_id`, `deposit`) VALUES (2, 1500, 1, 600, 2, 1500);
INSERT INTO `account` (`id`, `balance`, `margin_enable`, `margin_amount`, `user_id`, `deposit`) VALUES (3, 4000, 1, 500, 3, 4000);
INSERT INTO `account` (`id`, `balance`, `margin_enable`, `margin_amount`, `user_id`, `deposit`) VALUES (4, 200, 1, 500, 4, 200);
INSERT INTO `account` (`id`, `balance`, `margin_enable`, `margin_amount`, `user_id`, `deposit`) VALUES (5, 500, 1, 500, 5, 500);

COMMIT;


-- -----------------------------------------------------
-- Data for table `followed_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `dailytrainerdb`;
INSERT INTO `followed_user` (`user_id`, `followed_user_id`) VALUES (1, 1);

COMMIT;

