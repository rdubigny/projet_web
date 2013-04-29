package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Place;
import com.gr15.beans.Representation;
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
    public static final String ATT_REPRESENTATION_CHOISIE = "representation";
    public static final String PARAM_REPRESENTATION_ID = "id";
    public static final String VUE = "/WEB-INF/choixPlace.jsp";

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
	Representation representation = representationDao.trouver(request
		.getParameter(PARAM_REPRESENTATION_ID));
	HttpSession session = request.getSession();
	session.setAttribute(ATT_REPRESENTATION_CHOISIE, representation);

	/* récupération de la répartition des zones */
	Place matricePlace[][] = (Place[][]) session.getAttribute(ATT_PLACES);
	if (matricePlace == null) {
	    matricePlace = placeDao.genererPlan();
	}

	/* calcule la matrice des places */
	placeDao.updateDisponibilite(matricePlace);

	/* on transmet la matrice en attribut */
	request.setAttribute(ATT_PLACES, matricePlace);

	/* Affichage de la page d'acceuil client */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }
}
