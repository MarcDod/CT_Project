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
-- Table `mydb`.`Group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Group` (
  `groupID` INT NOT NULL,
  PRIMARY KEY (`groupID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Account` (
  `accountID` INT NOT NULL,
  `User` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `groupID` INT NOT NULL,
  PRIMARY KEY (`accountID`),
  INDEX `fk_Account_Group_idx` (`groupID` ASC),
  UNIQUE INDEX `User_UNIQUE` (`User` ASC),
  CONSTRAINT `fk_Account_Group`
    FOREIGN KEY (`groupID`)
    REFERENCES `mydb`.`Group` (`groupID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Item` (
  `itemName` VARCHAR(45) NOT NULL,
  `defaultPrice` FLOAT NULL,
  PRIMARY KEY (`itemName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Order` (
  `orderID` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL,
  `deadline` VARCHAR(45) NULL,
  `number` INT NULL,
  `closed` TINYINT NULL,
  `itemName` VARCHAR(45) NOT NULL,
  `accountID` INT NOT NULL,
  PRIMARY KEY (`orderID`),
  INDEX `fk_Order_item1_idx` (`itemName` ASC),
  INDEX `fk_Order_Account1_idx` (`accountID` ASC),
  CONSTRAINT `fk_Order_item1`
    FOREIGN KEY (`itemName`)
    REFERENCES `mydb`.`Item` (`itemName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_Account1`
    FOREIGN KEY (`accountID`)
    REFERENCES `mydb`.`Account` (`accountID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
