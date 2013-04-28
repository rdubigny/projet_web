
-- Spectacle -----------
INSERT INTO projweb_db.spectacle (nom_spectacle, base_prix) VALUES ('L Ecole des Femmes - Moliere','20.00');
INSERT INTO projweb_db.spectacle (nom_spectacle, base_prix) VALUES ('Troilus et Cressida - William Shakespeare','30.00');
INSERT INTO projweb_db.spectacle (nom_spectacle, base_prix) VALUES ('Ce que j appelle oubli - Laurent Mauvignier','10.00');

-- Utilisateur ---------
INSERT INTO projweb_db.Utilisateur (login, mot_de_passe, nom, prenom, mail, type_utilisateur) VALUES ('root', 'root', 'root', 'root', 'root@root.root', 'client');

-- Représentation ------
INSERT INTO projweb_db.representation (id_spectacle, moment_representation) VALUES ('1',NOW());