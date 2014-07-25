CREATE DATABASE  IF NOT EXISTS `institute` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `institute`;
-- MySQL dump 10.13  Distrib 5.6.13, for linux-glibc2.5 (i686)
--
-- Host: 127.0.0.1    Database: institute
-- ------------------------------------------------------
-- Server version	5.5.38

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cnotes` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'فوتوشوب','وقت البدء شهر 10'),(2,'ICDL','العدد اصبح 5 طلاب'),(3,'Android Programming',NULL),(4,'الرياضيات للصف التاسع',NULL),(5,'اللفة العربية للبكالوريا',NULL),(6,'اللغة الانكليزية للكالوريا',NULL),(7,'الكيمياء للتاسع',NULL),(8,'اللغة العربية للتاسع','تبدأ في 5-8-2014');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `spassword` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'1','1');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitor`
--

DROP TABLE IF EXISTS `visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visitor` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `vcid` int(11) NOT NULL,
  `vfname` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vlname` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vmobile` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vtelephone` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vemail` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vnotes` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`vid`),
  KEY `fk_visitor_contact_1_idx` (`vcid`),
  CONSTRAINT `fk_visitor_1` FOREIGN KEY (`vcid`) REFERENCES `course` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor`
--

LOCK TABLES `visitor` WRITE;
/*!40000 ALTER TABLE `visitor` DISABLE KEYS */;
INSERT INTO `visitor` VALUES (1,2,'محمد','sofan','','','',NULL),(2,4,'ali','al ali','96396548','0213354','test@example.com','test'),(3,3,'mohannd','labbabidy','1234','123','123','123'),(4,6,'frirst','last','mobile-no','phone-no-2','',''),(5,2,'ملهم','الملهم','mobile','phone','email','notes'),(18,5,'احمد','الاحمد','test-mobile','test-phone','test-email','test-notes'),(19,3,'محمد','حريتاني','000000000','00000000','m@m.com','notes-1'),(20,1,'محمد','المحمد','00000','0000000','0000','0000');
/*!40000 ALTER TABLE `visitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'institute'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-23  0:31:53
