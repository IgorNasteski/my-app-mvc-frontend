-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: front_back_api_db
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `token_user`
--

DROP TABLE IF EXISTS `token_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token_user` (
  `user_id` int NOT NULL,
  `token` varchar(170) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token_user`
--

LOCK TABLES `token_user` WRITE;
/*!40000 ALTER TABLE `token_user` DISABLE KEYS */;
INSERT INTO `token_user` VALUES (6,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcm9kbyIsImV4cCI6MTYxMDE5Mjg5NSwiaWF0IjoxNjEwMTU2ODk1fQ.R8y37qfLbMaNgwerNKNyEB0mByccBkTyzNX54MjQKXE'),(8,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRldG9rdW1wbyIsImV4cCI6MTYxMDE5Njg1MiwiaWF0IjoxNjEwMTYwODUyfQ.vu5dYLkS_neDqBg6Fp9cTMlAJb6ShNdvi9Urmgr1gDc'),(9,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkd2lnaHQ4NyIsImV4cCI6MTYxMDE5NDgwOCwiaWF0IjoxNjEwMTU4ODA4fQ.d7UPzT6dGdjfh_m03zxHjfxs9YBhlAnAHTF08Z7QTqc'),(11,''),(13,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9iYTEiLCJleHAiOjE2MTAyNTA3NTgsImlhdCI6MTYxMDIxNDc1OH0.RUXFS9fDnemNfNXMyE6K2euLzlM9tvCnfC3mMfP0goQ');
/*!40000 ALTER TABLE `token_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(170) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (6,'frodo','$2a$10$8B.ob8m8aQ9zTZSEYm44peT.ArH7asxXrHRbH4MKe4c8INC2FwuTu','frodo@gmail.com'),(8,'antetokumpo','$2a$10$vi/wMb4YFxeuIpCsc52OZ.iX2uRL1j.q0i3OX5yy29xSy8WHEpS2C','janis@gmail.com'),(9,'dwight87','$2a$10$D0FuKP82SZe1AZFPz5AwhuKqkNNOClUfn1q8QMcRIaEo6cc.UFsze','dwight.howard87@gmail.com'),(11,'proba2','$2a$10$LCKcFOXAz9sEad3PRadKWeFDT6YZ2JWrljXVymMRU5iYUlFfOFSWK','proba2@gmail.com'),(13,'proba1','$2a$10$WN/0pQpIX2D.a9TDGCyk7ezoTePMdbHQwre8z4WFhV07eJH8VNstS','proba1@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-09 18:53:49
