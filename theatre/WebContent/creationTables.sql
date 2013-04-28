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

CREATE TABLE projweb_db.representation(
id_representation INTEGER AUTO_INCREMENT,
id_spectacle INTEGER,
moment_representation DATETIME,
CHECK ( id_representation > 0 ),
CHECK (DATE_FORMAT(moment_representation, "%Y-%m-%d") >= '2013-09-01'),
PRIMARY KEY ( id_representation ),
FOREIGN KEY (id_spectacle) REFERENCES spectacle(id_spectacle) ON DELETE CASCADE
) ENGINE = INNODB;

CREATE TABLE projweb_db.zone(
id_zone INTEGER AUTO_INCREMENT,
categorie_prix VARCHAR (60) NOT NULL,
base_pourcentage_prix INTEGER,
CHECK ( base_pourcentage_prix >= 0 && base_pourcentage_prix <= 100),
PRIMARY KEY (id_zone)
) ENGINE = INNODB;
