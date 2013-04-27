package com.gr15.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.dao.DAOFactory;
import com.gr15.dao.SpectacleDao;

/**
 * Servlet implementation class EspaceClient
 */
@WebServlet("/espaceClient")
public class EspaceClient extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ATT_SPECTACLES = "spectacles";
    public static final String VUE = "/WEB-INF/espaceClient.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private SpectacleDao spectacleDao;

    public void init() throws ServletException {
	/* R�cup�ration d'une instance de notre DAO Spectacle */
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
	List<String> listeSpectacle = new ArrayList<String>();
	spectacleDao.lister(listeSpectacle);

	request.setAttribute(ATT_SPECTACLES, listeSpectacle);

	/* Affichage de la page d'acceuil client */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }
}
