package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.UtilisateurDao;
import com.gr15.form.IdentificationForm;

/**
 * Servlet implementation class Identification
 */
@WebServlet("/identification")
public class Identification extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String VUE = "/WEB-INF/identification.jsp";
    public static final String REDIRECTION_ESPACE_CLIENT = "espaceClient";
    public static final String REDIRECTION_ESPACE_RESPONSABLE = "espaceAdmin";
    public static final String CONF_DAO_FACTORY = "daofactory";

    private UtilisateurDao utilisateurDao;

    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getUtilisateurDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/* Affichage de la page de connexion */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/* Préparation de l'objet formulaire */
	IdentificationForm form = new IdentificationForm(utilisateurDao);

	/* Traitement de la requête et récupération du bean en résultant */
	Utilisateur utilisateur = form.connecterUtilisateur(request);

	/* Récupération de la session depuis la requête */
	HttpSession session = request.getSession();

	request.setAttribute(ATT_FORM, form);

	/**
	 * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
	 * Utilisateur à la session puis redirection vers l'espace client, sinon
	 * suppression du bean de la session et redirection vers la page
	 * d'identification.
	 */

	if (form.getErreurs().isEmpty()) {
	    session.setAttribute(ATT_SESSION_USER, utilisateur);
	    if (utilisateur.estResponsable())
		response.sendRedirect(REDIRECTION_ESPACE_RESPONSABLE);
	    else
		response.sendRedirect(REDIRECTION_ESPACE_CLIENT);
	} else {
	    session.setAttribute(ATT_SESSION_USER, null);
	    this.getServletContext().getRequestDispatcher(VUE)
		    .forward(request, response);
	}
    }

}
