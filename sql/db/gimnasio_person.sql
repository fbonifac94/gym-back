DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `person_first_name` varchar(50) NOT NULL,
  `person_last_name` varchar(50) NOT NULL,
  `person_phone` varchar(30) NOT NULL,
  `person_city` varchar(50) NOT NULL,
  `person_district` varchar(50) NOT NULL,
  `person_address` varchar(80) NOT NULL,
  `person_born_date` date DEFAULT NULL,
  `person_country` varchar(50) NOT NULL,
  `person_document_type` varchar(20) NOT NULL,
  `person_document_number` varchar(50) NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `persona_id_UNIQUE` (`person_id`),
  KEY `document_type_id_fk_idx` (`person_document_type`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `person` VALUES (9,'owner','owner','12345678','Buenos Aires','Lanús','Calle falsa 123','1949-03-10','Argentina','DNI','1234555'),(19,'Administrador','Administrador','12345678','Buenos Aires','Ituzaingó','Calle falsa 1234','1990-10-10','Argentina','DNI','12345678'),(20,'Profesor','Uno','1234567890','Buenos Aires','Castelar','Calle falsa 12','1989-09-12','Argentina','DNI','98765433'),(21,'Profesor','Dos','12345678','Buenos Aires','Morón','Calle falsa 1','1990-06-05','Argentina','DNI','1234561234'),(22,'Cliente','Uno','1234567890','Buenos Aires','Morón','Calle falsa 2','1993-01-01','Argentina','Pasaporte','12345678901'),(23,'Pepe','Cliente','1234567890','Buenos Aires','Villa lugano','Callle 123','1998-10-10','Argentina','DNI','98765435'),(24,'Lalo','Lalo','123456789','Capital federal','Capital federal','Calle 9804','1990-01-01','Argentina','DNI','123645234');
