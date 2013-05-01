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
type_utilisateur ENUM ("client" , "responsable", "guichet"),
UNIQUE (login),
PRIMARY KEY ( id_utilisateur )
) ENGINE = INNODB;

DELIMITER $$
CREATE TRIGGER projweb_db.resp_unique BEFORE INSERT ON projweb_db.utilisateur
FOR EACH ROW
BEGIN
  IF (SELECT COUNT(*) FROM projweb_db.utilisateur
     WHERE type_utilisateur = NEW.type_utilisateur AND type_utilisateur='responsable') > 0 
  THEN
    SIGNAL SQLSTATE '02000'
     SET MESSAGE_TEXT = 'Il ne peut y avoir qu\'un seul responsable de l\'application', 
         MYSQL_ERRNO = 1001;
  END IF;
END$$
DELIMITER ;

CREATE TABLE projweb_db.zone(
id_zone INTEGER AUTO_INCREMENT,
categorie_prix VARCHAR (60) NOT NULL,
base_pourcentage_prix INTEGER,
CHECK (base_pourcentage_prix >= 0 && base_pourcentage_prix <= 100),
PRIMARY KEY (id_zone)
) ENGINE = INNODB;

CREATE TABLE projweb_db.dossier(
id_dossier INTEGER AUTO_INCREMENT,
PRIMARY KEY (id_dossier)
) ENGINE = INNODB;

-- Entites faibles -----------
CREATE TABLE projweb_db.representation(
id_representation INTEGER AUTO_INCREMENT,
id_spectacle INTEGER,
moment_representation DATETIME,
UNIQUE(moment_representation),
PRIMARY KEY ( id_representation ),
FOREIGN KEY (id_spectacle) REFERENCES spectacle(id_spectacle) ON DELETE CASCADE
) ENGINE = INNODB;

DELIMITER $$

CREATE TRIGGER projweb_db.trigger1
BEFORE INSERT
ON projweb_db.representation
FOR EACH ROW
BEGIN
  IF (DATEDIFF(NEW.moment_representation, DATE('2012-09-01')) < 0)  || (DATEDIFF(NEW.moment_representation, DATE('2013-05-31')) > 0) THEN
    SIGNAL SQLSTATE '02000' SET MESSAGE_TEXT = 'Les repr�sentations doivent se d�rouler entre septembre et juin de l\'ann�e en cours ';
  END IF;
END$$

DELIMITER ;




CREATE TABLE projweb_db.place(
id_place INTEGER AUTO_INCREMENT,
numero_rang INTEGER,
numero_siege INTEGER,
id_zone INTEGER,
CHECK ( numero_rang > 0 ),
CHECK ( numero_siege > 0 ),
UNIQUE (numero_rang, numero_siege),
PRIMARY KEY ( id_place),
FOREIGN KEY (id_zone) REFERENCES zone(id_zone) ON DELETE CASCADE
) ENGINE = INNODB;

CREATE TABLE projweb_db.ticket(
id_ticket INTEGER AUTO_INCREMENT,
moment_vente DATETIME,
CHECK ( id_ticket > 0 ),
PRIMARY KEY (id_ticket)
) ENGINE = INNODB;

-- Associations --------------

CREATE TABLE projweb_db.reservation(
id_reservation INTEGER AUTO_INCREMENT,
id_representation INTEGER,
id_place INTEGER,
id_utilisateur INTEGER,
PRIMARY KEY ( id_reservation ),
UNIQUE (id_reservation, id_place),
FOREIGN KEY (id_representation) REFERENCES representation(id_representation) ON DELETE CASCADE,
FOREIGN KEY (id_place) REFERENCES place(id_place) ON DELETE CASCADE,
FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur) ON DELETE CASCADE
) ENGINE = INNODB;


CREATE TABLE projweb_db.achat(
id_achat INTEGER AUTO_INCREMENT,
id_representation INTEGER,
id_place INTEGER,
id_dossier INTEGER,
id_ticket INTEGER,
id_utilisateur INTEGER,
PRIMARY KEY ( id_achat ),
UNIQUE (id_achat, id_place),
FOREIGN KEY (id_place) REFERENCES place(id_place) ON DELETE CASCADE,
FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur) ON DELETE CASCADE,
FOREIGN KEY (id_representation) REFERENCES representation(id_representation) ON DELETE CASCADE,
FOREIGN KEY (id_dossier) REFERENCES dossier(id_dossier) ON DELETE CASCADE,
FOREIGN KEY (id_ticket) REFERENCES ticket(id_ticket) ON DELETE CASCADE
) ENGINE = INNODB;
