DROP TABLE IF EXISTS `routine`;

CREATE TABLE `routine` (
  `routine_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  `routine_aditional_info` varchar(500) DEFAULT NULL,
  `routine_title` varchar(100) NOT NULL,
  `routine_creation_date` date NOT NULL,
  `routine_modification_date` date NOT NULL,
  PRIMARY KEY (`routine_id`),
  UNIQUE KEY `routine_id_UNIQUE` (`routine_id`),
  KEY `customer_id_fk_idx` (`customer_id`),
  KEY `teacher_id_fk_idx` (`teacher_id`),
  CONSTRAINT `routine_customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `routine_teacher_id_fk` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;