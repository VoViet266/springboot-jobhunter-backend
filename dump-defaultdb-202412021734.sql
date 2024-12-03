-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: mysql-5a194d8-voviet266-ddc6.d.aivencloud.com    Database: defaultdb
-- ------------------------------------------------------
-- Server version	8.0.30
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */
;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */
;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */
;
/*!50503 SET NAMES utf8mb4 */
;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */
;
/*!40103 SET TIME_ZONE='+00:00' */
;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */
;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */
;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */
;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */
;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN = 0;
--
-- GTID state at the beginning of the backup 
--
SET @@GLOBAL.GTID_PURGED =
  /*!80000 '+'*/
  '5969eb53-6f8a-11ef-b53d-0a4385795eeb:1-15,
5b717b19-6f8c-11ef-a954-4a67b6b42909:1-14256,
9d0b0980-2d7f-11ef-813f-22f9b6e00470:1-56';
--
-- Table structure for table `companies`
--
DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `companies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` mediumtext,
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `companies`
--
LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */
;
INSERT INTO `companies`
VALUES (
    1,
    ' Viettel ',
    '123 Main Street',
    '<p>This is a detailed description of the company.</p>',
    '1733115708671_viettel-logo.png',
    '2024-11-18 11:34:41.204457',
    NULL,
    'voviet266@gmail.com',
    'voviet266@gmail.com'
  ),
(
    2,
    ' VinGroup ',
    '123 Main Street',
    '<p>This is a detailed description of the company.</p>',
    '1733115794900_vin-group-1658220639.png',
    '2024-11-18 11:35:55.078619',
    NULL,
    'voviet266@gmail.com',
    'voviet266@gmail.com'
  ),
(
    3,
    'FPT',
    'đường Nguyễn Thị Tạo, 113D/5 kv.Bình Nhựt, P.Long Hòa - Q.Bình Thủy',
    '<p><br></p>',
    '1733115827253_logo-fpt-software_043151683.png',
    '2024-11-27 17:29:14.624864',
    NULL,
    'voviet266@gmail.com',
    'voviet266@gmail.com'
  );
/*!40000 ALTER TABLE `companies` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `conversations`
--
DROP TABLE IF EXISTS `conversations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `conversations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_creator` bigint DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt2ie881jfhxdqsksyy4kpyjse` (`id_creator`),
  CONSTRAINT `FKt2ie881jfhxdqsksyy4kpyjse` FOREIGN KEY (`id_creator`) REFERENCES `users` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `conversations`
--
LOCK TABLES `conversations` WRITE;
/*!40000 ALTER TABLE `conversations` DISABLE KEYS */
;
/*!40000 ALTER TABLE `conversations` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `job_skill`
--
DROP TABLE IF EXISTS `job_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `job_skill` (
  `job_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`job_id`, `skill_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `job_skill_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`),
  CONSTRAINT `job_skill_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `job_skill`
--
LOCK TABLES `job_skill` WRITE;
/*!40000 ALTER TABLE `job_skill` DISABLE KEYS */
;
INSERT INTO `job_skill`
VALUES (1, 3);
/*!40000 ALTER TABLE `job_skill` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `jobs`
--
DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `jobs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `actived` bit(1) NOT NULL,
  `quantity` int NOT NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `description` mediumtext,
  `company_id` bigint DEFAULT NULL,
  `lever` enum('JUNIOR', 'MIDDLE', 'SENIOR') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrtmqcrktb6s7xq8djbs2a2war` (`company_id`),
  CONSTRAINT `FKrtmqcrktb6s7xq8djbs2a2war` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `jobs`
--
LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */
;
INSERT INTO `jobs`
VALUES (
    1,
    'Software Engineer',
    _binary '\0',
    5,
    'HOCHIMINH',
    90000,
    'This is a detailed job description...',
    2,
    NULL,
    '2024-11-18 11:39:48.330711',
    '2024-12-10 08:00:00.000000',
    '2024-11-10 08:00:00.000000',
    '2024-11-27 17:41:04.641159',
    'voviet266@gmail.com',
    'voviet266@gmail.com'
  ),
(
    2,
    'Software Engineer',
    _binary '',
    5,
    'New York',
    90000,
    'This is a detailed job description...',
    1,
    'MIDDLE',
    '2024-11-18 11:40:57.445983',
    '2024-12-10 08:00:00.000000',
    '2024-11-10 08:00:00.000000',
    NULL,
    'voviet266@gmail.com',
    NULL
  );
/*!40000 ALTER TABLE `jobs` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `permission_role`
--
DROP TABLE IF EXISTS `permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `permission_role` (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`),
  UNIQUE KEY `UKt49nxpqax9cveurs8f61sns2d` (`role_id`, `permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `permission_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `permission_role_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `permission_role`
--
LOCK TABLES `permission_role` WRITE;
/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */
;
INSERT INTO `permission_role`
VALUES (5, 7),
(6, 7),
(5, 8),
(6, 8),
(5, 9),
(5, 10),
(6, 10),
(5, 11),
(6, 11),
(5, 12),
(6, 12),
(5, 14),
(5, 15),
(5, 16),
(5, 17),
(5, 18),
(5, 19),
(5, 20),
(5, 21),
(5, 22),
(5, 23),
(5, 24),
(5, 25),
(5, 26),
(5, 27),
(5, 28),
(5, 29),
(5, 30),
(5, 31);
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `permissions`
--
DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `permissions` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `updated_at` datetime(6) DEFAULT NULL,
  `api_path` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 32 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `permissions`
--
LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */
;
INSERT INTO `permissions`
VALUES (
    '2024-11-29 14:48:45.035022',
    7,
    NULL,
    '/api/v1/permissions',
    'voviet266@gmail.com',
    'GET',
    'PERMISSIONS',
    'View Permissions',
    NULL
  ),
(
    '2024-11-29 14:48:55.545964',
    8,
    NULL,
    '/api/v1/companies',
    'voviet266@gmail.com',
    'GET',
    'COMPANIES',
    'View Companies',
    NULL
  ),
(
    '2024-11-29 14:49:01.886841',
    9,
    NULL,
    '/api/v1/users',
    'voviet266@gmail.com',
    'GET',
    'USERS',
    'View Users',
    NULL
  ),
(
    '2024-11-29 14:49:08.987746',
    10,
    NULL,
    '/api/v1/jobs',
    'voviet266@gmail.com',
    'GET',
    'JOBS',
    'View Jobs',
    NULL
  ),
(
    '2024-11-29 14:49:15.631665',
    11,
    NULL,
    '/api/v1/resumes',
    'voviet266@gmail.com',
    'GET',
    'RESUMES',
    'View Resumes',
    NULL
  ),
(
    '2024-11-29 14:49:21.919679',
    12,
    NULL,
    '/api/v1/roles',
    'voviet266@gmail.com',
    'GET',
    'ROLES',
    'View Roles',
    NULL
  ),
(
    '2024-11-29 15:49:01.899508',
    14,
    NULL,
    '/api/v1/users',
    'voviet266@gmail.com',
    'POST',
    'USERS',
    'Create User',
    NULL
  ),
(
    '2024-11-29 15:49:10.259192',
    15,
    NULL,
    '/api/v1/users',
    'voviet266@gmail.com',
    'PUT',
    'USERS',
    'Edit User',
    NULL
  ),
(
    '2024-11-29 15:49:20.498561',
    16,
    NULL,
    '/api/v1/users/{id}',
    'voviet266@gmail.com',
    'DELETE',
    'USERS',
    'Delete User',
    NULL
  ),
(
    '2024-11-29 15:54:34.593610',
    17,
    NULL,
    '/api/v1/roles',
    'voviet266@gmail.com',
    'POST',
    'ROLES',
    'Create Role',
    NULL
  ),
(
    '2024-11-29 15:54:44.503210',
    18,
    NULL,
    '/api/v1/roles',
    'voviet266@gmail.com',
    'PUT',
    'ROLES',
    'Edit Role',
    NULL
  ),
(
    '2024-11-29 15:54:52.310847',
    19,
    NULL,
    '/api/v1/roles/{id}',
    'voviet266@gmail.com',
    'DELETE',
    'ROLES',
    'Delete Role',
    NULL
  ),
(
    '2024-11-29 15:55:09.502023',
    20,
    NULL,
    '/api/v1/jobs',
    'voviet266@gmail.com',
    'POST',
    'JOBS',
    'Create Job',
    NULL
  ),
(
    '2024-11-29 15:55:16.933602',
    21,
    NULL,
    '/api/v1/jobs',
    'voviet266@gmail.com',
    'PUT',
    'JOBS',
    'Edit Job',
    NULL
  ),
(
    '2024-11-29 15:55:23.957785',
    22,
    NULL,
    '/api/v1/jobs/{id}',
    'voviet266@gmail.com',
    'DELETE',
    'JOBS',
    'Delete Job',
    NULL
  ),
(
    '2024-11-29 15:55:31.937948',
    23,
    NULL,
    '/api/v1/companies',
    'voviet266@gmail.com',
    'POST',
    'COMPANIES',
    'Create Company',
    NULL
  ),
(
    '2024-11-29 15:55:38.588639',
    24,
    NULL,
    '/api/v1/companies',
    'voviet266@gmail.com',
    'PUT',
    'COMPANIES',
    'Edit Company',
    NULL
  ),
(
    '2024-11-29 15:55:45.707970',
    25,
    NULL,
    '/api/v1/companies/{id}',
    'voviet266@gmail.com',
    'DELETE',
    'COMPANIES',
    'Delete Company',
    NULL
  ),
(
    '2024-11-29 15:55:54.096084',
    26,
    NULL,
    '/api/v1/resumes',
    'voviet266@gmail.com',
    'POST',
    'RESUMES',
    'Create Resume',
    NULL
  ),
(
    '2024-11-29 15:56:26.007612',
    27,
    NULL,
    '/api/v1/resumes/{id}',
    'voviet266@gmail.com',
    'PUT',
    'RESUMES',
    'Edit Resume',
    NULL
  ),
(
    '2024-11-29 15:56:33.147906',
    28,
    NULL,
    '/api/v1/resumes/{id}',
    'voviet266@gmail.com',
    'DELETE',
    'RESUMES',
    'Delete Resume',
    NULL
  ),
(
    '2024-11-29 15:56:45.383275',
    29,
    NULL,
    '/api/v1/permissions',
    'voviet266@gmail.com',
    'POST',
    'PERMISSIONS',
    'Create Permission',
    NULL
  ),
(
    '2024-11-29 15:56:52.628609',
    30,
    NULL,
    '/api/v1/permissions',
    'voviet266@gmail.com',
    'PUT',
    'PERMISSIONS',
    'Edit Permission',
    NULL
  ),
(
    '2024-11-29 15:56:59.405667',
    31,
    NULL,
    '/api/v1/permissions/{id}',
    'voviet266@gmail.com',
    'DELETE',
    'PERMISSIONS',
    'Delete Permission',
    NULL
  );
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `resumes`
--
DROP TABLE IF EXISTS `resumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `resumes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `job_id` bigint DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `resumes_id` bigint DEFAULT NULL,
  `resumes11_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjdec9qbp2blbpag6obwf0fmbd` (`job_id`),
  KEY `FKefm7gduxl00sldhyxac222797` (`resumes_id`),
  KEY `FKm62ewgfvd3xl09ha3al7o6k2r` (`resumes11_id`),
  KEY `FK340nuaivxiy99hslr3sdydfvv` (`user_id`),
  CONSTRAINT `FK340nuaivxiy99hslr3sdydfvv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKjdec9qbp2blbpag6obwf0fmbd` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `resumes`
--
LOCK TABLES `resumes` WRITE;
/*!40000 ALTER TABLE `resumes` DISABLE KEYS */
;
INSERT INTO `resumes`
VALUES (
    2,
    'voquocviet@gmail.com',
    0,
    '111111111',
    1,
    '2024-11-21 13:11:15.758817',
    '2024-11-29 16:16:55.405564',
    1,
    'voviet266@gmail.com',
    'voviet266@gmail.com',
    NULL,
    NULL
  ),
(
    3,
    'voviet266@gmail.com',
    0,
    '1732897624598_2c.docx',
    1,
    '2024-11-29 16:27:06.579554',
    NULL,
    1,
    'voviet266@gmail.com',
    NULL,
    NULL,
    NULL
  );
/*!40000 ALTER TABLE `resumes` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `roles`
--
DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `roles`
--
LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */
;
INSERT INTO `roles`
VALUES (
    5,
    'admin',
    'Admin được quyền xem',
    _binary '',
    '2024-11-29 14:51:35.897849',
    'voviet266@gmail.com',
    NULL,
    'voviet266@gmail.com'
  ),
(
    6,
    'user',
    'chỉ xem',
    _binary '',
    '2024-11-29 16:24:54.121434',
    'voviet266@gmail.com',
    NULL,
    NULL
  );
/*!40000 ALTER TABLE `roles` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `skills`
--
DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `skills` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `skills`
--
LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */
;
INSERT INTO `skills`
VALUES (
    1,
    'Java',
    '2024-11-18 11:35:14.713046',
    NULL,
    'voviet266@gmail.com',
    NULL
  ),
(
    3,
    'Python',
    '2024-11-26 13:47:15.815058',
    NULL,
    'voviet266@gmail.com',
    NULL
  ),
(
    4,
    'SpringBoot',
    '2024-11-26 13:47:28.132062',
    NULL,
    'voviet266@gmail.com',
    NULL
  );
/*!40000 ALTER TABLE `skills` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */
;
/*!50503 SET character_set_client = utf8mb4 */
;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `age` int NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `gender` enum('FEMALE', 'MALE', 'OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `refresh_token` mediumtext,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKin8gn4o1hpiwe6qe4ey7ykwq7` (`company_id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKin8gn4o1hpiwe6qe4ey7ykwq7` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */
;
--
-- Dumping data for table `users`
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */
;
INSERT INTO `users`
VALUES (
    1,
    'voviet266@gmail.com',
    'võ việt',
    0,
    'đường Nguyễn Thị Tạo, 113D/5 kv.Bình Nhựt, P.Long Hòa - Q.Bình Thủy',
    '2024-11-17 18:44:04.344706',
    '2024-12-02 06:26:20.509330',
    'MALE',
    '$2a$10$IWXpNEOf9LXjWOlpMMG/TOotzaUted2/AGSAG2V4/hpkn95Ac/VSW',
    'anonymousUser',
    '',
    'voviet266@gmail.com',
    5,
    2
  ),
(
    3,
    'vovieta3@gmail.com',
    'Vo Quoc Viet',
    0,
    NULL,
    '2024-12-02 06:15:35.716158',
    NULL,
    NULL,
    '$2a$10$Avuh/j83zQLW9lFw2gj/.eHfcrexkqmnFa1n3wOCvA8VVV7VogCVG',
    'anonymousUser',
    NULL,
    NULL,
    NULL,
    NULL
  ),
(
    4,
    'vovieta31@gmail.com',
    'voviet',
    11,
    '1111',
    '2024-12-02 06:16:49.289172',
    '2024-12-02 06:17:41.540659',
    'MALE',
    '$2a$10$sKhz/.v6YgO4eJz0hdnCeuEnX8EGZC4I/DPlyt/IvnNdCt5NdqI4y',
    'anonymousUser',
    '',
    'vovieta31@gmail.com',
    NULL,
    NULL
  ),
(
    5,
    'vovieta1@gmail.com',
    'viet',
    11,
    '1111',
    '2024-12-02 06:26:34.034871',
    '2024-12-02 06:26:45.688247',
    'MALE',
    '$2a$10$XRScOxdankqqlI8CMGl5eOhFUoUAFgxKxoUGwibJr.j8AF8TKQGxW',
    'anonymousUser',
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2b3ZpZXRhMUBnbWFpbC5jb20iLCJVc2VyIjp7ImlkIjo1LCJuYW1lIjoidmlldCIsImVtYWlsIjoidm92aWV0YTFAZ21haWwuY29tIn0sImV4cCI6MTczMzcyNTYwNSwiaWF0IjoxNzMzMTIwODA1fQ.XkC65OYJprtTglBv86kkx4TXEYdPPQjuRMkzJp2OwdY',
    'anonymousUser',
    NULL,
    NULL
  );
/*!40000 ALTER TABLE `users` ENABLE KEYS */
;
UNLOCK TABLES;
--
-- Dumping routines for database 'defaultdb'
--
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */
;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */
;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */
;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */
;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */
;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */
;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */
;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */
;
-- Dump completed on 2024-12-02 17:34:16