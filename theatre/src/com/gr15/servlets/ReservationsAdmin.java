package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class reservationsAdmin
 */
@WebServlet( "/admin/reservationsAdmin" )
public class ReservationsAdmin extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String VUE              = "/WEB-INF/reservationsAdmin.jsp";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
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
