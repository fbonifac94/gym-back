DROP TABLE IF EXISTS `day`;
CREATE TABLE `day` (
  `day_id` int NOT NULL AUTO_INCREMENT,
  `day_name` varchar(45) NOT NULL,
  `day_number` int NOT NULL,
  PRIMARY KEY (`day_id`),
  UNIQUE KEY `day_id_UNIQUE` (`day_id`),
  UNIQUE KEY `day_name_UNIQUE` (`day_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `day` VALUES (1,'LUNES',1),(2,'MARTES',2),(3,'MIERCOLES',3),(4,'JUEVES',4),(5,'VIERNES',5),(6,'SABADO',6),(7,'DOMINGO',7);