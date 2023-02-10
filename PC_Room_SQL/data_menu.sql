-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: data
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `m_idx` int NOT NULL AUTO_INCREMENT,
  `m_name` varchar(45) DEFAULT NULL,
  `m_type` varchar(45) DEFAULT NULL,
  `m_stock` varchar(45) DEFAULT NULL,
  `m_price` varchar(45) DEFAULT NULL,
  `m_img` varchar(255) DEFAULT NULL,
  `m_addTime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`m_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'바나나킥','과자류','100','1500','Img\\Menu\\Snack\\바나나킥_2022_10_02_01_58_33.jpg','2022-09-30/17:03:15'),(2,'양파링','과자류','100','1500','Img\\Menu\\Snack\\양파링_2022_09_30_17_03_43.png','2022-09-30/17:03:43'),(3,'쫄병스낵 숯불바베큐 맛','과자류','100','1000','Img\\Menu\\Snack\\쫄병스낵 숯불바베큐 맛_2022_09_30_17_04_34.jpg','2022-09-30/17:04:34'),(4,'쫄병스낵 매콤한 맛','과자류','100','1000','Img\\Menu\\Snack\\쫄병스낵 매콤한 맛_2022_09_30_17_04_58.jpg','2022-09-30/17:04:58'),(5,'포스틱','과자류','100','1500','Img\\Menu\\Snack\\포스틱_2022_09_30_17_05_43.jpg','2022-09-30/17:05:43'),(6,'포테토칩 오리지널 M','과자류','100','1500','Img\\Menu\\Snack\\포테토칩 오리지널 M_2022_09_30_17_06_02.jpg','2022-09-30/17:06:02'),(7,'새우깡','과자류','100','1500','Img\\Menu\\Snack\\새우깡_2022_09_30_17_06_53.jpg','2022-09-30/17:06:26'),(8,'쌀 새우깡','과자류','100','1500','Img\\Menu\\Snack\\쌀 새우깡_2022_09_30_17_07_23.jpg','2022-09-30/17:07:23'),(9,'오징어집','과자류','100','1500','Img\\Menu\\Snack\\오징어집_2022_09_30_17_08_07.jpg','2022-09-30/17:07:43'),(10,'알새우칩','과자류','100','1500','Img\\Menu\\Snack\\알새우칩_2022_09_30_17_07_59.jpg','2022-09-30/17:07:59'),(11,'조청유과','과자류','100','1500','Img\\Menu\\Snack\\조청유과_2022_09_30_17_08_26.jpg','2022-09-30/17:08:26'),(12,'벌집핏자','과자류','100','1500','Img\\Menu\\Snack\\벌집핏자_2022_09_30_17_08_47.jpg','2022-09-30/17:08:47'),(13,'인디안밥','과자류','100','1500','Img\\Menu\\Snack\\인디안밥_2022_09_30_17_09_00.jpg','2022-09-30/17:09:00'),(14,'구운양파','과자류','100','1500','Img\\Menu\\Snack\\구운양파_2022_09_30_17_09_10.jpg','2022-09-30/17:09:10'),(15,'허니버터칩','과자류','100','1500','Img\\Menu\\Snack\\허니버터칩_2022_09_30_17_09_19.jpg','2022-09-30/17:09:19'),(16,'허니버터칩 둘세데레체','과자류','100','1500','Img\\Menu\\Snack\\허니버터칩 둘세데레체_2022_09_30_17_09_35.jpg','2022-09-30/17:09:35'),(17,'오사쯔','과자류','100','1500','Img\\Menu\\Snack\\오사쯔_2022_09_30_17_09_47.jpg','2022-09-30/17:09:47'),(18,'꽃게랑','과자류','100','1500','Img\\Menu\\Snack\\꽃게랑_2022_09_30_17_09_59.jpg','2022-09-30/17:09:59'),(19,'콘칩','과자류','100','1500','Img\\Menu\\Snack\\콘칩_2022_09_30_17_10_08.jpg','2022-09-30/17:10:08'),(20,'카라멜 땅콩','과자류','100','1500','Img\\Menu\\Snack\\카라멜 땅콩_2022_09_30_17_10_32.jpg','2022-09-30/17:10:32'),(21,'못말리는 신짱!','과자류','100','1500','Img\\Menu\\Snack\\못말리는 신짱!_2022_09_30_17_10_48.jpg','2022-09-30/17:10:48'),(22,'김치 볶음밥','밥류','100','4500','Img\\Menu\\Rice\\김치 볶음밥_2022_09_30_17_14_15.jpg','2022-09-30/17:14:15'),(23,'짜장밥','밥류','100','4500','Img\\Menu\\Rice\\짜장밥_2022_09_30_17_15_19.jpg','2022-09-30/17:15:19'),(24,'낙지덮밥','밥류','100','4500','Img\\Menu\\Rice\\낙지덮밥_2022_09_30_17_18_05.jpg','2022-09-30/17:18:05'),(25,'제육덮밥','밥류','100','4500','Img\\Menu\\Rice\\제육덮밥_2022_09_30_17_18_48.jpg','2022-09-30/17:18:48'),(26,'새우볶음밥','밥류','100','4500','Img\\Menu\\Rice\\새우볶음밥_2022_09_30_17_19_25.jpg','2022-09-30/17:19:25'),(27,'소세지 치즈볶음밥','밥류','100','4500','Img\\Menu\\Rice\\소세지 치즈볶음밥_2022_09_30_17_20_15.jpg','2022-09-30/17:20:15'),(28,'스팸 김치볶음밥','밥류','100','4500','Img\\Menu\\Rice\\스팸 김치볶음밥_2022_09_30_17_21_09.jpg','2022-09-30/17:21:09'),(29,'스팸 야채볶음밥','밥류','100','4500','Img\\Menu\\Rice\\스팸 야채볶음밥_2022_09_30_17_22_27.jpg','2022-09-30/17:22:27'),(30,'소불고기 덮밥','밥류','100','5000','Img\\Menu\\Rice\\소불고기 덮밥_2022_09_30_17_23_20.jpg','2022-09-30/17:23:20'),(31,'치킨마요 덮밥','밥류','100','4500','Img\\Menu\\Rice\\치킨마요 덮밥_2022_09_30_17_24_41.jpg','2022-09-30/17:24:41'),(32,'짜파구리','면류','100','5000','Img\\Menu\\Noodle\\짜파구리_2022_09_30_17_29_06.jpg','2022-09-30/17:29:06'),(33,'치즈불닭볶음면','면류','100','5000','Img\\Menu\\Noodle\\치즈불닭볶음면_2022_09_30_17_30_09.jpg','2022-09-30/17:30:09'),(34,'[봉지]신라면','면류','100','3500','Img\\Menu\\Noodle\\[봉지]신라면_2022_09_30_17_31_14.jpg','2022-09-30/17:31:14'),(35,'[봉지]신라면 건면','면류','100','3500','Img\\Menu\\Noodle\\[봉지]신라면 건면_2022_09_30_17_31_31.png','2022-09-30/17:31:31'),(36,'[봉지]짜파게티','면류','100','3500','Img\\Menu\\Noodle\\[봉지]짜파게티_2022_09_30_17_31_58.jpg','2022-09-30/17:31:58'),(37,'[봉지]진라면 매운맛','면류','100','3500','Img\\Menu\\Noodle\\[봉지]진라면 매운맛_2022_09_30_17_32_19.jpg','2022-09-30/17:32:19'),(38,'[봉지]안성탕면','면류','100','3500','Img\\Menu\\Noodle\\[봉지]안성탕면_2022_09_30_17_33_15.jpg','2022-09-30/17:33:15'),(39,'[봉지]불닭볶음면','면류','100','3500','Img\\Menu\\Noodle\\[봉지]불닭볶음면_2022_09_30_17_33_37.jpg','2022-09-30/17:33:37'),(40,'[봉지]너구리','면류','100','3500','Img\\Menu\\Noodle\\[봉지]너구리_2022_09_30_17_34_42.jpg','2022-09-30/17:34:42'),(41,'[봉지]스파게티','면류','100','3500','Img\\Menu\\Noodle\\[봉지]스파게티_2022_09_30_17_37_31.jpg','2022-09-30/17:35:35'),(42,'[봉지]참깨라면','면류','100','3500','Img\\Menu\\Noodle\\[봉지]참깨라면_2022_09_30_17_38_23.jpg','2022-09-30/17:38:04'),(43,'[봉지]오징어짬뽕','면류','100','3500','Img\\Menu\\Noodle\\[봉지]오징어짬뽕_2022_09_30_17_41_06.jpg','2022-09-30/17:40:11'),(44,'[봉지]비빔면','면류','100','3500','Img\\Menu\\Noodle\\[봉지]비빔면_2022_09_30_17_40_58.jpg','2022-09-30/17:40:58'),(45,'타코야끼','튀김류','100','4000','Img\\Menu\\Fried\\타코야끼_2022_09_30_17_42_25.jpg','2022-09-30/17:42:25'),(46,'피카츄 돈까스','튀김류','100','2500','Img\\Menu\\Fried\\피카츄 돈까스_2022_09_30_17_45_51.jpg','2022-09-30/17:44:23'),(47,'치킨너겟','튀김류','100','3500','Img\\Menu\\Fried\\치킨너겟_2022_09_30_17_45_44.png','2022-09-30/17:45:44'),(48,'감자튀김','튀김류','100','3000','Img\\Menu\\Fried\\감자튀김_2022_09_30_17_46_57.jpg','2022-09-30/17:46:57'),(49,'치즈볼','튀김류','100','2500','Img\\Menu\\Fried\\치즈볼_2022_09_30_17_47_21.jpg','2022-09-30/17:47:21'),(50,'오징어튀김','튀김류','100','3500','Img\\Menu\\Fried\\오징어튀김_2022_09_30_17_47_52.jpg','2022-09-30/17:47:52'),(51,'군만두','튀김류','100','3500','Img\\Menu\\Fried\\군만두_2022_09_30_17_48_22.jpg','2022-09-30/17:48:22'),(52,'새우튀김','튀김류','100','4000','Img\\Menu\\Fried\\새우튀김_2022_09_30_17_48_55.jpg','2022-09-30/17:48:55'),(53,'[뚱캔]코카콜라','음료류','100','2000','Img\\Menu\\Drink\\코카콜라 뚱캔_2022_09_30_17_54_00.jpg','2022-09-30/17:54:00'),(54,'[뚱캔]칠성사이다','음료류','100','2000','Img\\Menu\\Drink\\칠성사이다 뚱캔_2022_09_30_17_54_12.jpg','2022-09-30/17:54:12'),(55,'[뚱캔]웰치스 ','음료류','100','2000','Img\\Menu\\Drink\\웰치스 뚱캔_2022_09_30_17_54_30.jpg','2022-09-30/17:54:30'),(56,'[뚱캔]스프라이트 ','음료류','100','2000','Img\\Menu\\Drink\\스프라이트 뚱캔_2022_09_30_17_55_02.jpg','2022-09-30/17:55:02'),(57,'[뚱캔]밀키스 ','음료류','100','2000','Img\\Menu\\Drink\\밀키스 뚱캔_2022_09_30_17_55_27.jpg','2022-09-30/17:55:27'),(58,'[뚱캔]코코팜 ','음료류','100','2000','Img\\Menu\\Drink\\코코팜 뚱캔_2022_09_30_17_56_12.jpg','2022-09-30/17:56:12'),(59,'[뚱캔]봉봉 ','음료류','100','2000','Img\\Menu\\Drink\\봉봉 뚱캔_2022_09_30_17_56_38.jpg','2022-09-30/17:56:38'),(60,'[뚱캔]환타 오렌지','음료류','100','2000','Img\\Menu\\Drink\\[뚱캔]환타 오렌지_2022_09_30_17_57_05.jpg','2022-09-30/17:57:05'),(61,'[뚱캔]펩시','음료류','100','2000','Img\\Menu\\Drink\\[뚱캔]펩시_2022_09_30_17_58_43.jpg','2022-09-30/17:58:43'),(62,'[뚱캔]펩시 제로','음료류','100','2000','Img\\Menu\\Drink\\[뚱캔]펩시 제로_2022_09_30_17_58_56.jpg','2022-09-30/17:58:56'),(63,'하리보','기타','100','2000','Img\\Menu\\Other\\하리보_2022_09_30_18_01_15.jpg','2022-09-30/18:01:15'),(64,'츄파춥스','기타','100','1000','Img\\Menu\\Other\\츄파춥스_2022_09_30_18_02_48.jpg','2022-09-30/18:02:08'),(65,'가나초콜릿','기타','100','1500','Img\\Menu\\Other\\가나초콜릿_2022_10_01_03_08_00.jpg','2022-09-30/18:02:39'),(66,'목캔디','기타','100','1500','Img\\Menu\\Other\\목캔디_2022_09_30_18_03_17.jpg','2022-09-30/18:03:17'),(67,'[랜덤]알바생의 마음','기타','100','1500','Img\\Menu\\Other\\[랜덤]알바생의 마음_2022_09_30_18_04_45.jpg','2022-09-30/18:04:17');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-02  1:59:00