package com.gr15.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.beans.Representation;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.RepresentationDao;

/**
 * Servlet implementation class ChoixRepresentation
 */
@WebServlet("/choixRepresentation")
public class ChoixRepresentation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ATT_REPRESENTATIONS = "representations";
    public static final String PARAM_SPECTACLE_ID = "id";
    public static final String VUE = "/WEB-INF/choixRepresentation.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private RepresentationDao representationDao;

    public void init() throws ServletException {
	/*
	 * R�cup�ration d'une instance des DAO repr�sentation
	 */
	this.representationDao = ((DAOFactory) getServletContext()
		.getAttribute(CONF_DAO_FACTORY)).getRepresentationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/*
	 * calcule la liste des repr�sentations � venir ne sont visibles que les
	 * repr�sentations qui commencent dans plus d'une heure si l'utilisateur
	 * n'est pas le guichet
	 */
	List<Representation> listeRepresentation = new ArrayList<Representation>();

	representationDao.listerParSpectacle(request.getParameter(PARAM_SPECTACLE_ID),
			                             ((Utilisateur) request.getSession().getAttribute("sessionUtilisateur")).getId(), 
			                             listeRepresentation);

	/* on transmet la liste en attribut */
	request.setAttribute(ATT_REPRESENTATIONS, listeRepresentation);

	/* Affichage de la page d'acceuil client */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }
}
