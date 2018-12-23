-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`group` (
  `groupID` INT(11) NOT NULL,
  PRIMARY KEY (`groupID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`account` (
  `User` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `groupID` INT(11) NOT NULL,
  PRIMARY KEY (`User`),
  INDEX `fk_Account_Group_idx` (`groupID` ASC),
  CONSTRAINT `fk_Account_Group`
    FOREIGN KEY (`groupID`)
    REFERENCES `mydb`.`group` (`groupID`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`item` (
  `itemName` VARCHAR(45) NOT NULL,
  `defaultPrice` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`itemName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`order` (
  `orderID` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL DEFAULT NULL,
  `deadline` VARCHAR(45) NULL DEFAULT NULL,
  `number` INT(11) NULL DEFAULT NULL,
  `canceled` TINYINT(4) NULL DEFAULT NULL,
  `itemName` VARCHAR(45) NOT NULL,
  `watched` TINYINT(4) NULL DEFAULT NULL,
  `User` VARCHAR(45) NOT NULL,
  `bought` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  INDEX `fk_Order_item1_idx` (`itemName` ASC),
  INDEX `fk_Order_Account1_idx` (`User` ASC),
  CONSTRAINT `fk_Order_Account1`
    FOREIGN KEY (`User`)
    REFERENCES `mydb`.`account` (`User`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Order_item1`
    FOREIGN KEY (`itemName`)
    REFERENCES `mydb`.`item` (`itemName`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
