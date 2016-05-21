-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sr03p001
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
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,1,1,'Une méthode HTTP',1,'BadAnswer'),(2,1,3,'Conteneur web',1,'BadAnswer'),(3,1,4,'Classe capable de recevoir une requête HTTP, et de renvoyer une réponse HTTP.',1,'GoodAnswer'),(4,2,1,'C\'est aussi un JavaBeans',1,'BadAnswer'),(5,2,2,'Elle n\'est pas réutilisable',1,'BadAnswer'),(6,2,3,'Elle est toujours abstraite',0,'BadAnswer'),(7,2,4,'Traitement de requêtes et la personnalisation de réponses',1,'GoodAnswer'),(8,3,1,'C\'est une boucle',1,'BadAnswer'),(9,3,2,'C\'est une condition',1,'GoodAnswer'),(10,4,1,'C\'est l\'opérateur ternaire',1,'GoodAnswer'),(11,4,2,'Opérateur ET, permettant de préciser une condition',1,'BadAnswer'),(12,4,3,'Opérateur OU, permettant de préciser une condition',1,'BadAnswer'),(13,4,4,'Opérateur supérieur ou égal',1,'BadAnswer'),(14,5,1,'Boucle permettant d\'itérer un certain nombre de requête tant que la condition est respectée.',1,'BadAnswer'),(15,5,2,'Boucle permettant d\'itérer un certain nombre de requête au moins une fois tant que la condition est respectée.',1,'GoodAnswer'),(16,6,1,'A alerter un utilisateur.',1,'BadAnswer'),(17,6,2,'A ouvrir une boite de dialogue.',1,'GoodAnswer'),(18,7,1,'A écrire dans la console du navigateur.',1,'GoodAnswer'),(19,7,2,'A se connecter à la console du naviagateur.',1,'BadAnswer'),(20,7,3,'Cette fonction n\'existe pas.',1,'BadAnswer'),(21,8,1,'Permet de faire une évaluation de notre code.',1,'BadAnswer'),(22,8,2,'Evaluer une chaîne de caractères tout comme s’il s’agissait d’un code source.',1,'GoodAnswer'),(23,8,3,'A rien !',1,'BadAnswer'),(24,9,1,'\"51\"',1,'GoodAnswer'),(25,9,2,'6',1,'BadAnswer'),(26,10,1,'Page Helper Process',1,'BadAnswer'),(27,10,2,'Programming Home Pages',1,'BadAnswer'),(28,10,3,'PHP: Hypertext Preprocessor',1,'GoodAnswer'),(29,11,1,'strlen',1,'GoodAnswer'),(30,11,2,'strlength',1,'BadAnswer'),(31,11,3,'length',1,'BadAnswer'),(32,11,4,'substr',1,'BadAnswer'),(33,12,1,'$a = $b',1,'BadAnswer'),(34,12,2,'$a == $b',1,'GoodAnswer'),(35,12,3,'$a != $b',1,'BadAnswer'),(36,12,4,'if($a,$b)',1,'BadAnswer'),(37,13,1,'Il sert à vérifier que toutes les conditions sont réalisées.',1,'BadAnswer'),(38,13,2,'Il sert à vérifier qu\'une, et une seule, des conditions est réalisée.',1,'BadAnswer'),(39,13,3,'Il sert à vérifier qu\'une, au moins, des conditions est réalisée.',1,'GoodAnswer'),(40,14,1,'0 ou 1',1,'BadAnswer'),(41,14,2,'TRUE ou FALSE',1,'GoodAnswer'),(42,14,3,'Toutes sauf NULL',1,'BadAnswer'),(43,15,1,'get',1,'BadAnswer'),(44,15,2,'mailto',1,'BadAnswer'),(45,15,3,'post',1,'GoodAnswer'),(46,16,1,'array_splice()',1,'BadAnswer'),(47,16,2,'array_pop()',1,'GoodAnswer'),(48,16,3,'array_pad()',1,'BadAnswer'),(49,16,4,'array_shift()',1,'BadAnswer'),(50,17,1,'parse_url()',1,'BadAnswer'),(51,17,2,'http_post()',1,'BadAnswer'),(52,17,3,'header()',1,'GoodAnswer'),(53,18,1,'mysql_fetch_row()',1,'GoodAnswer'),(54,18,2,'mysql_data_seek()',1,'BadAnswer'),(55,18,3,'mysql_affected_rows()',1,'BadAnswer'),(56,19,1,'delete',1,'BadAnswer'),(57,19,2,'unlink',1,'GoodAnswer'),(58,19,3,'remove',1,'BadAnswer'),(59,19,4,'clearfile',1,'BadAnswer'),(60,20,1,'exit',1,'GoodAnswer'),(61,20,2,'print_r',1,'BadAnswer'),(62,20,3,'define',1,'BadAnswer');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attempt`
--

LOCK TABLES `attempt` WRITE;
/*!40000 ALTER TABLE `attempt` DISABLE KEYS */;
INSERT INTO `attempt` VALUES (1,1,2,2,'2016-05-21 16:14:47','2016-05-21 20:14:47'),(2,2,1,1,'2016-05-21 16:14:47','2016-05-22 02:14:47'),(3,2,2,0,'2016-05-21 16:14:47','2016-05-21 18:14:47'),(4,1,3,2,'2016-05-22 14:36:45','2016-05-22 04:36:45');
/*!40000 ALTER TABLE `attempt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attemptanswer`
--

LOCK TABLES `attemptanswer` WRITE;
/*!40000 ALTER TABLE `attemptanswer` DISABLE KEYS */;
INSERT INTO `attemptanswer` VALUES (1,3),(2,3),(2,5),(1,7),(3,8),(3,12),(3,14),(4,18),(4,22),(4,25);
/*!40000 ALTER TABLE `attemptanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,2,1,'Qu\'est qu\'une servlet ?',1),(2,2,2,'Quelle est la particularité d\'une servlet ?',1),(3,1,1,'Qu\'est ce la structure if...else ?',1),(4,1,2,'A quoi sert l\'opérateur « ? » ?',1),(5,1,3,'A quoi sert la boucle do...while ?',1),(6,3,1,'A quoi sert la fonction alert() en JavaScript ?',1),(7,3,2,'A quoi sert la fonction console.log() en JavaScript ?',1),(8,3,3,'A quoi sert la fonction eval en JavaScript ?',1),(9,3,4,'Quelle est le résultat de eval(\'2+3\') + \'1\' ?',1),(10,4,1,'Que signifie PHP ?',1),(11,4,2,'Quelle fonction retourne la longueur d\'une chaîne de texte ?',1),(12,4,3,'Comment vérifie-t-on l\'égalité de deux variables : $a et $b ?',1),(13,4,4,'Quelle est l\'utilité de l\'opérateur || ?',1),(14,4,5,'Quelles valeurs peut prendre le type booléen ?',1),(15,4,6,'Dans le cas d\'envoi d\'informations plus ou moins sensibles par formulaire, quelle méthode utilisera-t-on de préférence ?',1),(16,5,1,'Quelle fonction retire un élément de la fin d\'un tableau ?',1),(17,5,2,'Quelle fonction permet d\'envoyer des en-têtes HTTP au navigateur avant le contenu de la page ?',1),(18,5,3,'Quelle fonction permet de lire le résultat d\'une ressources MySQL renvoyée par mysql_query() ?',1),(19,5,4,'Quelle fonction permet d\'effacer un fichier ?',1),(20,5,5,'Quelle instruction n\'est pas le nom d\'une fonction ?',1);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `questionnaire`
--

LOCK TABLES `questionnaire` WRITE;
/*!40000 ALTER TABLE `questionnaire` DISABLE KEYS */;
INSERT INTO `questionnaire` VALUES (1,'JavaScript','Boucle & Condition',1),(2,'JEE','Servlet',1),(3,'JavaScript','Fonction de bases',1),(4,'PHP','Quizz PHP débutant',1),(5,'PHP','Quizz PHP moyen',1),(6,'PHP','Quizz PHP difficile',1);
/*!40000 ALTER TABLE `questionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `superuser`
--

LOCK TABLES `superuser` WRITE;
/*!40000 ALTER TABLE `superuser` DISABLE KEYS */;
INSERT INTO `superuser` VALUES (1,'atchandj@gmail.com','Tchandjou','Adrien','atchandj','0654651084','UTC','2015-07-02 15:00:12',1),(2,'daniel@gmail.com','Artchounin','Daniel','daniel','0657311307','UTC','2015-05-02 11:00:12',1);
/*!40000 ALTER TABLE `superuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES ('Ajax'),('C et C++'),('Développement JAVA'),('JavaScript'),('JEE'),('PHP'),('WebServices');
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `trainee`
--

LOCK TABLES `trainee` WRITE;
/*!40000 ALTER TABLE `trainee` DISABLE KEYS */;
INSERT INTO `trainee` VALUES (1,'ckyle@gmail.com','Kyle','Cédric','ckyle','0156201074','UTC','2016-05-21 16:14:46',1),(2,'jdiesel@gmail.com','Diesel','Jean','jdiesel','0656221084','UTC','2016-05-21 16:14:46',1);
/*!40000 ALTER TABLE `trainee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-21 19:33:14
