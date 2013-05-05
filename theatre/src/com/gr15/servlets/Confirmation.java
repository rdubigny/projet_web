package com.gr15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.beans.Ticket;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.PlaceDao;
import com.gr15.form.PlaceForm;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/confirmation")
public class Confirmation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final String ATT_LISTE_TICKETS = "tickets";
    public static final String ATT_FORM = "form";

    public static final String VUE = "/WEB-INF/confirmation.jsp";

    public static final String CONF_DAO_FACTORY = "daofactory";

    private PlaceDao placeDao;

    public void init() throws ServletException {
	/* R�cup�ration d'une instance des DAO place */
	this.placeDao = ((DAOFactory) getServletContext().getAttribute(
		CONF_DAO_FACTORY)).getPlaceDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	/* préparation de l'objet metier */
	PlaceForm form = new PlaceForm(placeDao);

	/* traitement de la réservation ou de l'achat */
	List<Ticket> listeTickets = form.reserver(request);

	/*
	 * passage en attribut du résultat de l'opération et de la liste
	 * éventuelle des tickets
	 */
	request.setAttribute(ATT_FORM, form);
	request.setAttribute(ATT_LISTE_TICKETS, listeTickets);

	/* forward vers la page de confirmation */
	this.getServletContext().getRequestDispatcher(VUE)
		.forward(request, response);
    }

}
