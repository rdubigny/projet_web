package com.gr15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.PlaceDao;
import com.gr15.form.PlaceForm;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/confirmation")
public class Confirmation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final String ATT_LISTE_TICKETS = "tickets";
    public static final String ATT_FORM = "form";

    public static final String ATT_SESSION_UTILISATEUR = "sessionUtilisateur";
    public static final String ATT_SESSION_REPRESENTATION_CHOISIE = "representation";

    public static final String ATT_PLACES = "places";
    public static final String ATT_REPRESENTATION_CHOISIE = "representation";
    public static final int NB_PLACES = 600;
    public static final int NB_PLACES_GUICHET = 70;

    public static final String VUE = "/WEB-INF/confirmation.jsp";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private PlaceDao placeDao;

    public void init() throws ServletException {
	/* Récuperation d'une instance des DAO place */
	this.placeDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getPlaceDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/* préparation de l'objet metier */
	PlaceForm form = new PlaceForm(placeDao);

	/* récupération de la répartition des zones et des places restantes */
	HttpSession session = request.getSession();
	Representation representation = (Representation) session
		.getAttribute(ATT_SESSION_REPRESENTATION_CHOISIE);
	int placesRestantes = NB_PLACES;
	if (representation != null) {
	    Place matricePlace[][] = (Place[][]) session
		    .getAttribute(ATT_PLACES);
	    if (matricePlace == null)
		matricePlace = placeDao.genererPlan();

	    /* calcul la matrice des places */
	    placeDao.updateDisponibilite(matricePlace, representation);

	    /* calcul du nombre de places restantes et transmission à la JSP */
	    for (Place[] i : matricePlace) {
		for (Place j : i) {
		    if (j.isOccupe())
			placesRestantes--;
		}
	    }
	    if (!((Utilisateur) session.getAttribute(ATT_SESSION_UTILISATEUR))
		    .estGuichet())
		placesRestantes -= NB_PLACES_GUICHET;
	}

	/* traitement de la réservation ou de l'achat */
	List<Ticket> listeTickets = form.reserverAcheter(request,
		placesRestantes);

	/*
	 * passage en attribut du résultat de l'opération et de la liste
	 * éventuelle des tickets
	 */
	request.setAttribute(ATT_FORM, form);
	request.setAttribute(ATT_LISTE_TICKETS, listeTickets);

	/* forward vers la page de confirmation */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }
}
