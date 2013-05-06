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

import com.gr15.beans.Spectacle;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.SpectacleDao;

/**
 * Servlet implementation class EspaceClient
 */
@WebServlet("/espaceClient")
public class EspaceClient extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ATT_SPECTACLES = "spectacles";
    public static final String ATT_SESSION_REPRESENTATION_CHOISIE = "representation";
    public static final String VUE = "/WEB-INF/espaceClient.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private SpectacleDao spectacleDao;

    public void init() throws ServletException {
	/* Récupération d'une instance du DAO spectacle */
	this.spectacleDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getSpectacleDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/* calcule la liste des spectacles disponibles */
	List<Spectacle> listeSpectacle = new ArrayList<Spectacle>();
	spectacleDao.lister(listeSpectacle);

	request.setAttribute(ATT_SPECTACLES, listeSpectacle);

	/* supression de la variable de session de repréentation si elle existe */
	HttpSession session = request.getSession();
	session.setAttribute(ATT_SESSION_REPRESENTATION_CHOISIE, null);

	/* Affichage de la page d'acceuil client */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }
}
