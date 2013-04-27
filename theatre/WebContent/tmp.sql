CREATE DATABASE tmp DEFAULT CHARACTER SET utf8 COLLATE
utf8_general_ci;

CREATE TABLE tmp.Utilisateur (
login VARCHAR( 20 ) NOT NULL,
mot_de_passe VARCHAR( 56 ) NOT NULL,
nom VARCHAR( 20 ) NOT NULL,
prenom VARCHAR( 20 ) NOT NULL,
email VARCHAR( 60 ) NOT NULL,
PRIMARY KEY ( login )
) ENGINE = INNODB;

INSERT INTO tmp.Utilisateur (login, mot_de_passe, nom, prenom, email) VALUES ('root', 'root', 'root', 'root', 'chezRoot');

CREATE TABLE tmp.Spectacle (
nom VARCHAR( 60 ) NOT NULL,
PRIMARY KEY ( nom )
) ENGINE = INNODB;

INSERT INTO tmp.Spectacle (nom) VALUES ('L Ecole des Femmes - Moliere');
INSERT INTO tmp.Spectacle (nom) VALUES ('Troilus et Cressida - William Shakespeare');
INSERT INTO tmp.Spectacle (nom) VALUES ('Ce que j appelle oubli - Laurent Mauvignier');