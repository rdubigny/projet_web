package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermetureSilencieuse;
import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import com.gr15.beans.AssociePlaceRepresentation;
import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Zone;
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
			+ "WHERE id_representation = ? AND id_place = ?";
	private static final String SQL_SELECT_ASSOCIER = "SELECT id_representation, id_place "
			+ "FROM projweb_db.reservation WHERE id_reservation=?";
	private static final String SQL_SELECT_SPECTACLE = "SELECT id_spectacle "
			+ "FROM projweb_db.representation " 
			+ "WHERE id_representation = ?";
	private static final String SQL_SELECT_ZONE = "SELECT z.categorie_prix, (s.base_prix*z.base_pourcentage_prix)/100 "
			+ "FROM  projweb_db.spectacle s, projweb_db.zone z "
			+ "WHERE id_spectacle = ?";

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
			/* Récupération d'une connexion depuis la Factory */
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
					 * retourné
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
    // construit l'association des places à acheter en fonction de la
    // representation
    // en fonction d'un tableau d'identifiant qui est considéré non-vide
    // si estReserve vaut faux le parametre idRepresentation ne sert à rien
    public void associer(int[] ids,
	    LinkedList<AssociePlaceRepresentation> associeplacerepresentations,
	    boolean estReserve, int idRepresentation) {
	if (!estReserve)
	    for (int id : ids)
		// achat de places direct sans reservation préalable
		// (représentation unique)
		// ici id est un id_place
		associeplacerepresentations.add(new AssociePlaceRepresentation(
			idRepresentation, id));
	else {
	    // achat de places avec reservation préalable (représentation
	    // peuvent être différent)
	    // ici id est un id_reservation
	    // on recupère id_place et id_representation associés
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
		connexion = daoFactory.getConnection();
		for (int id : ids) {
		    try {
			preparedStatement = initialisationRequetePreparee(
				connexion, SQL_SELECT_ASSOCIER, false, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
			    associeplacerepresentations
				    .add(new AssociePlaceRepresentation(
					    resultSet
						    .getInt("id_representation"),
					    resultSet.getInt("id_place")));
			} else
			    throw new DAOException(
				    "la réservation n'a pas été trouvée");
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
    }

    @Override
    // Actes d'achat
    // Au niveau des requêtes
    // En une seule transaction :
    // 1. création d'un dossier
    // 2. création des tickets avec moment de la représentation
    // 3. création des achats avec id de la représentation
    // 4. si estReserve vaut vrai supprime les reservations
    public void acheter(int idUtilisateur,
	    LinkedList<AssociePlaceRepresentation> associeplacerepresentations,
	    List<Ticket> tickets, boolean estReserve) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;
	int idDossier;
	int idTicket;

	try {
	    connexion = daoFactory.getConnection();
	    // debut de la transaction
	    connexion.setAutoCommit(false);
	    // 1. création d'un dossier
	    try {
		preparedStatement = initialisationRequetePreparee(connexion,
			SQL_CREATION_DOSSIER, true);
		int statut = preparedStatement.executeUpdate();
		if (statut == 0)
		    throw new DAOException(
			    "Erreur lors de la création de dossier, aucun dossier n'a été crée.");
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

	    // En bouclant sur la liste associeplacerepresentations
	    for (int i = 0; i < associeplacerepresentations.size(); i++) {
		// 2. création d'un tickets avec moment de la représentation
		// 3. création d'un achats avec id de la représentation et de la
		// place
		// création d'un ticket
		try {
		    DateTime curDate = new DateTime();
		    preparedStatement = initialisationRequetePreparee(
			    connexion, SQL_CREATION_TICKET, true,
			    curDate.toDate());
		    int statut = preparedStatement.executeUpdate();
		    if (statut == 0)
			throw new DAOException(
				"Erreur lors de la création de ticket, aucun ticket n'a été émis.");
		    Ticket ticket = new Ticket();
		    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		    if (valeursAutoGenerees.next()) {
			idTicket = valeursAutoGenerees.getInt(1);
			ticket.setId(idTicket);
			/* la ligne suivante a peu de chances de marcher */
			ticket.setDate(curDate);
			tickets.add(ticket);
		    } else
			throw new DAOException(
				"Erreur lors de la création de ticket, aucun numéro de série n'a été généré.");
		} catch (SQLException e) {
		    throw new DAOException(e);
		} finally {
		    fermetureSilencieuse(valeursAutoGenerees);
		    fermetureSilencieuse(preparedStatement);
		}

		// création d'un achat
		try {
		    preparedStatement = initialisationRequetePreparee(
			    connexion, SQL_ACHAT, true,
			    associeplacerepresentations.get(i)
				    .getIdRepresentation(),
			    associeplacerepresentations.get(i).getIdPlace(),
			    idDossier, idTicket, idUtilisateur);
		    int statut = preparedStatement.executeUpdate();
		    if (statut == 0)
			throw new DAOException(
				"Erreur lors de l'achat, l'achat n'a pas été enregistré.");
		} catch (MySQLIntegrityConstraintViolationException e) {
		    // TODO supprimer la ligne suivante
		    e.printStackTrace();
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
				associeplacerepresentations.get(i)
					.getIdRepresentation(),
				associeplacerepresentations.get(i).getIdPlace());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0)
			    throw new DAOException(
				    "Erreur la reservation n'a pas ete supprimee.");
		    } catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermetureSilencieuse(connexion);
	}

	@Override
	// un utilisateur réserve pour une représentation donnée
	public void reserver(int idUtilisateur, int idRepresentation, int[] idPlaces) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			connexion.setAutoCommit(false);
			for (int i = 0; i < idPlaces.length; i++) {
				try {
					preparedStatement = initialisationRequetePreparee(
							connexion, SQL_RESERVATION, true, idRepresentation,
							idPlaces[i], idUtilisateur);
					int statut = preparedStatement.executeUpdate();
					if (statut == 0)
						throw new DAOException(
								"échec de la réservation, votre réservation n'a pas été enregistrée.");
					valeursAutoGenerees = preparedStatement.getGeneratedKeys();
					if (!valeursAutoGenerees.next())
						throw new DAOException(
								"échec de la réservation, aucun numéro de réservation n'a été généré.");
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
							"échec de la réservation, reservation(s) annulée(s).");
				} catch (SQLException exp) {
					throw new DAOException(exp);
				}
			}
			throw new DAOException(e);
		} finally {
			fermetureSilencieuse(connexion);
		}
	}


	public void listerZone(List<Zone> zones, int idRepresentation) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int idSpectacle = 0;
		try {
			connexion = daoFactory.getConnection();
			/* récupération de l'idSpectacle à partir de la representation */
			preparedStatement = initialisationRequetePreparee(
					connexion, SQL_SELECT_SPECTACLE, false, idRepresentation);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){
				idSpectacle = resultSet.getInt(1);
			} 
			preparedStatement = initialisationRequetePreparee(connexion,
					SQL_SELECT_ZONE, false, idSpectacle);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				zones.add(new Zone(resultSet.getString(1), resultSet.getFloat(2)));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermetureSilencieuse(resultSet);
			fermetureSilencieuse(preparedStatement);
			fermetureSilencieuse(connexion);
		}
	}
	
}
