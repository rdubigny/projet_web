package com.gr15.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.gr15.beans.Reservation;
import com.gr15.beans.Utilisateur;

import com.gr15.dao.DAOFactory;
import com.gr15.dao.ReservationDao;

/**
 * Servlet implementation class mesReservations
 */
@WebServlet( "/reservationsClient" )
public class ReservationsClient extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String ATT_RESERVATION = "reservations";
    public static final String VUE = "/WEB-INF/reservationsClient.jsp";
    
    private ReservationDao reservationDao;
    
    public void init() throws ServletException {
	/* Récupération d'une instance des DAO réservation*/
	this.reservationDao = ((DAOFactory) getServletContext().getAttribute("daofactory")).getReservationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,IOException {	
    	/* creation de liste de réservation */
    	List<Reservation> listeReservation = new ArrayList<Reservation>();
    	long idUtilisateur = ((Utilisateur)(request.getSession().getAttribute("sessionUtilisateur"))).getId();	
    	reservationDao.listerParReservation(idUtilisateur,listeReservation);
//    	if(!listeReservation.isEmpty()){
				System.out.println("la liste est vide !");
//    	}
    	/* ajout d'attribut à request */
    	request.setAttribute(ATT_RESERVATION, listeReservation);
        /* envoie des parametres de la page de réservation */
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
    IOException {
    	// TODO Auto-generated method stub
    }

}
