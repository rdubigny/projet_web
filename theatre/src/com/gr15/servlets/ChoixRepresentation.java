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

import com.gr15.beans.Representation;
import com.gr15.beans.Spectacle;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.RepresentationDao;
import com.gr15.dao.SpectacleDao;
import com.gr15.dao.UtilisateurDao;

/**
 * Servlet implementation class ChoixRepresentation
 */
@WebServlet("/choixRepresentation")
public class ChoixRepresentation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ATT_REPRESENTATIONS = "representations";
    public static final String ATT_SPECTACLE_CHOISI = "spectacle";
    public static final String PARAM_SPECTACLE_ID = "id";
    public static final String VUE = "/WEB-INF/choixRepresentation.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private SpectacleDao spectacleDao;
    private RepresentationDao representationDao;
    public void init() throws ServletException {
	/* Récupération d'une instance des DAO spectacle et représentation et utilisateur*/
	this.spectacleDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getSpectacleDao();
	this.representationDao = ((DAOFactory) getServletContext()
		.getAttribute(CONF_DAO_FACTORY)).getRepresentationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	/* réscupération du spectacle en sélection */
	Spectacle spectacle = spectacleDao.trouver(request
		.getParameter(PARAM_SPECTACLE_ID));
	HttpSession session = request.getSession();
	session.setAttribute(ATT_SPECTACLE_CHOISI, spectacle);

	/* calcule la liste des représentations à venir */
	/*
	 * ne sont visibles que les représentations qui commencent dans plus
	 * d'une heure
	 */
	List<Representation> listeRepresentation = new ArrayList<Representation>();
	HttpSession session1 = request.getSession();
	
	representationDao.listerParSpectacle(spectacle.getId(), (((Utilisateur)session1.getAttribute("sessionUtilisateur"))).getId(),
		listeRepresentation);

	/* on transmet la liste en attribut */
	request.setAttribute(ATT_REPRESENTATIONS, listeRepresentation);

	/* Affichage de la page d'acceuil client */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }
}
