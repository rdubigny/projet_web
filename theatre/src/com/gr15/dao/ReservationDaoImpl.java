package com.gr15.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gr15.beans.Reservation;

public class ReservationDaoImpl implements ReservationDao {

    private DAOFactory daoFactory;

    private static final String SQL_SELECT_RESERVATION_USER = "SELECT rs.id_reservation, rp.moment_representation, s.nom_spectacle, p.numero_rang, p.numero_siege, z.categorie_prix,(s.base_prix*z.base_pourcentage_prix)/100"
	    + "FROM  projweb_db.reservation rs, projweb_db.representation rp, projweb_db.spectacle s, projweb_db.place p, projweb_db.zone z"
	    + "WHERE rs.id_place = p.id_place AND rs.id_representation = rp.id_representation"
	    + "AND rp.id_spectacle = s.id_spectacle"
	    + "AND p.id_zone = z.id_zone" + "AND rs.id_utilisateur = ?";

    // Le constructeur
    public ReservationDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping)
     */
    private Reservation map(ResultSet resultset) throws SQLException {
	Reservation reservation = new Reservation();
	reservation.setId(resultset.getInt(1));
	reservation.setRepresentation(resultset.getString(2));
	reservation.setSpectacle(resultset.getString(3));
	reservation.setRang(resultset.getInt(4));
	reservation.setSiege(resultset.getInt(5));
	reservation.setZone(resultset.getString(6));
	reservation.setPrix(resultset.getFloat(7));
	return reservation;
    }

    @Override
    public void listerParReservation(long idUtilisateur,
	    List<Reservation> listeReservation) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultset = null;
	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT_RESERVATION_USER, false, idUtilisateur);
	    resultset = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet
	     * retourné
	     */
	    while (resultset.next()) {
		listeReservation.add(map(resultset));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultset, preparedStatement, connexion);
	}

    }

    @Override
    public void annuler(int idReservation) {

    }

}
