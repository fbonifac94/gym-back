DROP TABLE IF EXISTS `scheduled_class_inscription`;
CREATE TABLE `scheduled_class_inscription` (
  `scheduled_class_inscription_id` int NOT NULL AUTO_INCREMENT,
  `scheduled_class_id` int NOT NULL,
  `customer_id` int NOT NULL,
  PRIMARY KEY (`scheduled_class_inscription_id`),
  UNIQUE KEY `idscheduled_class_inscription_UNIQUE` (`scheduled_class_inscription_id`),
  KEY `scheduled_class_id_fk_idx` (`scheduled_class_id`),
  KEY `customer_id_fk_idx` (`customer_id`),
  CONSTRAINT `customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `scheduled_class_id_fk` FOREIGN KEY (`scheduled_class_id`) REFERENCES `scheduled_class` (`scheduled_class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;