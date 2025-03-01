-- MySQL Script generated by MySQL Workbench
-- Tue Feb 25 15:22:36 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kelen_fila
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema kelen_fila
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kelen_fila` ;
USE `kelen_fila` ;

-- -----------------------------------------------------
-- Table `kelen_fila`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`country` (
  `country_id` INT(11) NOT NULL AUTO_INCREMENT,
  `country_name` VARCHAR(45) NOT NULL,
  `telephone_code` VARCHAR(45) NOT NULL,
  `country_code` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`country_id`))
ENGINE=InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`person` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `lastname` VARCHAR(150) NOT NULL,
  `firstname` VARCHAR(150) NOT NULL,
  `phonenumber` VARCHAR(25) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  `residence_country` INT(11) NOT NULL, -- Corrigé l'orthographe
  `residence_city` VARCHAR(100) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `birth_date` DATE NOT NULL,
  `is_verified` TINYINT(4) NULL DEFAULT 0,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  `identity_card` VARCHAR(400) NULL,
  `is_identity_card_checked` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_user_country_idx` (`residence_country` ),
  UNIQUE INDEX `email_UNIQUE` (`email` ),
  INDEX `city_index` (`residence_city` ),
  CONSTRAINT `fk_user_country`
    FOREIGN KEY (`residence_country`)
    REFERENCES `kelen_fila`.`country` (`country_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`profile` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `profile_code` VARCHAR(45) NOT NULL,
  `profile_name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `deleted_at` DATETIME NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`account` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(160) NOT NULL,
  `password` VARCHAR(220) NOT NULL,
  `verified_at` TIMESTAMP NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
  `suspended_at` DATETIME NULL DEFAULT NULL,
  `remember_token` VARCHAR(160) NULL DEFAULT NULL,
  `person_type` ENUM('NATURAL', 'LEGAL') DEFAULT 'NATURAL',
  `profile_id` INT(11) NOT NULL,
  `person_id` BIGINT(20) NOT NULL,
  `avatar` VARCHAR(455) NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_account_profile_idx` (`profile_id` ),
  INDEX `fk_account_user_idx` (`person_id` ),
  CONSTRAINT `fk_account_person`
    FOREIGN KEY (`person_id`)
    REFERENCES `kelen_fila`.`person` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_account_profile`
    FOREIGN KEY (`profile_id`)
    REFERENCES `kelen_fila`.`profile` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`auction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`auction` (
  `auction_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `auction_code` VARCHAR(100) NOT NULL,
  `auction_date` DATETIME NOT NULL,
  `estimated_time` DATETIME NULL DEFAULT NULL,
  `auction_owner` BIGINT(20) NOT NULL,
  `auction_add_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
  `auction_duration` DATETIME NULL DEFAULT NULL,
  `auction_description` VARCHAR(400) NULL DEFAULT NULL,
  `auction_title` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`auction_id`),
  UNIQUE INDEX `numEnchere_UNIQUE` (`auction_code` ),
  INDEX `FK_ENCHERE_VENDEUR_idx` (`auction_owner` ),
  CONSTRAINT `FK_ENCHERE_VENDEUR`
    FOREIGN KEY (`auction_owner`)
    REFERENCES `kelen_fila`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`category` (
  `category_id` INT(11) NOT NULL AUTO_INCREMENT, -- Ajout de AUTO_INCREMENT
  `category_name` VARCHAR(100) NOT NULL,
  `category_illustration_image` VARCHAR(400) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `designCategorie_UNIQUE` (`category_name` ))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`article` (
  `article_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `article_name` VARCHAR(100) NOT NULL,
  `article_description` TEXT NOT NULL,
  `article_state` VARCHAR(150) NOT NULL,
  `article_base_price` DOUBLE NOT NULL,
  `item_number` INT(11) NOT NULL,
  `image1` VARCHAR(400) NOT NULL,
  `image2` VARCHAR(400) NOT NULL,
  `image3` VARCHAR(400) NOT NULL,
  `image4` VARCHAR(400) NOT NULL,
  `image5` VARCHAR(400) NULL DEFAULT NULL,
  `image6` VARCHAR(400) NULL DEFAULT NULL,
  `adjudication_datetime` DATETIME NULL DEFAULT NULL,
  `awarded_price` DOUBLE NULL DEFAULT NULL,
  `auction_id` BIGINT(20) NOT NULL,
  `category_id` INT(11) NOT NULL,
  PRIMARY KEY (`article_id`),
  INDEX `fk_lot_category_idx` (`category_id` ),
  INDEX `fk_article_auction` (`auction_id` ),
  CONSTRAINT `fk_article_auction`
    FOREIGN KEY (`auction_id`)
    REFERENCES `kelen_fila`.`auction` (`auction_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_lot_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `kelen_fila`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`participant_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`participant_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL DEFAULT NULL, -- Ajout de DEFAULT NULL
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`participant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`participant` (
  `account_id` BIGINT(20) NOT NULL,
  `auction_id` BIGINT(20) NOT NULL,
  `participant_role_id` INT NOT NULL,
  `registration_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
  `has_join_auction` TINYINT(4) NULL DEFAULT 0,
  `has_actived_notification` TINYINT(4) NULL DEFAULT 0,
  PRIMARY KEY (`account_id`, `auction_id`, `participant_role_id`), -- Ajout d'une clé primaire composite
  INDEX `fk_user_has_enchere_enchere1_idx` (`auction_id` ),
  INDEX `fk_user_has_enchere_user1_idx` (`account_id` ),
  INDEX `fk_participant_role_idx` (`participant_role_id` ),
  CONSTRAINT `fk_user_has_enchere_enchere1`
    FOREIGN KEY (`auction_id`)
    REFERENCES `kelen_fila`.`auction` (`auction_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_enchere_user1`
    FOREIGN KEY (`account_id`)
    REFERENCES `kelen_fila`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_participant_role`
    FOREIGN KEY (`participant_role_id`)
    REFERENCES `kelen_fila`.`participant_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`moral_person_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`moral_person_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ),
  UNIQUE INDEX `name_UNIQUE` (`name` ))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`moral_person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`moral_person` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `city` VARCHAR(200) NOT NULL,
  `country_id` INT NOT NULL,
  `moral_person_type_id` INT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, -- Ajout de DEFAULT CURRENT_TIMESTAMP
  `deleted_at` TIMESTAMP NULL DEFAULT NULL, -- Modifié de DEFAULT CURRENT_TIMESTAMP à DEFAULT NULL
  `account_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_moral_person_type_idx` (`moral_person_type_id` ),
  INDEX `fk_moral_person_account_idx` (`account_id` ),
  INDEX `fk_moral_person_country_idx` (`country_id` ),
  CONSTRAINT `fk_moral_person_type`
    FOREIGN KEY (`moral_person_type_id`)
    REFERENCES `kelen_fila`.`moral_person_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_moral_person_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `kelen_fila`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_moral_person_country` 
    FOREIGN KEY (`country_id`) 
    REFERENCES `kelen_fila`.`country` (`country_id`) 
    ON DELETE NO ACTION 
    ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`administrative_document_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`administrative_document_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `code` VARCHAR(200) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL DEFAULT NULL, -- Ajout de DEFAULT NULL
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ))
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `kelen_fila`.`administrative_document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kelen_fila`.`administrative_document` (
  `account_id` BIGINT(20) NOT NULL,
  `administrative_doc_type` INT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL DEFAULT NULL, -- Ajout de DEFAULT NULL
  PRIMARY KEY (`account_id`, `administrative_doc_type`), -- Ajout d'une clé primaire composite
  INDEX `fk_administrative_document_doc_type_idx` (`administrative_doc_type` ),
  INDEX `fk_administrative_document_moral_person_idx` (`account_id` ),
  CONSTRAINT `fk_administrative_document_moral_person`
    FOREIGN KEY (`account_id`)
    REFERENCES `kelen_fila`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_administrative_document_doc_type`
    FOREIGN KEY (`administrative_doc_type`)
    REFERENCES `kelen_fila`.`administrative_document_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT CHARACTER SET = utf8mb4;