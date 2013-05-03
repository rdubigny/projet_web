package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gr15.beans.Spectacle;

public class SpectacleDaoImpl implements SpectacleDao {
    private DAOFactory daoFactory;
    private static final String SQL_SELECT = "SELECT * FROM spectacle";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM spectacle WHERE id_spectacle=?";
   
    SpectacleDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void lister(List<Spectacle> listeSpectacle) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	try {
	    /* R�cup�ration d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT, false);
	    resultSet = preparedStatement.executeQuery();
	    /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
	    while (resultSet.next()) {
		listeSpectacle.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
    }

    @Override
    public Spectacle trouver(String id) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Spectacle spectacle = null;

	try {
	    /* R�cup�ration d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT_PAR_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
	    if (resultSet.next()) {
		spectacle = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
	return spectacle;
    }

    /*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des Spectacle (un ResultSet)
     * et un bean Spectacle.
     */
    private static Spectacle map(ResultSet resultSet) throws SQLException {
	Spectacle spectacle = new Spectacle();
	spectacle.setId(resultSet.getInt("id_spectacle"));
	spectacle.setNom(resultSet.getString("nom_spectacle"));
	spectacle.setBasePrix(resultSet.getFloat("base_prix"));
	return spectacle;
    }

}
