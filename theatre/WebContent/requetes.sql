-- identification

-- verif login
SELECT c.login FROM projweb_db.utilisateur c WHERE c.login = ?
-- verif mdp
SELECT c.mot_de_passe FROM projweb_db.utilisateur c WHERE c.mot_de_passe = ?
-- extraction id_utilisateur et type_utilisateur
SELECT c.id_utilisateur c.type_utilisateur FROM projweb_db.utilisateur c WHERE c.login = ?

-- acces id

SELECT s.id_spectacle FROM projweb_db.spectacle s ;
SELECT r.id_representation FROM projweb_db.representation r, projweb_db.spectacle s WHERE s.id_spectacle = r.id_spectacle ;

-- Liste spectacles j'ai mis juste nom spectacle parce que je crois que c'est le seul nécessaire ici

SELECT s.nom_spectacle FROM projweb_db.spectacle s ;

SELECT * FROM projweb_db.spectacle s ;

-- Liste representations

SELECT s.nom_spectacle, r.moment_representation FROM projweb_db.representation r, projweb_db.spectacle s
 WHERE s.id_spectacle = r.id_spectacle AND CURTIME() < SUBTIME(r.moment_representation, '0 01:00:00');
SELECT SUBTIME(r.moment_representation, '0 01:00:00') FROM projweb_db.representation r;

-- Liste réservations -- 

-- liste toutes les réservations en fonction de l'id_utilisateur

SELECT re.id_representation, r.moment_representation, s.nom_spectacle 
FROM projweb_db.utilisateur c, projweb_db.representation r, projweb_db.spectacle s, projweb_db.reservation re 
WHERE   s.id_spectacle = r.id_spectacle AND r.id_representation = re.id_representation 
							AND re.id_utilisateur = c.id_utilisateur
							AND c.id_utilisateur = ? ;

-- paiement reservation
INSERT INTO projweb_db.ticket (moment_representation) VALUES (CURTIME())
INSERT INTO projweb_db.dossier (moment_representation) VALUES (CURTIME())
INSERT INTO projweb_db.achat (id_representation ,id_place ,id_dossier ,id_ticket ,id_utilisateur) VALUES (?,?,?,?,?)
DELETE FROM projweb_db.reservation where id_reservation = ? 

-- reservation
INSERT INTO projweb_db.reservation (id_representation ,id_place, id_utilisateur) VALUES (?,?,?)

-- achat
INSERT INTO projweb_db.ticket (moment_representation) VALUES (CURTIME())
INSERT INTO projweb_db.dossier (moment_representation) VALUES (CURTIME())
INSERT INTO projweb_db.achat (id_representation ,id_place ,id_dossier ,id_ticket ,id_utilisateur) VALUES (?,?,?,?,?)


-- -------- Coté Admin

-- liste reservations
SELECT * from projweb_db.reservation;

-- liste spectacles

SELECT s.nom_spectacle FROM projweb_db.spectacle s ;
SELECT * FROM projweb_db.spectacle s ;

-- suppression reservation
DELETE FROM projweb_db.reservation where id_reservation = ? 
-- suppression spectacle -> représentations supprimées aussi ok
DELETE FROM projweb_db.spectacle where id_spectacle = ?;

-- - statistiques paaaaas opérationnel
-- spectacle le plus rentable sur la saison

SELECT (s.nom_spectacle, (SUM(recette_i),0)) AS total_recettes
FROM (SELECT (SUM(paye_i),0) AS recette_i
	FROM (SELECT nb_achats*s.base_prix*z.base_pourcentage_prix AS paye_i
		 FROM ((SELECT COUNT(*) 
				FROM projweb_db.achat projweb_db.spectacle s, representation re, projweb_db.place p, projweb_db.zone z
				WHERE p.id_zone = z.id_zone AND p.id_place = a.id_place AND re.id_representation = a.id_representation AND re.id_spectacle = s.id_spectacle) 
		
	GROUP BY s.nom_spectacle)
