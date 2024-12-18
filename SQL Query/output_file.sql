-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: calouself
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `itemId` varchar(255) NOT NULL,
  `itemName` varchar(255) NOT NULL,
  `itemSize` varchar(50) DEFAULT NULL,
  `itemPrice` decimal(10,2) NOT NULL,
  `itemCategory` varchar(100) DEFAULT NULL,
  `itemStatus` varchar(50) NOT NULL,
  `itemWishlist` tinyint(1) DEFAULT '0',
  `itemOfferStatus` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('IT001','Laptop','15.6 inch',899.99,'Electronics','Sold',0,NULL),('IT002','Smartphone','6.5 inch',499.99,'Electronics','Sold',0,NULL),('IT003','T-Shirt','M',19.99,'Clothing','Sold',0,NULL),('IT004','Jeans','32',39.99,'Clothing','Sold',0,NULL),('IT005','Sneakers','9',79.99,'Shoes','Sold',0,NULL),('IT0ca2633b','Gunting','Kecil',12000.00,'Home Appliances','Sold',0,'No Offer'),('IT13fe029a','Batu123','12 kgs',1300.00,'Clothing','Sold',0,'No Offer'),('IT1b72e228','Kertasu','13',12.00,'Home Appliances','Sold',0,'No Offer'),('IT3758d7fa','batu','50 kg',1300.00,'Books','Sold',0,'No Offer'),('IT4818df39','Batu','12 kg',130.00,'Clothing','Approved',0,'No Offer'),('IT4e109fbe','123','123',123.00,'Clothing','Sold',0,'No Offer'),('IT8f4fdfc4','GuntingKertasBatu','12 inch',2000.00,'Home Appliances','Available',0,'No Offer'),('ITb148c4bb','Kertas','12 lembar',2400.00,'Home Appliances','Available',0,'No Offer'),('ITc0f04996','Taffware Numpad','2.5 Inch',55000.00,'Electronics','Sold',0,'No Offer'),('ITf51472e4','Kertas','13',2000.00,'Books','Sold',0,'No Offer');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offers` (
  `offerId` int NOT NULL AUTO_INCREMENT,
  `itemId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `offerPrice` double NOT NULL,
  `status` varchar(10) DEFAULT 'Pending',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`offerId`),
  KEY `itemId` (`itemId`),
  KEY `userId` (`userId`),
  CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `item` (`itemId`) ON DELETE CASCADE,
  CONSTRAINT `offers_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES (1,'IT4818df39','US771',20,'Accepted','2024-12-15 09:03:33'),(2,'IT8f4fdfc4','US771',2000,'Accepted','2024-12-16 10:25:22'),(3,'ITc0f04996','US771',55000,'Accepted','2024-12-16 10:33:51'),(4,'ITf51472e4','US771',2000,'Accepted','2024-12-17 16:06:40');
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selleritems`
--

DROP TABLE IF EXISTS `selleritems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `selleritems` (
  `userId` varchar(255) NOT NULL,
  `itemId` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`,`itemId`),
  KEY `itemId` (`itemId`),
  CONSTRAINT `selleritems_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE,
  CONSTRAINT `selleritems_ibfk_2` FOREIGN KEY (`itemId`) REFERENCES `item` (`itemId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selleritems`
--

LOCK TABLES `selleritems` WRITE;
/*!40000 ALTER TABLE `selleritems` DISABLE KEYS */;
INSERT INTO `selleritems` VALUES ('US881','IT0ca2633b'),('US881','IT13fe029a'),('US881','IT1b72e228'),('US881','IT3758d7fa'),('US881','IT4818df39'),('US881','IT4e109fbe'),('US881','IT8f4fdfc4'),('US881','ITb148c4bb'),('US881','ITc0f04996'),('US881','ITf51472e4');
/*!40000 ALTER TABLE `selleritems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transactionId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `itemId` varchar(255) NOT NULL,
  PRIMARY KEY (`transactionId`),
  KEY `userId` (`userId`),
  KEY `itemId` (`itemId`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`itemId`) REFERENCES `item` (`itemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES ('TR00A61AD9','US771','IT1b72e228'),('TR16314EB1','US771','IT002'),('TR27506EF4','US771','IT004'),('TR346ACBCF','US771','IT13fe029a'),('TR428187BD','US771','IT001'),('TR5C102C18','US771','IT003'),('TR5C80C7A0','US771','IT4e109fbe'),('TR5FB9DA59','US771','ITc0f04996'),('TR69248FD8','US771','IT001'),('TR734110EF','US771','ITc0f04996'),('TR786A36C7','US771','ITf51472e4'),('TR7BBD1DCA','US771','IT001'),('TR94CF0040','US771','IT005'),('TRBF13BD37','US771','IT0ca2633b'),('TRD759DA09','US771','IT8f4fdfc4'),('TRE4F0A238','US771','IT3758d7fa'),('TREAFB12E6','US771','IT4818df39');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userId` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('US771','buyer1','buyerpass123','+621234567890','123 Main St','buyer'),('US772','buyer2','buyerpass456','+629876543210','456 Elm St','buyer'),('US773','buyer3','buyerpass789','+620123456789','789 Oak St','buyer'),('US881','seller1','sellerpass123','+621234567890','123 Main St','seller'),('US882','seller2','sellerpass456','+629876543210','456 Elm St','seller'),('US883','seller3','sellerpass789','+620123456789','789 Oak St','seller'),('US991','admin1','password123','+621234567890','123 Main St','admin'),('US992','admin2','password456','+629876543210','456 Elm St','admin'),('US993','admin3','password789','+620123456789','789 Oak St','admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `wishlistID` varchar(255) NOT NULL,
  `itemId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  PRIMARY KEY (`wishlistID`),
  KEY `itemId` (`itemId`),
  KEY `userId` (`userId`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`itemId`) REFERENCES `item` (`itemId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES ('WL-1734451417830','ITf51472e4','US771');
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-18 14:10:00
