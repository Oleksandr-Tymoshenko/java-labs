CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`),
  CONSTRAINT `roles_chk_1` CHECK ((`name` in (_utf8mb4'USER',_utf8mb4'ADMIN')))
) ;

INSERT INTO `roles` VALUES (2,'ADMIN'),(1,'USER');
