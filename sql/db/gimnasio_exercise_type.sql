DROP TABLE IF EXISTS `exercise_type`;
CREATE TABLE `exercise_type` (
  `exercise_type_id` int NOT NULL AUTO_INCREMENT,
  `exercise_type_name` varchar(150) NOT NULL,
  PRIMARY KEY (`exercise_type_id`),
  UNIQUE KEY `exercise_type_id_UNIQUE` (`exercise_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `exercise_type` VALUES (1,'Pectorales'),(2,'Espalda'),(3,'Bíceps'),(4,'Tríceps'),(5,'Abdomen'),(6,'Hombros'),(7,'Pantorrillas'),(8,'Piernas');