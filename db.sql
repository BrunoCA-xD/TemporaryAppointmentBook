CREATE DATABASE `contact_list` /*!40100 DEFAULT CHARACTER SET utf8 */;

use contact_list;

CREATE TABLE `contact` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(60) NOT NULL,
  `CPF` varchar(13) NOT NULL,
  `RG` varchar(12) NOT NULL,
  `NICKNAME` varchar(50) DEFAULT NULL,
  `ADDRESS` varchar(200) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `PHONE` varchar(11) NOT NULL,
  `WHATSAPP` varchar(11) NOT NULL,
  `LAST_CALL` date NOT NULL,
  `NEXT_CALL` date ,
  `CALL_INTERVAL` int,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
