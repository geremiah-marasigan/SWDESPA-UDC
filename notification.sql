CREATE TABLE `Notification` (
  `Name` varchar(45) NOT NULL,
  `DateSent` varchar(45) NOT NULL,
  PRIMARY KEY (`Name`,`DateSent`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;