DROP TABLE IF EXISTS `password_recuperation`;
CREATE TABLE `password_recuperation` (
  `password_recuperation_id` int NOT NULL AUTO_INCREMENT,
  `password_recuperation_code` varchar(45) NOT NULL,
  `password_recuperation_email` varchar(100) NOT NULL,
  `password_recuperation_creation_date_time` datetime NOT NULL,
  PRIMARY KEY (`password_recuperation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;