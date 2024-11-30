DROP TABLE IF EXISTS `routine_exercise`;
CREATE TABLE `routine_exercise` (
  `routine_exercise_id` int NOT NULL AUTO_INCREMENT,
  `routine_id` int NOT NULL,
  `exercise_id` int NOT NULL,
  `routine_exercise_repetitions` int NOT NULL,
  `routine_exercise_series` int NOT NULL,
  `routine_exercise_aditional_info` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`routine_exercise_id`),
  UNIQUE KEY `routine_exercise_id_UNIQUE` (`routine_exercise_id`),
  KEY `routine_id_fk_idx` (`routine_id`),
  KEY `exercise_id_fk_idx` (`exercise_id`),
  KEY `routine_exercise_fk_exercise_id_idx` (`exercise_id`),
  CONSTRAINT `routine_exercise_fk_exercise_id` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`exercise_id`),
  CONSTRAINT `routine_exercise_fk_routine_id` FOREIGN KEY (`routine_id`) REFERENCES `routine` (`routine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;