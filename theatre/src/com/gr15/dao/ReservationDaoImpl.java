package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermetureSilencieuse;
import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;
import com.gr15.dao.PlaceDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.gr15.beans.Reservation;

public class ReservationDaoImpl implements ReservationDao {

	private DAOFactory daoFactory;

	private static final String SQL_DELETE_RESERVATION_USER = "DELETE FROM projweb_db.reservation WHERE id_utilisateur = ? AND id_reservation = ?";

	private static final String SQL_DELETE_RESERVATION_ADMIN = "DELETE FROM projweb_db.reservation WHERE  id_reservation = ?";
	
	private static final String SQL_SELECT_RESERVATION = "SELECT rs.id_reservation, rp.moment_representation, "
			+ "s.nom_spectacle, p.numero_rang, p.numero_siege, z.categorie_prix,"
			+ "CAST((s.base_prix*z.base_pourcentage_prix)/100 AS DECIMAL(8,2))"
			+ "FROM  projweb_db.reservation rs, projweb_db.representation rp, projweb_db.spectacle s, projweb_db.place p, "
			+ "projweb_db.zone z WHERE rs.id_place = p.id_place AND rs.id_representation = rp.id_representation "
			+ "AND rp.id_spectacle = s.id_spectacle AND p.id_zone = z.id_zone AND rs.id_utilisateur = ?";

	private static final String SQL_SELECT_RESERVATION_ADMIN = "SELECT rs.id_reservation, u.nom, u.prenom, rp.moment_representation, "
			+ "s.nom_spectacle, p.numero_rang, p.numero_siege, z.categorie_prix,(s.base_prix*z.base_pourcentage_prix)/100 FROM  "
			+ "projweb_db.reservation rs, projweb_db.utilisateur u, projweb_db.representation rp, projweb_db.spectacle s, projweb_db.place p,"
			+ " projweb_db.zone z WHERE rs.id_place = p.id_place "
			+ "AND rs.id_representation = rp.id_representation "
			+ "AND rs.id_utilisateur = u.id_utilisateur "
			+ "AND rp.id_spectacle = s.id_spectacle "
			+ "AND p.id_zone = z.id_zone group by rs.id_reservation "
			+ "order by rp.moment_representation";

	// Le constructeur
	public ReservationDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	private Reservation mapAdmin(ResultSet resultset) throws SQLException {
		Reservation reservation = new Reservation();
		reservation.setId(resultset.getInt("id_reservation"));
		reservation.setSpectacle(resultset.getString("nom_spectacle"));
		reservation.setRang(resultset.getInt("numero_rang"));
		reservation.setSiege(resultset.getInt("numero_siege"));
		reservation.setZone(resultset.getString("categorie_prix"));
		reservation.setPrix(resultset.getFloat(9));
		reservation.setPrenomClient(resultset.getString("prenom"));
		reservation.setNomClient(resultset.getString("nom"));
		reservation.setDate(new DateTime(resultset
				.getTimestamp("moment_representation")));

		return reservation;
	}

	// annulation par un utilisateur
	// il ne peut supprimer que ses réservations 
	@Override
	public void annulerReservation(int idUtilisateur, int idReservation) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_RESERVATION_USER, false, idUtilisateur, idReservation);
			statut = preparedStatement.executeUpdate();
			if (statut == 0)
				throw new DAOException(
						"Erreur la reservation n'a pas ete supprimee.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermetureSilencieuse(preparedStatement);
			fermetureSilencieuse(connexion);
		}
	}
	
	// annulation par le responsable
	// il peut supprimer n'importe qu'elle reservation d'un utilisateur quelconque
	public void annulerReservation(int  idReservation ){
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut = 0;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_DELETE_RESERVATION_ADMIN, false, idReservation);
			statut = preparedStatement.executeUpdate();
			if (statut == 0)
				throw new DAOException(
						"Erreur la reservation n'a pas ete supprimee.");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermetureSilencieuse(preparedStatement);
			fermetureSilencieuse(connexion);
		}
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

	public void lister(List<Reservation> listeReservation) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_RESERVATION_ADMIN, false);
			resultset = preparedStatement.executeQuery();
			/*
			 * Parcours de la ligne de données de l'éventuel ResulSet retourné
			 */
			while (resultset.next()) {
				listeReservation.add(mapAdmin(resultset));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultset, preparedStatement, connexion);
		}
	}

	@Override
	public void listerParReservation(int idUtilisateur,
			List<Reservation> listeReservation) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_RESERVATION, false, idUtilisateur);
			resultset = preparedStatement.executeQuery();
			/*
			 * Parcours de la ligne de données de l'éventuel ResulSet retourné
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

}
