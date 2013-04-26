package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Utilisateur;
import com.gr15.form.IdentificationForm;

/**
 * Servlet implementation class Identification
 */
@WebServlet("/identification")
public class Identification extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // useless public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    public static final String VUE = "/WEB-INF/identification.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Identification() {
	super();
	// TODO Auto-generated constructor stub
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
	IdentificationForm form = new IdentificationForm();

	/* Traitement de la requête et récupération du bean en résultant */
	Utilisateur utilisateur = form.connecterUtilisateur(request);

	/* Récupération de la session depuis la requête */
	HttpSession session = request.getSession();

	/**
	 * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
	 * Utilisateur à la session, sinon suppression du bean de la session.
	 */
	if (form.getErreurs().isEmpty()) {
	    session.setAttribute(ATT_SESSION_USER, utilisateur);
	} else {
	    session.setAttribute(ATT_SESSION_USER, null);
	}

	request.setAttribute(ATT_FORM, form);
	// useless request.setAttribute(ATT_USER, utilisateur);

	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }

}
