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

-- Liste representations

SELECT s.nom_spectacle, r.moment_representation FROM projweb_db.representation r, projweb_db.spectacle s
 WHERE s.id_spectacle = r.id_spectacle AND CURTIME() < SUBTIME(r.moment_representation, '0 01:00:00');
SELECT SUBTIME(r.moment_representation, '0 01:00:00') FROM projweb_db.representation r;

-- Liste réservations

SELECT * FROM projweb_db.reservation;

SELECT re.id_representation, r.moment_representation, s.nom_spectacle 
FROM projweb_db.utilisateur c, projweb_db.representation r, projweb_db.spectacle s, projweb_db.reservation re 
WHERE   s.id_spectacle = r.id_spectacle AND r.id_representation = re.id_representation 
							AND re.id_utilisateur = c.id_utilisateur
							AND c.id_utilisateur = ? ;
