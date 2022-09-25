CREATE DATABASE  IF NOT EXISTS `redsoildb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `redsoildb`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: redsoildb
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `blooddonationtestingdetails`
--

DROP TABLE IF EXISTS `blooddonationtestingdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blooddonationtestingdetails` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `Donor_ID` int unsigned NOT NULL,
  `Previously_Donated` tinyint(1) DEFAULT NULL,
  `Previously_Donated_Date` date DEFAULT NULL,
  `Diseases` varchar(512) DEFAULT NULL,
  `Weight` varchar(128) DEFAULT NULL,
  `BP` varchar(128) DEFAULT NULL,
  `HB` varchar(128) DEFAULT NULL,
  `Resp_Sys` varchar(128) DEFAULT NULL,
  `Cvs` varchar(128) DEFAULT NULL,
  `Gi_System` varchar(128) DEFAULT NULL,
  `Other` varchar(128) DEFAULT NULL,
  `Fit` varchar(128) DEFAULT NULL,
  `Unit` varchar(128) DEFAULT NULL,
  `ABO` varchar(128) DEFAULT NULL,
  `RH` varchar(128) DEFAULT NULL,
  `HIV` varchar(128) DEFAULT NULL,
  `HBsAg` varchar(128) DEFAULT NULL,
  `HCV` varchar(128) DEFAULT NULL,
  `VDRL` varchar(128) DEFAULT NULL,
  `Expiry_date` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Donor_ID` (`Donor_ID`),
  CONSTRAINT `blooddonationtestingdetails_ibfk_1` FOREIGN KEY (`Donor_ID`) REFERENCES `blooddonationuserdata` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blooddonationtestingdetails`
--

LOCK TABLES `blooddonationtestingdetails` WRITE;
/*!40000 ALTER TABLE `blooddonationtestingdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `blooddonationtestingdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blooddonationuserdata`
--

DROP TABLE IF EXISTS `blooddonationuserdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blooddonationuserdata` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `Blood_Donation_Orgnization` varchar(200) DEFAULT NULL,
  `Donor_Name` varchar(50) NOT NULL,
  `Gender` varchar(8) DEFAULT NULL,
  `Age` tinyint unsigned DEFAULT NULL,
  `Occupation` varchar(100) DEFAULT NULL,
  `Address` varchar(128) DEFAULT NULL,
  `Phone` varchar(10) DEFAULT NULL,
  `Email` varchar(320) DEFAULT NULL,
  `Patient_Name` varchar(50) NOT NULL,
  `Donor_ID` varchar(100) NOT NULL,
  `Date_Of_Creation` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Donor_ID` (`Donor_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blooddonationuserdata`
--

LOCK TABLES `blooddonationuserdata` WRITE;
/*!40000 ALTER TABLE `blooddonationuserdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `blooddonationuserdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `bloodstautstotal`
--

DROP TABLE IF EXISTS `bloodstautstotal`;
/*!50001 DROP VIEW IF EXISTS `bloodstautstotal`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `bloodstautstotal` AS SELECT 
 1 AS `count(Donor_ID)`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `bloodtypestotal`
--

DROP TABLE IF EXISTS `bloodtypestotal`;
/*!50001 DROP VIEW IF EXISTS `bloodtypestotal`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `bloodtypestotal` AS SELECT 
 1 AS `count(ABO)`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Company_Name` varchar(1024) NOT NULL,
  `Company_Address` text NOT NULL,
  `Company_Phone` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'Haitomns Blood Bank','Knowhere','9800000000');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `company_id` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `login_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `company` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,1,'haitomns','12345');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `removeblooddetails`
--

DROP TABLE IF EXISTS `removeblooddetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `removeblooddetails` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `Blood_Donation_Orgnization` varchar(200) DEFAULT NULL,
  `Donor_Name` varchar(50) NOT NULL,
  `Gender` varchar(8) DEFAULT NULL,
  `Age` tinyint unsigned DEFAULT NULL,
  `Occupation` varchar(100) DEFAULT NULL,
  `Address` varchar(128) DEFAULT NULL,
  `Phone` varchar(10) DEFAULT NULL,
  `Email` varchar(320) DEFAULT NULL,
  `Patient_Name` varchar(50) DEFAULT NULL,
  `Donor_ID` varchar(100) DEFAULT NULL,
  `Date_Of_Creation` date DEFAULT NULL,
  `Previously_Donated` tinyint(1) DEFAULT NULL,
  `Previously_Donated_Date` date DEFAULT NULL,
  `Diseases` varchar(512) DEFAULT NULL,
  `Weight` varchar(128) DEFAULT NULL,
  `BP` varchar(128) DEFAULT NULL,
  `HB` varchar(128) DEFAULT NULL,
  `Resp_Sys` varchar(128) DEFAULT NULL,
  `Cvs` varchar(128) DEFAULT NULL,
  `Gi_System` varchar(128) DEFAULT NULL,
  `Other` varchar(128) DEFAULT NULL,
  `Fit` varchar(128) DEFAULT NULL,
  `Unit` varchar(128) DEFAULT NULL,
  `ABO` varchar(128) DEFAULT NULL,
  `RH` varchar(128) DEFAULT NULL,
  `HIV` varchar(128) DEFAULT NULL,
  `HBsAg` varchar(128) DEFAULT NULL,
  `HCV` varchar(128) DEFAULT NULL,
  `VDRL` varchar(128) DEFAULT NULL,
  `Expiry_date` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `removeblooddetails`
--

LOCK TABLES `removeblooddetails` WRITE;
/*!40000 ALTER TABLE `removeblooddetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `removeblooddetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'redsoildb'
--

--
-- Dumping routines for database 'redsoildb'
--
/*!50003 DROP PROCEDURE IF EXISTS `removeBlood` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeBlood`(DonorIdToDelete varchar(100))
BEGIN
	INSERT INTO removeblooddetails (`ID`,`Blood_Donation_Orgnization`,`Donor_Name`,`Gender`,`Age`,`Occupation`,`Address`,`Phone`,`Email`,`Patient_Name`,`Donor_ID`,`Date_Of_Creation`,`Previously_Donated`,`Previously_Donated_Date`,`Diseases`,`Weight`,`BP`,`HB`,`Resp_Sys`,`Cvs`,`Gi_System`,`Other`,`Fit`,`Unit`,`ABO`,`RH`,`HIV`,`HBsAg`,`HCV`,`VDRL`,`Expiry_date`)
	SELECT blooddonationuserdata.Donor_ID,`Blood_Donation_Orgnization`,`Donor_Name`,`Gender`,`Age`,`Occupation`,`Address`,`Phone`,`Email`,`Patient_Name`,blooddonationuserdata.Donor_ID,`Date_Of_Creation`,`Previously_Donated`,`Previously_Donated_Date`,`Diseases`,`Weight`,`BP`,`HB`,`Resp_Sys`,`Cvs`,`Gi_System`,`Other`,`Fit`,`Unit`,`ABO`,`RH`,`HIV`,`HBsAg`,`HCV`,`VDRL`,`Expiry_date`
	FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  
	ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID
	where blooddonationuserdata.Donor_ID = DonorIdToDelete;
    
    delete from blooddonationtestingdetails where Donor_ID = 
    (select ID from blooddonationuserdata where Donor_ID = DonorIdToDelete);
    delete from blooddonationuserdata where Donor_ID = DonorIdToDelete;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `bloodstautstotal`
--

/*!50001 DROP VIEW IF EXISTS `bloodstautstotal`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `bloodstautstotal` AS select count(`blooddonationuserdata`.`Donor_ID`) AS `count(Donor_ID)` from `blooddonationuserdata` union all select count(`blooddonationtestingdetails`.`Donor_ID`) AS `count(Donor_ID)` from `blooddonationtestingdetails` where (`blooddonationtestingdetails`.`Expiry_date` > curdate()) union all select count(`blooddonationtestingdetails`.`Donor_ID`) AS `count(Donor_ID)` from `blooddonationtestingdetails` where (`blooddonationtestingdetails`.`Expiry_date` < curdate()) union all select sum(`blooddonationtestingdetails`.`Unit`) AS `sum(Unit)` from `blooddonationtestingdetails` where (`blooddonationtestingdetails`.`Expiry_date` > curdate()) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `bloodtypestotal`
--

/*!50001 DROP VIEW IF EXISTS `bloodtypestotal`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `bloodtypestotal` AS select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'A') and (`blooddonationtestingdetails`.`RH` = '+')) union all select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'A') and (`blooddonationtestingdetails`.`RH` = '-')) union all select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'B') and (`blooddonationtestingdetails`.`RH` = '+')) union all select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'B') and (`blooddonationtestingdetails`.`RH` = '-')) union all select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'AB') and (`blooddonationtestingdetails`.`RH` = '+')) union all select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'AB') and (`blooddonationtestingdetails`.`RH` = '-')) union all select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'O') and (`blooddonationtestingdetails`.`RH` = '+')) union all select count(`blooddonationtestingdetails`.`ABO`) AS `count(ABO)` from `blooddonationtestingdetails` where ((`blooddonationtestingdetails`.`ABO` = 'O') and (`blooddonationtestingdetails`.`RH` = '-')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-25  8:10:39
