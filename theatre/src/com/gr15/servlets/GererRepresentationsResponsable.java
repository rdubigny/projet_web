package com.gr15.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gr15.beans.Representation;
import com.gr15.dao.DAOFactory;
import com.gr15.dao.RepresentationDao;

/**
 * Servlet implementation class gererRepresentationsResponsable
 */
@WebServlet( "/responsable/gererRepresentationsResponsable" )
public class GererRepresentationsResponsable extends HttpServlet {
    private static final long  serialVersionUID   = 1L;
    public static final String VUE                = "/WEB-INF/gererRepresentationsResponsable.jsp";
    public static final String CONF_DAO_FACTORY   = "daofactory";
    public static final String ATT_REPRESENTATION = "representationsResponsable";

    private RepresentationDao  representationDao;

    public void init() throws ServletException {
        /* Récupération d'une instance du DAO spectacle */
        this.representationDao = ( (DAOFactory) getServletContext().getAttribute(
                CONF_DAO_FACTORY ) ).getRepresentationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        /* calcule la liste des représentations disponibles */
        List<Representation> listeRepresentation = new ArrayList<Representation>();
        representationDao.lister( listeRepresentation );

        request.setAttribute( ATT_REPRESENTATION, listeRepresentation );

        /* Affichage de la page d'acceuil client */
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
