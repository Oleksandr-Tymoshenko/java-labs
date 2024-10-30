CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);

INSERT INTO `users` VALUES (1,'','2024-09-26 14:14:23.985281','johndoe@example.com','John','Doe','$2a$10$faPyHjuPlFxizJW9xpI4qe1bsNUemE0sleZaYMwth2ziLOgqgXaku','',2),(5,'','2024-09-27 18:08:10.141227','user@gmail.com','user','user','$2a$10$80VpZhVx1AZKENWhoKVbuem6OjgQUR23W.d.43if.E.MvFTEyFEoa','',1),(6,'','2024-09-27 18:12:34.626687','admin@gmail.com','admin','admin','$2a$10$9qM2kiVO0qVPHhxkreLGl.nb7KC6ZQKsSOpZj/2wSMxNyVfDa/z9S','',1),(7,'','2024-10-17 13:18:53.663714','aleksandrtimosenko089@gmail.com','123','123','$2a$10$wZS3qizROIujl0L6LxMcm.LXkgzFeEnpHhlTVBfQ80ekBz4QG612i','+38034362342',1),(8,'','2024-10-17 13:39:55.067542','aleksandrtimosenko090@gmail.com','OLEKSANDR','TYMOSHENKO','$2a$10$cc3UaJu.3Cp.49nRuHW7TufOAAf8BTBJDrtqCLJm7Q7riP00QcJFi','+38345123156',1);
