DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(50) NOT NULL,
  `user_password` varchar(200) NOT NULL,
  `person_id` int NOT NULL,
  `role_id` int NOT NULL,
  `user_is_first_login` tinyint NOT NULL DEFAULT '1',
  `user_expire_date` datetime DEFAULT NULL,
  `user_status` varchar(45) NOT NULL DEFAULT 'BA',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_email_UNIQUE` (`user_email`),
  KEY `person_id_idx` (`person_id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `person_id_user_fk` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `role_id_user_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user` VALUES (9,'owner@gmail.com','$2a$10$8WKNn4J1RbDpOWptPi6xveR2awvzfLYoPWK5Wbfwf3.KqZhyPmah2',9,5,0,NULL,'HA');
