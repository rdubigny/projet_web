SET GLOBAL event_scheduler = ON;

CREATE EVENT projweb_db.maj_reservations 
ON SCHEDULE 
EVERY 1 HOUR 
DO 
DELETE FROM projweb_db.reservation WHERE id_representation IN 
(SELECT rep.id_representation FROM projweb_db.reservation res ,projweb_db.representation rep
	WHERE rep.id_representation = res.id_representation AND CURTIME() < SUBTIME(rep.moment_representation, '0 01:00:00')); 
