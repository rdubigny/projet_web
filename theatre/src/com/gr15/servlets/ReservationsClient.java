package com.gr15.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.beans.Reservation;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOException;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.ReservationDao;

/**
 * Servlet implementation class mesReservations
 */
@WebServlet("/reservationsClient")
public class ReservationsClient extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String PARAM_RESERVATION_ID = "id";
    public static final String RESERVATION_ID = "id";
    public static final String ATT_MESSAGE_ANNULATION = "messageAnnulation";

    private ReservationDao reservationDao;

    public void init() throws ServletException {
	/* Récupération d'une instance des DAO réservation */
	this.reservationDao = ((DAOFactory) getServletContext().getAttribute(
		"daofactory")).getReservationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/* recupération de l'idUtilisateur en variable de session */
	int idUtilisateur = ((Utilisateur) (request.getSession()
		.getAttribute("sessionUtilisateur"))).getId();
	/* annulation d'une reservation */
	request.setAttribute(ATT_MESSAGE_ANNULATION, "");
	if (request.getParameter(PARAM_RESERVATION_ID) != null) {
	    try {
		int idReservation = Integer.parseInt(request
			.getParameter(PARAM_RESERVATION_ID));
		reservationDao.annulerReservation(idUtilisateur, idReservation);
		request.setAttribute(ATT_MESSAGE_ANNULATION,
			"Votre annulation a réussi !");
	    } catch (DAOException e) {
		/*
		 * si l'annulation ne fonctionne pas une exception est levée
		 */
		request.setAttribute(ATT_MESSAGE_ANNULATION, e.getMessage());
	    }
	}
	/* création de liste de réservation */
	List<Reservation> listeReservation = new ArrayList<Reservation>();

	reservationDao.listerParReservation(idUtilisateur, listeReservation);

	/* ajout d'attribut à request */
	request.setAttribute("reservations", listeReservation);

	/* envoie des parametres de la page de réservation */
	this.getServletContext()
		.getRequestDispatcher("/WEB-INF/reservationsClient.jsp")
		.forward(request, response);
    }
}
