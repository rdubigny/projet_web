DROP DATABASE projweb_db;
CREATE DATABASE projweb_db DEFAULT CHARACTER SET utf8 COLLATE
utf8_general_ci;
CREATE TABLE projweb_db.spectacle (
id_spectacle INTEGER AUTO_INCREMENT,
nom_spectacle VARCHAR( 200 ) NOT NULL,
base_prix NUMERIC(4,2),
CHECK (id_spectacle > 0),
UNIQUE(nom_spectacle), 
CHECK (base_prix > 0),
PRIMARY KEY ( id_spectacle )
) ENGINE = INNODB;
INSERT INTO projweb_db.spectacle (nom_spectacle, base_prix) VALUES ('L Ecole des Femmes - Moliere','20.00');
INSERT INTO projweb_db.spectacle (nom_spectacle, base_prix) VALUES ('Troilus et Cressida - William Shakespeare','30.00');
INSERT INTO projweb_db.spectacle (nom_spectacle, base_prix) VALUES ('Ce que j appelle oubli - Laurent Mauvignier','10.00');

CREATE TABLE projweb_db.utilisateur (
id_utilisateur INTEGER AUTO_INCREMENT,
login VARCHAR( 56 ) NOT NULL,
mot_de_passe VARCHAR( 56 ) NOT NULL,
nom VARCHAR( 20 ) NOT NULL,
prenom VARCHAR( 20 ) NOT NULL,
mail VARCHAR( 60 ) NOT NULL,
type_utilisateur ENUM ("client" , "responsable"),
UNIQUE (login),
PRIMARY KEY ( id_utilisateur )
) ENGINE = INNODB;
INSERT INTO projweb_db.Utilisateur (login, mot_de_passe, nom, prenom, mail, type_utilisateur) VALUES ('root', 'root', 'root', 'root', 'root@root.root', 'client');

CREATE TABLE projweb_db.representation(
id_representation INTEGER AUTO_INCREMENT,
id_spectacle INTEGER,
moment_representation DATETIME,
CHECK ( id_representation > 0 ),
CHECK (DATE_FORMAT(moment_representation, "%Y-%m-%d") >= '2013-09-01'),
PRIMARY KEY ( id_representation ),
FOREIGN KEY (id_spectacle) REFERENCES spectacle(id_spectacle)
) ENGINE = INNODB;

INSERT INTO projweb_db.representation (id_spectacle, moment_representation) VALUES ('1',NOW());
INSERT INTO projweb_db.representation (id_spectacle, moment_representation) VALUES ('1','2013-05-28 15:00:00');
INSERT INTO projweb_db.representation (id_spectacle, moment_representation) VALUES ('1','2013-03-28 15:00:00');