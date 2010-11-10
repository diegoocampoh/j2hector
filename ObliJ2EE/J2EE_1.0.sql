SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `banco` ;
CREATE SCHEMA IF NOT EXISTS `banco` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `banco` ;

-- -----------------------------------------------------
-- Table `banco`.`TipoCliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `banco`.`TipoCliente` ;

CREATE  TABLE IF NOT EXISTS `banco`.`TipoCliente` (
  `idTipoCliente` INT NOT NULL ,
  `Nombre` VARCHAR(45) NULL ,
  PRIMARY KEY (`idTipoCliente`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `banco`.`Cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `banco`.`Cliente` ;

CREATE  TABLE IF NOT EXISTS `banco`.`Cliente` (
  `id` INT NOT NULL ,
  `TipoCliente_idTipoCliente` INT NULL ,
  `SaldoCuenta` DOUBLE NULL ,
  `SaldoReservado` DOUBLE NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Cliente_TipoCliente1` (`TipoCliente_idTipoCliente` ASC) ,
  CONSTRAINT `fk_Cliente_TipoCliente1`
    FOREIGN KEY (`TipoCliente_idTipoCliente` )
    REFERENCES `banco`.`TipoCliente` (`idTipoCliente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `banco`.`Servicio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `banco`.`Servicio` ;

CREATE  TABLE IF NOT EXISTS `banco`.`Servicio` (
  `id` INT NOT NULL ,
  `Nombre` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `banco`.`Cliente_has_Servicio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `banco`.`Cliente_has_Servicio` ;

CREATE  TABLE IF NOT EXISTS `banco`.`Cliente_has_Servicio` (
  `Cliente_id` INT NOT NULL ,
  `Servicio_id` INT NOT NULL ,
  `MontoDebitar` DOUBLE NULL ,
  `fechaInicio` DATE NULL ,
  `fechaFin` DATE NULL ,
  `Periodicidad` DOUBLE NULL ,
  PRIMARY KEY (`Cliente_id`, `Servicio_id`) ,
  INDEX `fk_Cliente_has_Servicio_Servicio1` (`Servicio_id` ASC) ,
  CONSTRAINT `fk_Cliente_has_Servicio_Cliente1`
    FOREIGN KEY (`Cliente_id` )
    REFERENCES `banco`.`Cliente` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_has_Servicio_Servicio1`
    FOREIGN KEY (`Servicio_id` )
    REFERENCES `banco`.`Servicio` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `banco`.`Debito`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `banco`.`Debito` ;

CREATE  TABLE IF NOT EXISTS `banco`.`Debito` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `FechaEjecucion` DATE NULL ,
  `MontoDebitado` VARCHAR(45) NULL ,
  `Cliente_id` INT NOT NULL ,
  `Servicio_id` INT NOT NULL ,
  `Confirmado` TINYINT(1)  NULL ,
  `Resultado` VARCHAR(45) NULL ,
  `Cliente_has_Servicio_Cliente_id` INT NOT NULL ,
  `Cliente_has_Servicio_Servicio_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Debito_Cliente_has_Servicio1` (`Cliente_has_Servicio_Cliente_id` ASC, `Cliente_has_Servicio_Servicio_id` ASC) ,
  CONSTRAINT `fk_Debito_Cliente_has_Servicio1`
    FOREIGN KEY (`Cliente_has_Servicio_Cliente_id` , `Cliente_has_Servicio_Servicio_id` )
    REFERENCES `banco`.`Cliente_has_Servicio` (`Cliente_id` , `Servicio_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
