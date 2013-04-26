package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Deconnection
 */
@WebServlet("/deconnexion")
public class Deconnexion extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String URL_REDIRECTION = "identification";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/* Récupération et destruction de la session en cours */
	HttpSession session = request.getSession();
	session.invalidate();

	/* Redirection vers la page d'identification */
	response.sendRedirect(URL_REDIRECTION);
    }

}
