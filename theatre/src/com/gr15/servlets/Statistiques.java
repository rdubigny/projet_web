package com.gr15.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.beans.Spectacle;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.StatistiquesDao;

/**
 * Servlet implementation class Statistiques
 */
@WebServlet( "/responsable/statistiques" )
public class Statistiques extends HttpServlet {
    private static final long  serialVersionUID                 = 1L;
    public static final String VUE                              = "/WEB-INF/statistiques.jsp";
    public static final String CONF_DAO_FACTORY                 = "daofactory";
    public static final String ATT_PLACES_VENDUES               = "placesVendues";
    public static final String ATT_PLACES_VENDUES_PAR_SPECTACLE = "placesVenduesParSpectacle";
    public static final String ATT_SPECTACLE_RENTABLE           = "listeSpectacleRentable";
    public static final String ATT_SPECTACLE_LE_PLUS_RENTABLE   = "nomSpectacle";
    public static final String ATT_CLIENT_OR                    = "clientOr";

    private StatistiquesDao    statistiquesDao;

    public void init() throws ServletException {
        /* Récupération d'une instance du DAO spectacle */
        this.statistiquesDao = ( (DAOFactory) getServletContext().getAttribute(
                CONF_DAO_FACTORY ) ).getStatistiquesDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {

        /* Nombre de places Vendues */
        int placesVendues = statistiquesDao.totalPlacesVendues();
        request.setAttribute( ATT_PLACES_VENDUES, placesVendues );

        /* Nombre de places vendues par spectacle */
        List<Spectacle> listeSpectacle = new ArrayList<Spectacle>();
        statistiquesDao.placesVenduesParSpectacle( listeSpectacle );
        request.setAttribute( ATT_PLACES_VENDUES_PAR_SPECTACLE, listeSpectacle );

        /* Liste des spectacles avec recette classé par ordre de rentabilité */
        List<Spectacle> listeSpectacleRentable = new ArrayList<Spectacle>();
        statistiquesDao.listerSpectacleRentabilite( listeSpectacleRentable );
        request.setAttribute( ATT_SPECTACLE_RENTABLE, listeSpectacleRentable );

        /* Spectacle le plus rentable */
        String spectacle = statistiquesDao.spectacleLePlusRentable();
        request.setAttribute( ATT_SPECTACLE_LE_PLUS_RENTABLE, spectacle );

        /* Meilleur client */
        Utilisateur utilisateur = statistiquesDao.clientOr();
        request.setAttribute( ATT_CLIENT_OR, utilisateur );

        // TODO Auto-generated method stub
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
    }

}
