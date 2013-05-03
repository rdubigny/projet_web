package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermetureSilencieuse;
import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Utilisateur;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class PlaceDaoImpl implements PlaceDao {
    private DAOFactory daoFactory;
    private static final String SQL_ZONES = "SELECT id_place, numero_rang, numero_siege, id_zone FROM place";
    private static final String SQL_SELECT_PLACES_RESERVEES = "SELECT p.numero_rang , p.numero_siege "
	    + "FROM projweb_db.place p, projweb_db.reservation r "
	    + "WHERE r.id_place = p.id_place AND r.id_representation=?";
    private static final String SQL_SELECT_PLACES_ACHETEES = "SELECT p.numero_rang , p.numero_siege "
	    + "FROM projweb_db.place p, projweb_db.achat a "
	    + "WHERE a.id_place = p.id_place AND a.id_representation=?";
    private static final String SQL_CREATION_TICKET = "INSERT INTO projweb_db.ticket (moment_vente) "
	    + "VALUES (?)";// (CURTIME())";
    private static final String SQL_CREATION_DOSSIER = "INSERT INTO projweb_db.dossier () VALUES ()";
    private static final String SQL_ACHAT = "INSERT INTO projweb_db.achat "
	    + "(id_representation ,id_place ,id_dossier ,id_ticket ,id_utilisateur) VALUES (?,?,?,?,?)";
    private static final String SQL_RESERVATION = "INSERT INTO "
	    + "projweb_db.reservation (id_representation, id_place, id_utilisateur)"
	    + " VALUES (?,?,?);";
    private static final String SQL_SUPPRESSION_RESERVATION = "DELETE FROM projweb_db.reservation "
	    + "WHERE id_representation = ? AND id_place = ?;";

    PlaceDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public Place[][] genererPlan() {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	/*
	 * WARNING : les dimensions du tableau sont impl�ment�es de mani�re
	 * statique, c'est mal.
	 */
	Place[][] res = new Place[20][30];
	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion,
		    SQL_ZONES, false);
	    resultSet = preparedStatement.executeQuery();
	    /* Parcours de la ligne de donn�es de l'éventuel ResulSet retourné */
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
    public void updateDisponibilite(Place[][] matricePlace,
	    Representation representation) {
	for (Place[] i : matricePlace) {
	    for (Place j : i) {
		j.setLibre();
	    }
	}
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    /* R�cup�ration d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    for (int i = 0; i < 2; i++) {
		try {
		    switch (i) {
		    case 0:
			preparedStatement = initialisationRequetePreparee(
				connexion, SQL_SELECT_PLACES_RESERVEES, false,
				representation.getId());
			break;
		    case 1:
			preparedStatement = initialisationRequetePreparee(
				connexion, SQL_SELECT_PLACES_ACHETEES, false,
				representation.getId());
			break;
		    default:
		    }
		    resultSet = preparedStatement.executeQuery();
		    /*
		     * Parcours de la ligne de données de l'éventuel ResulSet
		     * retourn�
		     */
		    while (resultSet.next()) {
			matricePlace[resultSet.getInt("numero_rang") - 1][resultSet
				.getInt("numero_siege") - 1].setOccupe();
		    }
		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermetureSilencieuse(resultSet);
		    fermetureSilencieuse(preparedStatement);
		}
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermetureSilencieuse(connexion);
	}
    }

    @Override
    public void reserver(int idUtilisateur,
	    int idRepresentation, String[] ids) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;
	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    connexion.setAutoCommit(false);
	    for (String s : ids) {
		try {
		    preparedStatement = initialisationRequetePreparee(
			    connexion, SQL_RESERVATION, true,
			    idRepresentation, s, idUtilisateur);
		    int statut = preparedStatement.executeUpdate();
		    if (statut == 0)
			throw new DAOException(
				"échec de la réservation, votre r�servation n'a pas été enregistrée.");
		    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		    if (!valeursAutoGenerees.next())
			throw new DAOException(
				"échec de la réservation, aucun num�ro de r�servation n'a été généré.");
		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermetureSilencieuse(valeursAutoGenerees);
		    fermetureSilencieuse(preparedStatement);
		}
	    }
	    connexion.commit();
	} catch (SQLException e) {
	    if (connexion != null) {
		try {
		    connexion.rollback();
		    throw new DAOException(
			    "échec de la réservation, la réservation ont été annulée.");
		} catch (SQLException exp) {
		    throw new DAOException(exp);
		}
	    }
	    throw new DAOException(e);
	} finally {
	    fermetureSilencieuse(connexion);
	}
    }

    @Override
    public void acheter(int idUtilisateur, int idRepresentation,
	    String[] ids, List<Ticket> tickets, boolean estReserve) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;
	int idDossier;
	int idTicket;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    connexion.setAutoCommit(false);
	    /* cr�ation dossier */
	    try {
		preparedStatement = initialisationRequetePreparee(connexion,
			SQL_CREATION_DOSSIER, true);
		int statut = preparedStatement.executeUpdate();
		if (statut == 0)
		    throw new DAOException(
			    "Erreur lors de la cr�ation de dossier, aucun dossier n'a été crée.");
		valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		if (valeursAutoGenerees.next()) {
		    idDossier = valeursAutoGenerees.getInt(1);
		} else
		    throw new DAOException(
			    "Erreur lors de la création de dossier, aucun numéro de série n'a été généré.");
	    } catch (SQLException e) {
		throw new DAOException(e);
	    } finally {
		fermetureSilencieuse(valeursAutoGenerees);
		fermetureSilencieuse(preparedStatement);
	    }

	    /* parcours de la liste des tickets */

	    for (String s : ids) {

		/* cr�ation tickets */

		try {
		    DateTime curDate = new DateTime();
		    preparedStatement = initialisationRequetePreparee(
			    connexion, SQL_CREATION_TICKET, true,
			    curDate.toDate());
		    int statut = preparedStatement.executeUpdate();
		    if (statut == 0)
			throw new DAOException(
				"Erreur lors de la cr�ation de ticket, aucun ticket n'a été émis.");
		    Ticket ticket = new Ticket();
		    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		    if (valeursAutoGenerees.next()) {
			idTicket = valeursAutoGenerees.getInt(1);
			ticket.setId(idTicket);
			/* la ligne suivante � peu de chances de marcher */
			ticket.setDate(curDate);
			tickets.add(ticket);
		    } else
			throw new DAOException(
				"Erreur lors de la cr�ation de ticket, aucun numéro de série n'a été généré.");
		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermetureSilencieuse(valeursAutoGenerees);
		    fermetureSilencieuse(preparedStatement);
		}

		/* achat de la place */

		try {
		    preparedStatement = initialisationRequetePreparee(
			    connexion, SQL_ACHAT, true, idRepresentation,
			    s, idDossier, idTicket, idUtilisateur);
		    int statut = preparedStatement.executeUpdate();
		    if (statut == 0)
			throw new DAOException(
				"Erreur lors de l'achat, l'achat n'a pas été enregistré.");
		} catch (MySQLIntegrityConstraintViolationException e) {
		    throw new DAOException(
			    "Une des places que vous avez sélectionnées a déjà été réservée. Veuillez recommencez votre choix.");
		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermetureSilencieuse(valeursAutoGenerees);
		    fermetureSilencieuse(preparedStatement);
		}

		/* suppression des reservations si elle(s) existent */
		if (estReserve) {
		    try {
			preparedStatement = initialisationRequetePreparee(
				connexion, SQL_SUPPRESSION_RESERVATION, true,
				idRepresentation, s);
			int statut = preparedStatement.executeUpdate();
			if (statut == 0)
			    throw new DAOException("Erreur la reservation n'a pas ete supprimee.");
		    } catch (SQLException e) {
			throw new DAOException(e);
		    } finally {
			fermetureSilencieuse(valeursAutoGenerees);
			fermetureSilencieuse(preparedStatement);
		    }

		}

	    } // fin du for s : ids tableau de id_place en String

	    connexion.commit();
	} catch (SQLException e) {
	    if (connexion != null) {
		try {
		    connexion.rollback();
		    throw new DAOException(
			    "Erreur lors de l'achat, l'achat a été annulé.");
		} catch (SQLException exp) {
		    throw new DAOException(exp);
		}
	    }
	} finally {
	    fermetureSilencieuse(connexion);
	}
    }
    // notes encore utiles
    // TODO a supprimer
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
