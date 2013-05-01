package com.gr15.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.PlaceDao;

public class PlaceForm {
    public static final String PARAM_PLACE_ID = "id";
    public static final String PARAM_ACTION = "action";
    public static final String PARAM_RESERVATION = "reservation";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_REPRESENTATION = "representation";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private PlaceDao placeDao;

    public PlaceForm(PlaceDao placeDao) {
	this.placeDao = placeDao;
    }

    public String getResultat() {
	return resultat;
    }

    public Map<String, String> getErreurs() {
	return erreurs;
    }

    public List<Ticket> reserver(HttpServletRequest request) {
	List<Ticket> listeTickets = new ArrayList<Ticket>();

	/* récupération des places sélectionnées */
	String[] ids = request.getParameterValues(PARAM_PLACE_ID);

	/* récupération de l'utilisateur et de la représentation en sélection */
	HttpSession session = request.getSession();
	Utilisateur utilisateur = (Utilisateur) session
		.getAttribute(ATT_SESSION_USER);
	Representation representation = (Representation) session
		.getAttribute(ATT_REPRESENTATION);

	/* enregistrement de la réservation ou de l'achat */
	if (request.getParameter(PARAM_ACTION).equals(PARAM_RESERVATION)) {
	    placeDao.reserver(utilisateur, representation, ids);
	    listeTickets = null;
	} else
	    placeDao.acheter(utilisateur, representation, ids, listeTickets);

	return listeTickets;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
	erreurs.put(champ, message);
    }

}
