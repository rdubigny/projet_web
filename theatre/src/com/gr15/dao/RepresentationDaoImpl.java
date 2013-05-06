package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.gr15.beans.Representation;

public class RepresentationDaoImpl implements RepresentationDao {

    private DAOFactory daoFactory;
    private static final String SQL_SELECT_PAR_ID = "SELECT r.id_representation, s.nom_spectacle, r.moment_representation, r.id_spectacle"
	    + " FROM projweb_db.representation r, projweb_db.spectacle s "
	    + "WHERE s.id_spectacle = r.id_spectacle AND r.id_representation=?";

    private static final String SQL_SELECT_REPRESENTATIONS = "SELECT r.id_representation, s.nom_spectacle, r.moment_representation, r.id_spectacle "
	    + "	FROM projweb_db.representation r, projweb_db.utilisateur u, projweb_db.spectacle s"
	    + "	WHERE s.id_spectacle = r.id_spectacle "
	    + "		AND s.id_spectacle = ?"
	    + "		AND u.id_utilisateur = ?"
	    + "		AND CURTIME() < r.moment_representation "
	    + "		AND ((u.type_utilisateur = 'client' AND CURTIME() < SUBTIME(r.moment_representation, '0 01:00:00') "
	    + "				 OR (u.type_utilisateur = 'guichet'))) ORDER BY r.moment_representation";

    private static final String SQL_SELECT_CHRONO = "SELECT r.id_representation, s.nom_spectacle, r.id_spectacle,"
	    + "r.moment_representation FROM projweb_db.spectacle s, projweb_db.representation r "
	    + "WHERE r.id_spectacle = s.id_spectacle ORDER BY r.moment_representation";

    private static final String SQL_DELETE = "DELETE FROM projweb_db.representation where id_representation = ?";

    RepresentationDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    public void lister(List<Representation> listeRepresentation) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT_CHRONO, false);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		listeRepresentation.add(map_responsable(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

    }

    @Override
    public void listerParSpectacle(String idSpectacle, int idUtilisateur,
	    List<Representation> listeRepresentation) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT_REPRESENTATIONS, false, idSpectacle,
		    idUtilisateur);
	    resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
		listeRepresentation.add(map_responsable(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
    }

    @Override
    public Representation trouver(String id) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Representation representation = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT_PAR_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    if (resultSet.next()) {
		representation = map_responsable(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
	return representation;
    }

    @Override
    public void supprimer(String idRepresentation) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_DELETE, true, idRepresentation);
	    int statut = preparedStatement.executeUpdate();
	    if (statut == 0) {
		throw new DAOException(
			"Echec de la suppression du client, aucune ligne supprimée de la table.");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
    }

    private static Representation map_responsable(ResultSet resultSet)

    throws SQLException {
	Representation representation = new Representation();
	representation.setId(resultSet.getInt("id_representation"));
	representation.setIdSpectacle(resultSet.getInt("id_spectacle"));
	representation.setDate(new DateTime(resultSet
		.getTimestamp("moment_representation")));
	representation.setNomSpectacle(resultSet.getString("nom_spectacle"));
	return representation;
    }

}
