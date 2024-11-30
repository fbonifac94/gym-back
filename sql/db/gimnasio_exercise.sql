DROP TABLE IF EXISTS `exercise`;
CREATE TABLE `exercise` (
  `exercise_id` int NOT NULL AUTO_INCREMENT,
  `exercise_name` varchar(300) NOT NULL,
  `exercise_type_id` int NOT NULL,
  PRIMARY KEY (`exercise_id`),
  UNIQUE KEY `exercise_id_UNIQUE` (`exercise_id`),
  KEY `exercise_type_id_fk_idx` (`exercise_type_id`),
  CONSTRAINT `exercise_type_id_fk` FOREIGN KEY (`exercise_type_id`) REFERENCES `exercise_type` (`exercise_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `exercise` VALUES (1,'Press de pecho en máquina',1),(2,'Press de pecho declinado en máquina',1),(3,'Pullover en máquina',1),(4,'Remo supino con mancuernas',2),(5,'Remo supino en polea baja (de pie)',2),(6,'Remo sentado en polea con agarre de cuerda',2),(7,'Curl predicador con polea',3),(8,'Curl predicador a una mano con mancuerna',3),(9,'Curl martillo en polea (con cuerda)',3),(10,'Press francés con polea baja',4),(11,'Press de copa con mancuerna de pie',4),(12,'Press cerrado en banco inclinado',4),(13,'Rueda abdominal de rodillas',5),(14,'Rueda abdominal de pie',5),(15,'Rotación de tronco en máquina',5),(16,'Rotación interna de hombro en polea',6),(17,'Rotación externa de hombro en polea',6),(18,'Rotación externa de hombro con mancuerna',6),(19,'Levantamiento de pantorrillas en máquina smith',7),(20,'Elevación de pantorrillas con barra',7),(21,'Levantamiento de pantorrilla a una pierna',7),(22,'Sentadillas sobre bosu',8),(23,'Sentadilla sumo(sin equipo)',8),(24,'Sentadilla sumo en máquina smith',8);
