package com.gr15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.dao.DAOFactory;
import com.gr15.dao.RepresentationDao;

/**
 * Servlet implementation class AnnulationRepresentation
 */
@WebServlet( "/annulationRepresentation" )
public class AnnulationRepresentation extends HttpServlet {
    private static final long  serialVersionUID        = 1L;
    public static final String VUE                     = "/WEB-INF/annulationRepresentation.jsp";
    public static final String PARAM_ID_REPRESENTATION = "idRepresentation";
    public static final String CONF_DAO_FACTORY        = "daofactory";

    private RepresentationDao  representationDao;

    public void init() throws ServletException {
        /*
         * Récupération d'une instance des DAO représentation
         */
        this.representationDao = ( (DAOFactory) getServletContext()
                .getAttribute( CONF_DAO_FACTORY ) ).getRepresentationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        /* Récupération du paramètre */
        String idRepresentation = getValeurParametre( request,
                PARAM_ID_REPRESENTATION );
        representationDao.supprimer( idRepresentation );
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

    /*
     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
     * contenu sinon.
     */
    private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
