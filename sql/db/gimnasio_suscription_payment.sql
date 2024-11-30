DROP TABLE IF EXISTS `suscription_payment`;
CREATE TABLE `suscription_payment` (
  `suscription_payment_id` int NOT NULL AUTO_INCREMENT,
  `suscription_payment_amount` decimal(10,2) NOT NULL,
  `suscription_payment_date` datetime NOT NULL,
  `suscription_payment_quantity` int NOT NULL,
  `suscription_payment_quantity_time_units` varchar(45) NOT NULL,
  `user_id` int NOT NULL,
  `suscription_payment_new_expire_date` date NOT NULL,
  `suscription_payment_old_expire_date` date DEFAULT NULL,
  PRIMARY KEY (`suscription_payment_id`),
  UNIQUE KEY `suscription_payment_id_UNIQUE` (`suscription_payment_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;