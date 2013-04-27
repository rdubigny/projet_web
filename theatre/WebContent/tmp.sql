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

DROP DATABASE tmp;

CREATE DATABASE tmp DEFAULT CHARACTER SET utf8 COLLATE
utf8_general_ci;



-- ---- Entités Simple ------

CREATE TABLE tmp.spectacle (
id_spectacle INTEGER ,
nom_spectacle VARCHAR( 200 ) NOT NULL,
base_prix NUMERIC(4,2),
CHECK (id_spectacle > 0),
UNIQUE(nom_spectacle), 
CHECK (base_prix > 0),
PRIMARY KEY ( id_spectacle )
) ENGINE = INNODB;

CREATE TABLE tmp.zone (
id_zone INTEGER,
categorie_prix VARCHAR( 200 ) NOT NULL ,
pourcentage_prix INTEGER,
CHECK (pourcentage_prix >= 0 && pourcentage_prix <= 100 ),
CHECK (id_zone > 0),	
PRIMARY KEY ( id_zone )
) ENGINE = INNODB;

CREATE TABLE tmp.dossier (
id_dossier INTEGER,
CHECK ( id_dossier > 0 ),
PRIMARY KEY ( id_dossier )
) ENGINE = INNODB;

CREATE TABLE tmp.utilisateur (
id_utilisateur INTEGER ,
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

CREATE TABLE tmp.representation(
id_representation INTEGER ,
id_spectacle INTEGER,
moment_representation DATETIME,
CHECK ( id_representation > 0 ),
CHECK (DATE_FORMAT(moment_representation, "%Y-%m-%d") >= '2013-09-01'),
PRIMARY KEY ( id_representation ),
FOREIGN KEY (id_spectacle) REFERENCES spectacle(id_spectacle)
) ENGINE = INNODB;

-- ---- Insertions------------

INSERT INTO tmp.Spectacle (nom) VALUES ('L Ecole des Femmes - Moliere');
INSERT INTO tmp.Spectacle (nom) VALUES ('Troilus et Cressida - William Shakespeare');
INSERT INTO tmp.Spectacle (nom) VALUES ('Ce que j appelle oubli - Laurent Mauvignier');



