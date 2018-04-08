CREATE TABLE `udc`.`booked` (
  `Name` VARCHAR(45) NOT NULL,
  `StartDay` VARCHAR(45) NOT NULL,
  `EndDay` VARCHAR(45) NOT NULL,
  `Repeat` VARCHAR(45) NOT NULL,
  `StartTime` INT(11) NOT NULL,
  `EndTime` INT(11) NOT NULL,
  `Doctor` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Name`, `StartDay`, `EndDay`, `Repeat`, `StartTime`, `EndTime`, `Doctor`));
