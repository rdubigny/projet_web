package com.gr15.form;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gr15.beans.AssociePlaceRepresentation;
import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOException;
import com.gr15.dao.PlaceDao;

public class PlaceForm {
    public static final String PARAM_PLACE_ID = "id";
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_RESERVATION = "reservation";
    public static final String PARAM_PAIEMENT = "paiement";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_REPRESENTATION = "representation";

    private String resultat;
    private String erreur = null;
    private PlaceDao placeDao;

    public PlaceForm(PlaceDao placeDao) {
	this.placeDao = placeDao;
    }

    public String getResultat() {
	return resultat;
    }

    public String getErreur() {
	return erreur;
    }

    // Nom à changer de la fonction
    public List<Ticket> reserver(HttpServletRequest request) {
	List<Ticket> listeTickets = new ArrayList<Ticket>();

	/* boolean spécifiant s'il s'agit d'une réservation */
	boolean isReservation = PARAM_RESERVATION.equals(request
		.getParameter(PARAM_ACTION));
	boolean estReserve = PARAM_PAIEMENT.equals(request
		.getParameter(PARAM_ACTION));

	/* récupération des places sélectionnées en String et convertion en int */
	String[] idss = request.getParameterValues(PARAM_PLACE_ID);

	try {

	    if (idss == null) {
		erreur = "Veuillez sélectionner au moins une place pour procéder à l'achat";
	    } else {
		HttpSession session = request.getSession();
		// recupération de l'identificateur de l'utilisateur connecté
		int idUtilisateur = ((Utilisateur) session
			.getAttribute(ATT_SESSION_USER)).getId();
		// recupération de l'identificateur de la représentation choisie
		int idRepresentation = 0;
		if (((Representation) session.getAttribute(ATT_REPRESENTATION)) != null)
		    idRepresentation = ((Representation) session
			    .getAttribute(ATT_REPRESENTATION)).getId();
		// recupération d'un tableau d'entier contenant
		// les identificateurs de place
		int[] ids = new int[idss.length];
		for (int i = 0; i < idss.length; i++) {
		    ids[i] = Integer.parseInt(idss[i]);
		}

		if (!estReserve || isReservation) {
		    // vérification du nombre de place disponibles
		    // cette solution ne gère pas les accès concurentiels
		}

		/* enregistrement de la réservation, de l'achat ou du paiement */
		if (isReservation) {
		    placeDao.reserver(idUtilisateur, idRepresentation, ids);
		    listeTickets = null;
		} else {
		    // si de reservationsClients/ -> confirmation/ estReserve =
		    // true
		    // si de choixPlace/ -> confirmation/ estReserve = false
		    LinkedList<AssociePlaceRepresentation> associePlaceRepresentations = new LinkedList<AssociePlaceRepresentation>();
		    placeDao.associer(ids, associePlaceRepresentations,
			    estReserve, idRepresentation);
		    placeDao.acheter(idUtilisateur,
			    associePlaceRepresentations, listeTickets,
			    estReserve);
		}
	    }
	    if (erreur == null) {
		if (idss.length > 1)
		    resultat = isReservation ? "Vos places ont été réservées avec succès"
			    : "Vos places ont été achetées avec succés";
		else
		    resultat = isReservation ? "Votre place a été réservée avec succès"
			    : "Votre place a été achetée avec succés";
	    } else {
		resultat = isReservation ? "Echec de la réservation"
			: "Echec de l'achat";
	    }
	} catch (DAOException e) {
	    resultat = "Echec de l'opération : une erreur imprévue est survenue.";
	    erreur = e.getMessage();
	    e.printStackTrace();
	}

	return listeTickets;
    }
}
