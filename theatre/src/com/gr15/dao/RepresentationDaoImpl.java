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
    private static final String SQL_SELECT = "SELECT * FROM representation WHERE id_spectacle=?";

    RepresentationDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void listerParSpectacle(long idSpectacle,
	    List<Representation> listeRepresentation) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT, false, idSpectacle);
	    resultSet = preparedStatement.executeQuery();
	    /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	    while (resultSet.next()) {
		// TODO Vérifier que la date n'est pas passée moins une heure
		listeRepresentation.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des Représentation (un
     * ResultSet) et un bean Représentation.
     */
    private static Representation map(ResultSet resultSet) throws SQLException {
	Representation representation = new Representation();
	representation.setId(resultSet.getLong("id_representation"));
	representation.setIdSpectacle(resultSet.getLong("id_spectacle"));
	representation.setDate(new DateTime(resultSet
		.getDate("moment_representation")));
	return representation;
    }

}
