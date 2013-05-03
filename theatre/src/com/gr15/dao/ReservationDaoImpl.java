package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Reservation;

public class ReservationDaoImpl implements ReservationDao {

    private DAOFactory daoFactory;
    private static final String SQL_SELECT_RESERVATIONS_USER = "SELECT re.id_representation, r.moment_representation, s.nom_spectacle"
	    + "FROM projweb_db.utilisateur c, projweb_db.representation r, projweb_db.spectacle s, projweb_db.reservation re"
	    + "WHERE   s.id_spectacle = r.id_spectacle AND r.id_representation = re.id_representation"
	    + "AND re.id_utilisateur = c.id_utilisateur"
	    + "AND c.id_utilisateur = ?";

    // Le constructeur
    public ReservationDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public List<Reservation> lister(int idClient) {
	return null;

    }

    @Override
    public void annuler(int idReservation) {

    }

}
