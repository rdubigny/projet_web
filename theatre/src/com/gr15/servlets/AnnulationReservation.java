package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.dao.DAOException;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.ReservationDao;

/**
 * Servlet implementation class AnnulationReservation
 */
@WebServlet("/admin/annulationReservation")
public class AnnulationReservation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String VUE = "/admin/reservationsAdmin";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String PARAM_ID_RESERVATION = "idReservation";

    private ReservationDao reservationDao;

    public void init() throws ServletException {
	/*
	 * Récupération d'une instance des DAO repr�sentation
	 */
	this.reservationDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getReservationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

	/* Récupération du paramêtre */
	String id = getValeurParametre(request, PARAM_ID_RESERVATION);
	if (id != null) {
	    int idReservation = Integer.parseInt(id);
	    try {
		/* recupération de l'identificateur de l'utilisateur connecté */
		reservationDao.annulerReservation(idReservation);

		/* Redirection vers la page des reservations */
		response.sendRedirect(request.getContextPath() + VUE
			+ "?suppression=1");
	    } catch (DAOException e) {
		response.sendRedirect(request.getContextPath() + VUE
			+ "?suppression=0");
	    }
	} else
	    response.sendRedirect(request.getContextPath() + VUE);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

    /*
     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre(HttpServletRequest request,
	    String nomChamp) {
	String valeur = request.getParameter(nomChamp);
	if (valeur == null || valeur.trim().length() == 0) {
	    return null;
	} else {
	    return valeur;
	}
    }

}
