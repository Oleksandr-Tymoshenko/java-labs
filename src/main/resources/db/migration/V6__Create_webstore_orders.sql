CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_deleted` bit(1) NOT NULL,
  `order_date` datetime(6) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `shipping_address` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `total` decimal(38,2) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `orders_chk_1` CHECK ((`status` in (_utf8mb4'PENDING',_utf8mb4'PROCESSING',_utf8mb4'SHIPPED',_utf8mb4'DELIVERED',_utf8mb4'CANCELLED')))
);

INSERT INTO `orders` VALUES (1,_binary '\0','2024-10-30 00:27:25.884328','+380997178421','address','PENDING',1159.88,7),(2,_binary '\0','2024-10-30 00:34:39.098051','+3802339173792','address','PENDING',240.00,7);
