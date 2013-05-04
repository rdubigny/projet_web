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
import com.gr15.dao.DAOFactory;
import com.gr15.dao.ReservationDao;

/**
 * Servlet implementation class reservationsAdmin
 */
@WebServlet( "/admin/reservationsAdmin" )
public class ReservationsAdmin extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String VUE              = "/WEB-INF/reservationsAdmin.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_RESERVATIONS = "reservationsAdmin";
    private ReservationDao     reservationDao;

    public void init() throws ServletException {
        /* Récupération d'une instance du DAO spectacle */
        this.reservationDao = ( (DAOFactory) getServletContext().getAttribute(
                CONF_DAO_FACTORY ) ).getReservationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        /* calcule la liste des reservations disponibles */
        List<Reservation> listeReservation = new ArrayList<Reservation>();
        reservationDao.lister( listeReservation );
        request.setAttribute( ATT_RESERVATIONS, listeReservation );

        /* Affichage de la page de reservations pour l'admin */
        this.getServletContext().getRequestDispatcher( VUE )
                .forward( request, response );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        /* Affichage de la page d'acceuil client */
        this.getServletContext().getRequestDispatcher( VUE )
                .forward( request, response );
    }

}
