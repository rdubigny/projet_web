package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Utilisateur;

public class PlaceDaoImpl implements PlaceDao {
    private DAOFactory daoFactory;
    private static final String SQL_ZONES = "SELECT id_place, numero_rang, numero_siege, id_zone FROM place";
    private static final String SQL_SELECT_PLACE_OCCUPEES = "select p.numero_rang , p.numero_siege "
	    + "from projweb_db.place p, projweb_db.achat a, projweb_db.reservation r "
	    + "where a.id_place = p.id_place OR r.id_place = p.id_place";
    private static final String SQL_ACHAT = "";
    private static final String SQL_RESERVATION = "INSERT INTO "
	    + "projweb_db.reservation (id_representation, id_place, id_utilisateur)"
	    + " VALUES (?,?,?);";

    PlaceDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public Place[][] genererPlan() {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	/*
	 * WARNING : les dimensions du tableau sont implémentées de manière
	 * statique, c'est mal.
	 */
	Place[][] res = new Place[20][30];
	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_ZONES, false);
	    resultSet = preparedStatement.executeQuery();
	    /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	    while (resultSet.next()) {
		res[resultSet.getInt("numero_rang") - 1][resultSet
			.getInt("numero_siege") - 1] = new Place(
			resultSet.getInt("id_place"),
			resultSet.getInt("id_zone"));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
	return res;
    }

    @Override
    public void updateDisponibilite(Place[][] matricePlace) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_SELECT_PLACE_OCCUPEES, false);
	    resultSet = preparedStatement.executeQuery();
	    /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	    while (resultSet.next()) {
		matricePlace[resultSet.getInt("numero_rang") - 1][resultSet
			.getInt("numero_siege") - 1].setOccupe();
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
    }

    @Override
    public void reserver(Utilisateur utilisateur,
	    Representation representation, String[] ids, boolean achat) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    connexion.setAutoCommit(false);
	    for (String s : ids) {
		if (achat)
		    preparedStatement = initialisationRequetePreparee(
			    connexion, SQL_ACHAT, false);
		else {
		    preparedStatement = initialisationRequetePreparee(
			    connexion, SQL_RESERVATION, false,
			    representation.getId(), s, utilisateur.getId());
		    resultSet = preparedStatement.executeQuery();
		    /*
		     * Parcours de la ligne de données de l'éventuel ResulSet
		     * retourné
		     */
		    while (resultSet.next()) {
			// matricePlace[resultSet.getInt("numero_rang") -
			// 1][resultSet
			// .getInt("numero_siege") - 1].setOccupe();
		    }
		}
	    }
	    connexion.commit();
	} catch (SQLException e) {
	    // if (con != null) {
	    // try {
	    // con.rollback();
	    // } catch (SQLException ex1) {
	    // Logger lgr = Logger.getLogger(Transaction.class.getName());
	    // lgr.log(Level.WARNING, ex1.getMessage(), ex1);
	    // }
	    // }
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}
    }

    // inspiration
    // Connection con = null;
    // Statement st = null;
    //
    // String url = "jdbc:mysql://localhost:3306/testdb";
    // String user = "testuser";
    // String password = "test623";
    //
    // try {
    //
    // con = DriverManager.getConnection(url, user, password);
    // st = con.createStatement();
    //
    // con.setAutoCommit(false);
    //
    // st.executeUpdate("UPDATE Authors SET Name = 'Leo Tolstoy' "
    // + "WHERE Id = 1");
    // st.executeUpdate("UPDATE Books SET Title = 'War and Peace' "
    // + "WHERE Id = 1");
    // st.executeUpdate("UPDATE Books SET Titl = 'Anna Karenina' "
    // + "WHERE Id = 2");
    //
    // con.commit();
    //
    // } catch (SQLException ex) {
    //
    // if (con != null) {
    // try {
    // con.rollback();
    // } catch (SQLException ex1) {
    // Logger lgr = Logger.getLogger(Transaction.class.getName());
    // lgr.log(Level.WARNING, ex1.getMessage(), ex1);
    // }
    // }
    //
    // Logger lgr = Logger.getLogger(Transaction.class.getName());
    // lgr.log(Level.SEVERE, ex.getMessage(), ex);
    //
    // } finally {
    //
    // try {
    // if (st != null) {
    // st.close();
    // }
    // if (con != null) {
    // con.close();
    // }
    //
    // } catch (SQLException ex) {
    //
    // Logger lgr = Logger.getLogger(Transaction.class.getName());
    // lgr.log(Level.WARNING, ex.getMessage(), ex);
    // }
    // }
    // }
}
