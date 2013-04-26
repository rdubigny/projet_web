package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gr15.beans.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {
    private DAOFactory daoFactory;
    private static final String SQL_SELECT_PAR_LOGIN = "SELECT login, nom, prenom, email, mot_de_passe FROM Utilisateur WHERE login = ?";

    UtilisateurDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public Utilisateur trouver(String login) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Utilisateur utilisateur = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT_PAR_LOGIN, false, login);
	    resultSet = preparedStatement.executeQuery();
	    /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	    if (resultSet.next()) {
		utilisateur = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return utilisateur;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Utilisateur map(ResultSet resultSet) throws SQLException {
	Utilisateur utilisateur = new Utilisateur();
	utilisateur.setLogin(resultSet.getString("login"));
	utilisateur.setNom(resultSet.getString("nom"));
	utilisateur.setPrenom(resultSet.getString("prenom"));
	utilisateur.setEmail(resultSet.getString("email"));
	utilisateur.setMotdepasse(resultSet.getString("mot_de_passe"));
	return utilisateur;
    }

    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public void setEstAdmin(Utilisateur utilisateur) throws DAOException {
	// TODO Auto-generated method stub

    }
}
