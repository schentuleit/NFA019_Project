-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 20 juin 2025 à 08:19
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pixel_art_db`
--
CREATE DATABASE IF NOT EXISTS `pixel_art_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `pixel_art_db`;

-- --------------------------------------------------------

--
-- Structure de la table `auteur`
--

DROP TABLE IF EXISTS `auteur`;
CREATE TABLE IF NOT EXISTS `auteur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `auteur`
--

INSERT INTO `auteur` (`id`, `nom`, `email`) VALUES
(2, 'Moi qui d\'autre', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `dessin`
--

DROP TABLE IF EXISTS `dessin`;
CREATE TABLE IF NOT EXISTS `dessin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `largeur` int NOT NULL,
  `hauteur` int NOT NULL,
  `date_creation` datetime DEFAULT CURRENT_TIMESTAMP,
  `auteur_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `auteur_id` (`auteur_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `dessin`
--

INSERT INTO `dessin` (`id`, `nom`, `largeur`, `hauteur`, `date_creation`, `auteur_id`) VALUES
(6, 'El magnifico Testo', 32, 32, '2025-06-21 10:17:15', 2);

-- --------------------------------------------------------

--
-- Structure de la table `pixel`
--

DROP TABLE IF EXISTS `pixel`;
CREATE TABLE IF NOT EXISTS `pixel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dessin_id` int NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  `couleur_hex` varchar(7) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dessin_id` (`dessin_id`)
) ENGINE=MyISAM AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `pixel`
--

INSERT INTO `pixel` (`id`, `dessin_id`, `x`, `y`, `couleur_hex`) VALUES
(63, 6, 2, 12, '#000000'),
(62, 6, 15, 11, '#000000'),
(61, 6, 15, 10, '#01ae01'),
(60, 6, 14, 10, '#ccffcc'),
(59, 6, 11, 10, '#ff3333'),
(58, 6, 3, 10, '#01ae01'),
(57, 6, 25, 9, '#ff3333'),
(56, 6, 20, 9, '#01ae01'),
(55, 6, 14, 9, '#01ae01'),
(54, 6, 6, 9, '#ff3333'),
(53, 6, 21, 7, '#ccffcc'),
(52, 6, 18, 7, '#000000'),
(51, 6, 5, 6, '#000000'),
(50, 6, 11, 5, '#000000'),
(49, 6, 8, 5, '#ccffcc'),
(48, 6, 11, 4, '#ff3333'),
(47, 6, 6, 4, '#01ae01'),
(46, 6, 19, 3, '#ff3333'),
(64, 6, 18, 12, '#ccffcc'),
(65, 6, 24, 12, '#000000'),
(66, 6, 11, 13, '#ccffcc'),
(67, 6, 25, 13, '#01ae01'),
(68, 6, 13, 14, '#01ae01'),
(69, 6, 25, 14, '#ccffcc'),
(70, 6, 4, 15, '#ff3333'),
(71, 6, 10, 16, '#ff3333'),
(72, 6, 5, 17, '#ccffcc'),
(73, 6, 10, 17, '#ccffcc'),
(74, 6, 11, 17, '#000000'),
(75, 6, 20, 17, '#ff3333'),
(76, 6, 24, 18, '#000000'),
(77, 6, 26, 18, '#ff3333'),
(78, 6, 1, 19, '#000000'),
(79, 6, 20, 20, '#ccffcc'),
(80, 6, 3, 21, '#01ae01'),
(81, 6, 11, 21, '#ccffcc'),
(82, 6, 16, 21, '#000000'),
(83, 6, 24, 21, '#01ae01'),
(84, 6, 9, 22, '#000000'),
(85, 6, 11, 22, '#ccffcc'),
(86, 6, 21, 22, '#01ae01'),
(87, 6, 27, 22, '#ff3333'),
(88, 6, 4, 23, '#ff3333'),
(89, 6, 21, 23, '#ff3333'),
(90, 6, 14, 24, '#01ae01'),
(91, 6, 11, 26, '#01ae01'),
(92, 6, 12, 26, '#ff3333'),
(93, 6, 23, 26, '#ccffcc'),
(94, 6, 26, 26, '#ccffcc');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
