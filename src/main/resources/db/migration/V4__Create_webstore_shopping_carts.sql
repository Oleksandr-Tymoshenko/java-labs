CREATE TABLE IF NOT EXISTS `shopping_carts` (
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK3iw2988ea60alsp0gnvvyt744` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

INSERT INTO `shopping_carts` VALUES (1);
