CREATE DATABASE  IF NOT EXISTS `nbad_proj` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `nbad_proj`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: nbad_proj
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `StudyID` int(11) NOT NULL,
  `QuestionID` int(11) NOT NULL,
  `UserName` varchar(40) NOT NULL,
  `Choice` varchar(40) DEFAULT NULL,
  `DateSubmitted` datetime DEFAULT NULL,
  PRIMARY KEY (`StudyID`,`QuestionID`,`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (9,4,'nbhirud@gmail.com','bdfb','2016-04-17 00:00:00'),(9,4,'nikhil@gmail.com','bdfb','2016-04-03 00:00:00'),(10,5,'nbhirud@gmail.com','kknlnkn','2016-04-17 00:00:00'),(10,5,'nikhil@gmail.com','jb,j','2016-04-03 00:00:00'),(11,6,'nbhirud@gmail.com','lkkl','2016-04-17 00:00:00'),(12,7,'sravan1707@gmail.com','somewhat','2016-04-03 00:00:00');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `QuestionID` int(11) NOT NULL AUTO_INCREMENT,
  `StudyID` int(11) DEFAULT NULL,
  `Question` varchar(50) DEFAULT NULL,
  `AnswerType` varchar(10) DEFAULT NULL,
  `Option1` varchar(40) DEFAULT NULL,
  `Option2` varchar(40) DEFAULT NULL,
  `Option3` varchar(40) DEFAULT NULL,
  `Option4` varchar(40) DEFAULT NULL,
  `Option5` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`QuestionID`),
  KEY `StudyID` (`StudyID`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`StudyID`) REFERENCES `study` (`StudyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (4,9,'Sravan1','Text','bdfb','sfbfb','gngfhn','',''),(5,10,'Chaitanya2','Text','jb,j','kknlnkn','nklnlknlknlk','',''),(6,11,'Sai1','Text','khj','hlklk','lkkl','',''),(7,12,'Do you use FB','Text','alwasy','never','somewhat','',''),(8,13,'Twitter','Text','gsgds','dffdb','fsbfsdb','',''),(9,14,'Sravan','Text','sfbdfnkk','lkbkn','ksnbkn','','');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reported`
--

DROP TABLE IF EXISTS `reported`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reported` (
  `QuestionID` int(11) NOT NULL,
  `StudyID` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL,
  `NumParticipants` int(15) DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `UserName` varchar(50) NOT NULL,
  PRIMARY KEY (`StudyID`,`QuestionID`,`UserName`),
  KEY `QuestionID` (`QuestionID`),
  CONSTRAINT `reported_ibfk_1` FOREIGN KEY (`QuestionID`) REFERENCES `question` (`QuestionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reported_ibfk_2` FOREIGN KEY (`StudyID`) REFERENCES `study` (`StudyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reported`
--

LOCK TABLES `reported` WRITE;
/*!40000 ALTER TABLE `reported` DISABLE KEYS */;
INSERT INTO `reported` VALUES (7,12,'2016-04-03 00:00:00',0,'Approved','sravan1707@gmail.com');
/*!40000 ALTER TABLE `reported` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study`
--

DROP TABLE IF EXISTS `study`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `study` (
  `StudyID` int(11) NOT NULL AUTO_INCREMENT,
  `StudyName` varchar(40) DEFAULT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `DateCreated` datetime DEFAULT NULL,
  `ImageURL` varchar(50) DEFAULT NULL,
  `ReqParticipants` int(15) DEFAULT NULL,
  `ActParticipants` int(15) DEFAULT NULL,
  `SStatus` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`StudyID`),
  KEY `Username` (`Username`),
  CONSTRAINT `study_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `user` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study`
--

LOCK TABLES `study` WRITE;
/*!40000 ALTER TABLE `study` DISABLE KEYS */;
INSERT INTO `study` VALUES (9,'Sravan','gss','sravan1707@gmail.com',NULL,'visited.png',3,3,'Start'),(10,'Sravan2','nlknklnk','sravan1707@gmail.com',NULL,'bronze.png',4,4,'Start'),(11,'Nikhil','lk','nbhirud@uncc.edu',NULL,'unvisited.png',3,3,'Start'),(12,'Regarding Facebook','FB Study','nikhil@gmail.com',NULL,'logo.png',16,0,'Start'),(13,'Twitter Study','xbdsbvdx','nikhil@gmail.com',NULL,'silver.png',3,0,'Start'),(14,'Sravan','knlknsk','sravan1707@gmail.com',NULL,'unvisited.png',4,0,'Start');
/*!40000 ALTER TABLE `study` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tempuser`
--

DROP TABLE IF EXISTS `tempuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tempuser` (
  `UName` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(500) DEFAULT NULL,
  `IssueDate` datetime DEFAULT NULL,
  `Token` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tempuser`
--

LOCK TABLES `tempuser` WRITE;
/*!40000 ALTER TABLE `tempuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `tempuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `Name` varchar(50) DEFAULT NULL,
  `Username` varchar(50) NOT NULL DEFAULT '',
  `Password` varchar(500) NOT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Studies` int(15) DEFAULT NULL,
  `Participation` int(15) DEFAULT NULL,
  `Coins` int(15) DEFAULT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('Anudeesh Puppala','anudeesh@gmail.com','puppala','Administrator',0,0,0),('Nikhil Bhirud','nbhirud@gmail.com','dd3003ac7b7f1c82144f938956743191bb4e3a0e5af28dc2a5dbc4257c98b29a','Administrator',0,3,3),('n','nbhirud@uncc.edu','dd3003ac7b7f1c82144f938956743191bb4e3a0e5af28dc2a5dbc4257c98b29a','Participant',0,0,0),('Nikhil Sai','nikhil@gmail.com','nikhil','Participant',0,2,4),('Sravan Chaitanya','sravan1707@gmail.com','gesture','Participant',0,1,5),('Sravan Chaitanya','sravankurivella@gmail.com','2984d69af8499bfe49f09797ac16157a1eb213435125042bbf99b58820d806a5','Participant',0,0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'nbad_proj'
--

--
-- Dumping routines for database 'nbad_proj'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-17 18:09:14
