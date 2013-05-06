package com.gr15.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Utilisateur;
import com.gr15.beans.Zone;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.PlaceDao;
import com.gr15.dao.RepresentationDao;

/**
 * Servlet implementation class ChoixPlace
 */

@WebServlet("/choixPlace")
public class ChoixPlace extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ATT_PLACES = "places";
    public static final String ATT_SESSION_UTILISATEUR = "sessionUtilisateur";
    public static final String ATT_EST_GUICHET = "estGuichet";
    public static final String ATT_REPRESENTATION_CHOISIE = "representation";
    public static final String ATT_PLACES_RESTANTES = "placesRestantes";
    public static final String ATT_ERREUR = "erreur";

    public static final String PARAM_REPRESENTATION_ID = "id";

    public static final int NB_PLACES = 600;
    public static final int NB_PLACES_GUICHET = 70;

    public static final String VUE = "/WEB-INF/choixPlace.jsp";
    public static final String ESPACE_CLIENT = "/espaceClient";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private RepresentationDao representationDao;
    private PlaceDao placeDao;

    public void init() throws ServletException {
	/* Récupération d'une instance des DAO représentation et place */
	this.representationDao = ((DAOFactory) getServletContext()
		.getAttribute(CONF_DAO_FACTORY)).getRepresentationDao();
	this.placeDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getPlaceDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	/* récupération de la représentation sélectionnée */
	String id_representation = request
		.getParameter(PARAM_REPRESENTATION_ID);
	/* vérification des données entrées */
	if (id_representation == null) {
	    request.setAttribute(ATT_ERREUR,
		    "Erreur : Aucune représentation n'a été selectionnée");

	    /* Affichage de la page d'espace client */
	    this.getServletContext().getRequestDispatcher(ESPACE_CLIENT)
		    .forward(request, response);
	    return;
	}

	Representation representation = representationDao
		.trouver(id_representation);
	if (representation == null) {
	    request.setAttribute(ATT_ERREUR,
		    "Erreur : La représentation n'est pas accessible");

	    /* Affichage de la page d'espace client */
	    this.getServletContext().getRequestDispatcher(ESPACE_CLIENT)
		    .forward(request, response);
	    return;
	}

	HttpSession session = request.getSession();
	session.setAttribute(ATT_REPRESENTATION_CHOISIE, representation);

	/* récupération de la répartition des zones */
	Place matricePlace[][] = (Place[][]) session.getAttribute(ATT_PLACES);
	if (matricePlace == null)
	    matricePlace = placeDao.genererPlan();

	/* calcul la matrice des places */
	placeDao.updateDisponibilite(matricePlace, representation);

	/* on transmet la matrice en attribut */
	request.setAttribute(ATT_PLACES, matricePlace);

	/*
	 * on transmet le type d'utilisateur en attribut pour pouvoir empêcher
	 * la réservation par le guichet
	 */
	Utilisateur utilisateur = (Utilisateur) session
		.getAttribute(ATT_SESSION_UTILISATEUR);
	request.setAttribute(ATT_EST_GUICHET, utilisateur.estGuichet());

	/* calcul du nombre de places restantes et transmission à la JSP */
	int placesRestantes = NB_PLACES;
	for (Place[] i : matricePlace) {
	    for (Place j : i) {
		if (j.isOccupe())
		    placesRestantes--;
	    }
	}
	if (!utilisateur.estGuichet())
	    placesRestantes -= NB_PLACES_GUICHET;
	request.setAttribute(ATT_PLACES_RESTANTES, placesRestantes);

	/* Transmettre les Zones */
	List<Zone> listeZone = new ArrayList<Zone>();
	placeDao.listerZone(listeZone, Integer.parseInt(id_representation));
	request.setAttribute("zones", listeZone);

	/* Affichage de la page de sélection des places */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }

}
