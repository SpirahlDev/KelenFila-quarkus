-- Seed pour la table profile avec différents profils d'utilisateurs
INSERT INTO `kelen_fila`.`profile` 
(`profile_code`, `profile_name`, `description`, `is_active`) 
VALUES 
('ADMIN', 'Administrateur', 'Gère le système', 1),
('ROOT', 'Administrateur', 'Accès complet à toutes les fonctionnalités du système', 1),
('CLIENT', 'Acheteur', 'Peut participer aux enchères et faire des offres', 1),
('AUCTIONNEER', 'Commissaire-priseur', 'Supervise le déroulement des enchères', 1);