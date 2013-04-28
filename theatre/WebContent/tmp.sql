CREATE DATABASE bd DEFAULT CHARACTER SET utf8 COLLATE
utf8_general_ci;

CREATE TABLE bd.Utilisateur (
login VARCHAR( 20 ) NOT NULL,
mot_de_passe VARCHAR( 56 ) NOT NULL,
nom VARCHAR( 20 ) NOT NULL,
prenom VARCHAR( 20 ) NOT NULL,
email VARCHAR( 60 ) NOT NULL,
PRIMARY KEY ( login )
) ENGINE = INNODB;

INSERT INTO bd.Utilisateur (login, mot_de_passe, nom, prenom, email) VALUES ('root', 'root', 'root', 'root', 'chezRoot');

CREATE TABLE bd.Spectacle (
nom VARCHAR( 60 ) NOT NULL,
PRIMARY KEY ( nom )
) ENGINE = INNODB;

DROP DATABASE bd;

CREATE DATABASE bd DEFAULT CHARACTER SET utf8 COLLATE
utf8_general_ci;



-- ---- Entités Simple ------

CREATE TABLE bd.spectacle (
id_spectacle INTEGER AUTO_INCREMENT,
nom_spectacle VARCHAR( 200 ) NOT NULL,
base_prix NUMERIC(4,2),
CHECK (id_spectacle > 0),
UNIQUE(nom_spectacle), 
CHECK (base_prix > 0),
PRIMARY KEY ( id_spectacle )
) ENGINE = INNODB;

CREATE TABLE bd.zone (
id_zone INTEGER AUTO_INCREMENT,
categorie_prix VARCHAR( 200 ) NOT NULL ,
pourcentage_prix INTEGER,
CHECK (pourcentage_prix >= 0 && pourcentage_prix <= 100 ),
CHECK (id_zone > 0),	
PRIMARY KEY ( id_zone )
) ENGINE = INNODB;

CREATE TABLE bd.dossier (
id_dossier INTEGER AUTO_INCREMENT,
CHECK ( id_dossier > 0 ),
PRIMARY KEY ( id_dossier )
) ENGINE = INNODB;

CREATE TABLE bd.utilisateur (
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

-- ---- Entités faibles ------

CREATE TABLE bd.representation(
id_representation INTEGER AUTO_INCREMENT,
id_spectacle INTEGER,
moment_representation DATETIME,
CHECK ( id_representation > 0 ),
CHECK (DATE_FORMAT(moment_representation, "%Y-%m-%d") >= '2013-09-01'),
PRIMARY KEY ( id_representation ),
FOREIGN KEY (id_spectacle) REFERENCES spectacle(id_spectacle)
) ENGINE = INNODB;

-- ---- Insertions------------

INSERT INTO tmp.spectacle (id_spectacle, nom_spectacle, base_prix) VALUES ('1','L Ecole des Femmes - Moliere','20.00');
INSERT INTO tmp.spectacle (id_spectacle, nom_spectacle, base_prix) VALUES ('2','Troilus et Cressida - William Shakespeare','30.00');
INSERT INTO tmp.spectacle (id_spectacle, nom_spectacle, base_prix) VALUES ('3','Ce que j appelle oubli - Laurent Mauvignier','10.00');



