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

    public void init() throws ServletException {
	/* Récupération d'une instance des DAO spectacle et représentation */
	this.representationDao = ((DAOFactory) getServletContext()
		.getAttribute(CONF_DAO_FACTORY)).getRepresentationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	/* réscupération de la représentation sélectionnée */
	Representation representation = representationDao.trouver(request
		.getParameter(PARAM_REPRESENTATION_ID));
	HttpSession session = request.getSession();
	session.setAttribute(ATT_REPRESENTATION_CHOISIE, representation);

	/* calcule la matrice des places */
	Place matricePlace[][] = {
		{ new Place(1), new Place(2), new Place(3) },
		{ new Place(4), new Place(5), new Place(6) } };// new
							       // Place[6][6];
	// matricePlace.
	// représentation
	// List<List<Representation>> listeRepresentation = new
	// ArrayList<Representation>();
	// representationDao.listerParSpectacle(spectacle.getId(),
	// listeRepresentation);

	int i = matricePlace.length;

	/* on transmet la matrice en attribut */
	request.setAttribute(ATT_PLACES, matricePlace);

	/* Affichage de la page d'acceuil client */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }
}
