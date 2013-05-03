package com.gr15.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOException;
import com.gr15.dao.PlaceDao;

public class PlaceForm {
    public static final String PARAM_PLACE_ID = "id";
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_RESERVATION = "reservation";
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

    public List<Ticket> reserver(HttpServletRequest request) {
	List<Ticket> listeTickets = new ArrayList<Ticket>();

	/* boolean spécifiant s'il s'agit d'une r�servation */
	boolean isReservation = PARAM_RESERVATION.equals(request
		.getParameter(PARAM_ACTION));

	/* r�cup�ration des places s�lectionn�es */
	String[] ids = request.getParameterValues(PARAM_PLACE_ID);

	try {

	    if (ids == null) {
		erreur = "Veuillez s�lectionner au moins une place pour proc�der � l'achat";
	    } else {

		/*
		 * r�cup�ration de l'utilisateur et de la repr�sentation en
		 * s�lection
		 */
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session
			.getAttribute(ATT_SESSION_USER);
		Representation representation = (Representation) session
			.getAttribute(ATT_REPRESENTATION);

		/* enregistrement de la r�servation ou de l'achat */
		if (isReservation) {
		    placeDao.reserver(utilisateur, representation, ids);
		    listeTickets = null;
		} else
		    placeDao.acheter(utilisateur, representation, ids,
			    listeTickets, false);
	    }

	    if (erreur == null) {
		if (ids.length > 1)
		    resultat = isReservation ? "Vos places ont �t� r�serv�es avec succ�s"
			    : "Vos places ont �t� achet�es avec succ�s";
		else
		    resultat = isReservation ? "Votre place � �t� r�serv�e avec succ�s"
			    : "Votre place � �t� achet�e avec succ�s";
	    } else {
		resultat = isReservation ? "Echec de la r�servation"
			: "Echec de l'achat";
	    }
	} catch (DAOException e) {
	    resultat = "Echec de l'op�ration : une erreur impr�vue est survenue.";
	    erreur = e.getMessage();
	    e.printStackTrace();
	}

	return listeTickets;
    }
}
