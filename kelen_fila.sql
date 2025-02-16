-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : sam. 15 fév. 2025 à 23:07
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `kelen_fila`
--

-- --------------------------------------------------------

--
-- Structure de la table `account`
--

CREATE TABLE `account` (
  `account_id` bigint(20) NOT NULL,
  `login` varchar(160) NOT NULL,
  `password` varchar(60) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `suspended_at` datetime DEFAULT NULL,
  `remember_token` varchar(160) DEFAULT NULL,
  `profile_id` int(11) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  `avatar` varchar(455) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `account`
--

INSERT INTO `account` (`account_id`, `login`, `password`, `created_at`, `suspended_at`, `remember_token`, `profile_id`, `person_id`, `avatar`, `updated_at`) VALUES
(1, 'alia07@yopmail.com', '$2y$12$tfQRwleq42zF2cQU.S5YNeYcpaGq/zopVolBPS4TNOUfRXYpKnfTG', '2024-09-19 18:35:34', NULL, 'BSrQikOMIg', 12, 3, 'https://via.placeholder.com/100x100.png/006688?text=people+Faker+voluptas', '2024-09-19 18:35:34');

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

CREATE TABLE `article` (
  `article_id` bigint(20) NOT NULL,
  `article_name` varchar(100) NOT NULL,
  `article_description` text NOT NULL,
  `article_state` varchar(150) NOT NULL,
  `article_base_price` double NOT NULL,
  `item_number` int(11) NOT NULL,
  `image1` varchar(400) NOT NULL,
  `image2` varchar(400) NOT NULL,
  `image3` varchar(400) NOT NULL,
  `image4` varchar(400) NOT NULL,
  `image5` varchar(400) DEFAULT NULL,
  `image6` varchar(400) DEFAULT NULL,
  `adjudication_datetime` datetime DEFAULT NULL,
  `awarded_price` double DEFAULT NULL,
  `auction_id` bigint(20) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `auction`
--

CREATE TABLE `auction` (
  `auction_id` bigint(20) NOT NULL,
  `auction_code` varchar(100) NOT NULL,
  `auction_date` datetime NOT NULL,
  `estimated_time` datetime DEFAULT NULL,
  `auction_owner` bigint(20) NOT NULL,
  `auction_add_date` timestamp NULL DEFAULT current_timestamp(),
  `auction_duration` datetime DEFAULT NULL,
  `auction_description` varchar(400) DEFAULT NULL,
  `auction_title` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `category_illustration_image` varchar(400) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `country`
--

CREATE TABLE `country` (
  `country_id` int(11) NOT NULL,
  `country_name` varchar(45) NOT NULL,
  `telephone_code` varchar(45) NOT NULL,
  `country_code` varchar(45) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `country`
--

INSERT INTO `country` (`country_id`, `country_name`, `telephone_code`, `country_code`, `created_at`, `deleted_at`, `updated_at`) VALUES
(1, 'Bénin', '+229', 'BEN', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(2, 'Burkina Faso', '+226', 'BFA', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(3, 'Côte d\'Ivoire', '+225', 'CIV', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(4, 'Gambie', '+220', 'GMB', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(5, 'Ghana', '+233', 'GHA', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(6, 'Guinée', '+224', 'GIN', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(7, 'Guinée-Bissau', '+245', 'GNB', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(8, 'Liberia', '+231', 'LBR', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(9, 'Mali', '+223', 'MLI', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(10, 'Niger', '+227', 'NER', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(11, 'Nigeria', '+234', 'NGA', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(12, 'Sénégal', '+221', 'SEN', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(13, 'Sierra Leone', '+232', 'SLE', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52'),
(14, 'Togo', '+228', 'TGO', '2024-09-19 18:29:52', NULL, '2024-09-19 18:29:52');

-- --------------------------------------------------------

--
-- Structure de la table `identity_evidence`
--

CREATE TABLE `identity_evidence` (
  `evidence_id` bigint(20) NOT NULL,
  `owner_identity_card_filepath` varchar(400) DEFAULT NULL,
  `enterprise_idu_code` varchar(45) DEFAULT NULL COMMENT 'Enterprise unique identification number, when it is one',
  `commercial_register` varchar(400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='I''m planning to store here identity informations about the natural person or the legal entity\n';

-- --------------------------------------------------------

--
-- Structure de la table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2024_09_18_181410_create_personal_access_tokens_table', 1),
(2, '0001_01_01_000000_create_users_table', 2);

-- --------------------------------------------------------

--
-- Structure de la table `otp`
--

CREATE TABLE `otp` (
  `id` int(11) NOT NULL,
  `value` varchar(45) NOT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT 1,
  `creationDate` datetime NOT NULL,
  `expirationDate` timestamp NULL DEFAULT NULL,
  `action` varchar(45) DEFAULT NULL,
  `canal` varchar(45) DEFAULT NULL,
  `id_account` bigint(20) DEFAULT NULL,
  `sent` tinyint(1) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  `tmp_token` varchar(160) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `participant`
--

CREATE TABLE `participant` (
  `account_id` bigint(20) NOT NULL,
  `auction_id` bigint(20) NOT NULL,
  `is_bidder` tinyint(4) NOT NULL DEFAULT 1,
  `registration_date` timestamp NULL DEFAULT current_timestamp(),
  `has_join_auction` tinyint(4) DEFAULT 0,
  `has_actived_notification` tinyint(4) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `permission`
--

CREATE TABLE `permission` (
  `id_permission` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `code` varchar(45) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `person`
--

CREATE TABLE `person` (
  `person_id` bigint(20) NOT NULL,
  `email` varchar(160) NOT NULL,
  `phonenumber` varchar(25) NOT NULL,
  `residence_contry` int(11) NOT NULL,
  `residence_city` varchar(45) NOT NULL,
  `address` varchar(100) NOT NULL,
  `is_verified` tinyint(4) DEFAULT 0,
  `person_name` varchar(150) NOT NULL,
  `person_type` enum('LEGAL_ENTITY','NATURAL_ENTITY') NOT NULL,
  `evidence_id` bigint(20) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `person`
--

INSERT INTO `person` (`person_id`, `email`, `phonenumber`, `residence_contry`, `residence_city`, `address`, `is_verified`, `person_name`, `person_type`, `evidence_id`, `updated_at`) VALUES
(1, 'trevion.willms@example.org', '(936) 364-3146', 3, 'Destiniside', '28772 Ebert Points\nKoelpinland, WA 36387-1803', 0, 'Joany Nader', 'LEGAL_ENTITY', NULL, NULL),
(2, 'ycruickshank@example.net', '+1-518-939-3662', 3, 'Ashleyfurt', '1343 Milan Island Apt. 535\nSporerland, MA 68234-3671', 0, 'Tia Reichel', 'LEGAL_ENTITY', NULL, NULL),
(3, 'edmond08@example.net', '223.945.7741', 3, 'East Newton', '997 Adelia Tunnel Suite 183\nNevahaven, NV 86214', 1, 'Herminio Mitchell', 'LEGAL_ENTITY', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `profile`
--

CREATE TABLE `profile` (
  `profile_id` int(11) NOT NULL,
  `profile_code` varchar(45) NOT NULL,
  `profile_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `profile`
--

INSERT INTO `profile` (`profile_id`, `profile_code`, `profile_name`, `description`, `is_active`, `created_at`, `deleted_at`, `updated_at`) VALUES
(5, 'id', 'molestiae', 'Cumque voluptatem molestiae quae veritatis culpa sunt.', 1, '2024-09-19 18:17:26', NULL, '2024-09-19 18:17:26'),
(6, 'nulla', 'dolore', 'Deserunt et sit qui aut.', 1, '2024-09-19 18:21:30', NULL, '2024-09-19 18:21:30'),
(7, 'quia', 'cumque', 'Qui labore odit qui eos voluptatem.', 0, '2024-09-19 18:22:47', NULL, '2024-09-19 18:22:47'),
(8, 'inventore', 'sit', 'Natus fugit provident ea sit.', 0, '2024-09-19 18:23:18', NULL, '2024-09-19 18:23:18'),
(9, 'quis', 'natus', 'Sed quos eveniet voluptas sunt hic ad ut.', 0, '2024-09-19 18:31:01', NULL, '2024-09-19 18:31:01'),
(10, 'autem', 'illum', 'Quia quis voluptatem officiis nesciunt inventore rerum magnam.', 1, '2024-09-19 18:33:07', NULL, '2024-09-19 18:33:07'),
(11, 'repellat', 'est', 'Et exercitationem sit non.', 0, '2024-09-19 18:35:16', NULL, '2024-09-19 18:35:16'),
(12, 'fugit', 'doloremque', 'Ea omnis et soluta dicta eveniet et ad dolore.', 0, '2024-09-19 18:35:34', NULL, '2024-09-19 18:35:34');

-- --------------------------------------------------------

--
-- Structure de la table `profile_has_permission`
--

CREATE TABLE `profile_has_permission` (
  `profil_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `successful_bider`
--

CREATE TABLE `successful_bider` (
  `successful_bider_id` bigint(20) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `awared_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `fk_account_profile_idx` (`profile_id`),
  ADD KEY `fk_account_user_idx` (`person_id`);

--
-- Index pour la table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`article_id`),
  ADD KEY `fk_lot_category_idx` (`category_id`),
  ADD KEY `fk_article_auction` (`auction_id`);

--
-- Index pour la table `auction`
--
ALTER TABLE `auction`
  ADD PRIMARY KEY (`auction_id`),
  ADD UNIQUE KEY `numEnchere_UNIQUE` (`auction_code`),
  ADD KEY `FK_ENCHERE_VENDEUR_idx` (`auction_owner`);

--
-- Index pour la table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`),
  ADD UNIQUE KEY `designCategorie_UNIQUE` (`category_name`);

--
-- Index pour la table `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`country_id`);

--
-- Index pour la table `identity_evidence`
--
ALTER TABLE `identity_evidence`
  ADD PRIMARY KEY (`evidence_id`);

--
-- Index pour la table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `otp`
--
ALTER TABLE `otp`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `participant`
--
ALTER TABLE `participant`
  ADD KEY `fk_user_has_enchere_enchere1_idx` (`auction_id`),
  ADD KEY `fk_user_has_enchere_user1_idx` (`account_id`);

--
-- Index pour la table `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD PRIMARY KEY (`email`);

--
-- Index pour la table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`id_permission`);

--
-- Index pour la table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`person_id`),
  ADD KEY `fk_user_country_idx` (`residence_contry`),
  ADD KEY `fk_person_identity_evidence_idx` (`evidence_id`);

--
-- Index pour la table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Index pour la table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`);

--
-- Index pour la table `profile_has_permission`
--
ALTER TABLE `profile_has_permission`
  ADD KEY `fk_profil_has_permission_profil_idx` (`profil_id`),
  ADD KEY `fk_profil_has_permission_permission_idx` (`permission_id`);

--
-- Index pour la table `successful_bider`
--
ALTER TABLE `successful_bider`
  ADD PRIMARY KEY (`successful_bider_id`,`article_id`),
  ADD KEY `fk_user_has_lot_lot1_idx` (`article_id`),
  ADD KEY `fk_user_has_lot_user1_idx` (`successful_bider_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `article`
--
ALTER TABLE `article`
  MODIFY `article_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `auction`
--
ALTER TABLE `auction`
  MODIFY `auction_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `country`
--
ALTER TABLE `country`
  MODIFY `country_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `identity_evidence`
--
ALTER TABLE `identity_evidence`
  MODIFY `evidence_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `otp`
--
ALTER TABLE `otp`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- AUTO_INCREMENT pour la table `permission`
--
ALTER TABLE `permission`
  MODIFY `id_permission` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pour la table `person`
--
ALTER TABLE `person`
  MODIFY `person_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `successful_bider`
--
ALTER TABLE `successful_bider`
  MODIFY `successful_bider_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `fk_account_person` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_account_profile` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `fk_article_auction` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`auction_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_lot_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `auction`
--
ALTER TABLE `auction`
  ADD CONSTRAINT `FK_ENCHERE_VENDEUR` FOREIGN KEY (`auction_owner`) REFERENCES `account` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `participant`
--
ALTER TABLE `participant`
  ADD CONSTRAINT `fk_user_has_enchere_enchere1` FOREIGN KEY (`auction_id`) REFERENCES `auction` (`auction_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_has_enchere_user1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `fk_person_identity_evidence` FOREIGN KEY (`evidence_id`) REFERENCES `identity_evidence` (`evidence_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_country` FOREIGN KEY (`residence_contry`) REFERENCES `country` (`country_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `profile_has_permission`
--
ALTER TABLE `profile_has_permission`
  ADD CONSTRAINT `fk_profil_has_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id_permission`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_profil_has_permission_profil` FOREIGN KEY (`profil_id`) REFERENCES `profile` (`profile_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `successful_bider`
--
ALTER TABLE `successful_bider`
  ADD CONSTRAINT `fk_user_has_lot_lot1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_has_lot_user1` FOREIGN KEY (`successful_bider_id`) REFERENCES `person` (`person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
