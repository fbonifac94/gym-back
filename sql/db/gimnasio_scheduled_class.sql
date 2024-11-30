DROP TABLE IF EXISTS `scheduled_class`;
CREATE TABLE `scheduled_class` (
  `scheduled_class_id` int NOT NULL AUTO_INCREMENT,
  `scheduled_class_init_date_time` datetime NOT NULL,
  `scheduled_class_end_date_time` datetime NOT NULL,
  `scheduled_class_actual_amount` int NOT NULL,
  `class_id` int NOT NULL,
  `scheduled_class_status` varchar(45) NOT NULL,
  `teacher_id` int NOT NULL,
  `scheduled_class_total_amount` int NOT NULL,
  PRIMARY KEY (`scheduled_class_id`),
  UNIQUE KEY `scheduled_class_id_UNIQUE` (`scheduled_class_id`),
  KEY `class_id_fk_idx` (`class_id`),
  CONSTRAINT `class_id_fk` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;