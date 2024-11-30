DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `class_id` int NOT NULL AUTO_INCREMENT,
  `day_id` int NOT NULL,
  `schedule_id_init` int NOT NULL,
  `schedule_id_end` int NOT NULL,
  `teacher_id` int NOT NULL,
  `class_name` varchar(60) NOT NULL,
  `class_persons_amount` int NOT NULL,
  `class_status` varchar(45) NOT NULL,
  PRIMARY KEY (`class_id`),
  UNIQUE KEY `class_id_UNIQUE` (`class_id`),
  KEY `day_id_fk_idx` (`day_id`),
  KEY `schedule_id_init_fk_idx` (`schedule_id_init`),
  KEY `schedule_id_end_fk_idx` (`schedule_id_end`),
  KEY `teacher_id_fk_idx` (`teacher_id`),
  CONSTRAINT `day_id_fk` FOREIGN KEY (`day_id`) REFERENCES `day` (`day_id`),
  CONSTRAINT `schedule_id_end_fk` FOREIGN KEY (`schedule_id_end`) REFERENCES `schedule` (`schedule_id`),
  CONSTRAINT `schedule_id_init_fk` FOREIGN KEY (`schedule_id_init`) REFERENCES `schedule` (`schedule_id`),
  CONSTRAINT `teacher_id_fk` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;