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

-- Liste spectacles j'ai mis juste nom spectacle parce que je crois que c'est le seul n�cessaire ici

SELECT s.nom_spectacle FROM projweb_db.spectacle s ;

SELECT * FROM projweb_db.spectacle s ;

-- ------------- Liste representations

-- Liste representations renvoie toutes les repr�sentations futures si l'utilisateur est de type guichet, 
-- les repr�sentations futures se d�roulant dans moins d'une heure si l'utilisateur est de type client

SELECT s.nom_spectacle, r.moment_representation 
	FROM projweb_db.representation r, projweb_db.utilisateur u, projweb_db.spectacle s
	WHERE s.id_spectacle = r.id_spectacle 
		AND s.id_spectacle = ?
		AND u.id_utilisateur = ? 
		AND CURTIME() < r.moment_representation 
		AND ((u.type_utilisateur = 'client' AND CURTIME() < SUBTIME(r.moment_representation, '0 01:00:00')
				 OR (u.type_utilisateur = 'guichet')));

-- Liste r�servations -- 

-- liste toutes les r�servations en fonction de l'id_utilisateur
SELECT re.id_representation, r.moment_representation, s.nom_spectacle, p.numero_rang, p.numero_siege, p.id_place, 
z.categorie_prix, z.base_pourcentage_prix
FROM projweb_db.utilisateur u,projweb_db.place p, projweb_db.representation r, projweb_db.spectacle s,
 projweb_db.reservation re , projweb_db.zone z 
WHERE z.id_zone = p.id_zone and s.id_spectacle = r.id_spectacle AND r.id_representation = re.id_representation 
							AND re.id_utilisateur = u.id_utilisateur
							AND u.id_utilisateur = ? and p.id_place = re.id_place and p.id_zone = z.id_zone GROUP BY r.id_representation;

-- version Gilles après qu'on ait parlé de l'affichage reservationsClient.jsp

-- liste toutes les r�servations en fonction de l'id_utilisateur
SELECT rs.id_reservation, rp.moment_representation, s.nom_spectacle, p.numero_rang, p.numero_siege, z.categorie_prix,(s.base_prix*z.base_pourcentage_prix)/100
FROM  projweb_db.reservation rs, projweb_db.representation rp, projweb_db.spectacle s, projweb_db.place p, projweb_db.zone z
WHERE rs.id_place = p.id_place AND rs.id_representation = rp.id_representation
                               AND rp.id_spectacle = s.id_spectacle
                               AND p.id_zone = z.id_zone
                               AND rs.id_utilisateur = ?;
-- GROUP BY rp.id_representation; trier de la plus récente à la plus veille pas rapport à l'rp.id_representation mais au rp.moment_representation

-- suppression reservation réecrite me demander pourquoi
DELETE FROM projweb_db.reservation WHERE id_representation = ? AND id_place = ?;

-- fin gilles                               
							
-- paiement reservation
INSERT INTO projweb_db.ticket (moment_vente) VALUES (CURTIME());
INSERT INTO projweb_db.dossier (); -- Gilles pense que c'est "INSERT INTO projweb_db.dossier VALUES ();"


INSERT INTO projweb_db.achat (id_representation ,id_place ,id_dossier ,id_ticket ,id_utilisateur) VALUES (?,?,?,?,?)
DELETE FROM projweb_db.reservation where id_reservation = ? 

-- reservation
INSERT INTO projweb_db.reservation (id_representation ,id_place, id_utilisateur) VALUES (?,?,?)

-- achat
INSERT INTO projweb_db.ticket (moment_representation) VALUES (CURTIME())
INSERT INTO projweb_db.dossier (moment_representation) VALUES (CURTIME())
INSERT INTO projweb_db.achat (id_representation ,id_place ,id_dossier ,id_ticket ,id_utilisateur) VALUES (?,?,?,?,?)


-- -------- Cot� Admin

-- liste reservations
SELECT * from projweb_db.reservation;

-- liste representations ordre chronologique 

SELECT s.nom_spectacle, r.moment_representation FROM projweb_db.spectacle s, projweb_db.representation r 
WHERE r.id_spectacle = s.id_spectacle ORDER BY r.moment_representation;

-- suppression reservation
DELETE FROM projweb_db.reservation where id_reservation = ? -- Gilles pense qu'il manque ;
-- suppression spectacle -> repr�sentations supprim�es aussi ok
DELETE FROM projweb_db.spectacle where id_spectacle = ?;

-- - statistiques paaaaas op�rationnel
-- TOTAL PLACES VENDUES 
select count(*) from projweb_db.achat a ; 
-- TOTAL PLACES VENDUES PAR SPECTACLE
select s.nom_spectacle,count(*) from projweb_db.achat a , projweb_db.spectacle s, projweb_db.representation r
 where a.id_representation = r.id_representation and r.id_spectacle = s.id_spectacle group by s.nom_spectacle; 

-- TOTAL recette par spectacle
select s.nom_spectacle,sum(s.base_prix*z.base_pourcentage_prix/100) from projweb_db.zone z, projweb_db.place p, projweb_db.achat a , projweb_db.spectacle s, projweb_db.representation r
 where a.id_representation = r.id_representation and
 r.id_spectacle = s.id_spectacle and  p.id_zone = z.id_zone and a.id_place = p.id_place group by s.nom_spectacle; 

-- spectacle le plus rentable sur la saison

 -- spectacle le plus rentable sur la saison
select s.nom_spectacle from projweb_db.zone z, projweb_db.place p, projweb_db.achat a , projweb_db.spectacle s, projweb_db.representation r
 where a.id_representation = r.id_representation and
 r.id_spectacle = s.id_spectacle and  p.id_zone = z.id_zone and a.id_place = p.id_place group by s.nom_spectacle order by  (sum(s.base_prix*z.base_pourcentage_prix/100)) DESC; 

