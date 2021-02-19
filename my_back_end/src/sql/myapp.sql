
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
-- Table structure for table `Coupon`
--

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

DROP TABLE IF EXISTS tb_user;

create table tb_user
(
    user_id int auto_increment,
    name varchar(255) null,
    phone varchar(255) not null,
    password varchar(255) not null,
    position varchar(255) default '游客' not null,
    constraint tb_user_pk
        primary key (user_id)
);
DROP TABLE IF EXISTS tb_task;

create table tb_task
(
    task_id int auto_increment,
    task_name varchar(255) not null,
    status varchar(255) default '待完成' not null,
    create_time datetime not null,
    expected_time datetime not null,
    expected_exam_time datetime not null,
    quality_inspector int default null,
    group_leader int not null,
    comments varchar(2550) null,
    finish_time datetime,
    finish_exam_time datetime,
    constraint tb_task_pk
        primary key (task_id)
);

