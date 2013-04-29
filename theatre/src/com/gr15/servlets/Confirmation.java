package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Representation;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.PlaceDao;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String VUE = "/WEB-INF/confirmation.jsp";
    public static final String PARAM_PLACE_ID = "id";
    public static final String PARAM_ACTION = "action";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_REPRESENTATION = "representation";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private PlaceDao placeDao;

    public void init() throws ServletException {
	/* Récupération d'une instance des DAO place */
	this.placeDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getPlaceDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	String[] ids = request.getParameterValues(PARAM_PLACE_ID);
	HttpSession session = request.getSession();
	Utilisateur utilisateur = (Utilisateur) session
		.getAttribute("ATT_SESSION_USER");
	Representation representation = (Representation) session
		.getAttribute("ATT_REPRESENTATION");
	if (request.getParameter(PARAM_ACTION).equals("reservation"))
	    placeDao.reserver(utilisateur, representation, ids, false);
	else
	    placeDao.reserver(utilisateur, representation, ids, false);
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }

}
