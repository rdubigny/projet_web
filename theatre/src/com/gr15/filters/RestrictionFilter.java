package com.gr15.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RestrictionFilter. Filtre l'accès à
 * l'application.
 */

// @WebFilter( urlPatterns = "/*" )
public class RestrictionFilter implements Filter {
    public static final String ACCES_CONNEXION = "/identification";
    public static final String ATT_ERREUR = "erreur";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res,
	    FilterChain chain) throws IOException, ServletException {
	/* Cast des objets request et response */
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) res;

	/* Non-filtrage des ressources statiques */
	String chemin = request.getRequestURI().substring(
		request.getContextPath().length());
	if (chemin.startsWith("/inc")) {
	    chain.doFilter(request, response);
	    return;
	}

	/* Récupération de la session depuis la requête */
	HttpSession session = request.getSession();

	/**
	 * Si l'objet utilisateur n'existe pas dans la session en cours, alors
	 * l'utilisateur n'est pas connecté.
	 */
	String erreur = "Vous devez vous identifier pour accéder à votre espace";

	if (session.getAttribute(ATT_SESSION_USER) == null) {
	    /*
	     * si la page qu'on cherche à avoir est différente de theatre/ ou
	     * identification/
	     */
	    if (!chemin.contentEquals(ACCES_CONNEXION)
		    && !chemin.contentEquals("/")) {
		/* on envoie à la page suivante qu'il ya eu une erreur */
		request.setAttribute(ATT_ERREUR, erreur);
	    }
	    /* Redirection vers la page d'identification */
	    // response.sendRedirect(request.getContextPath() +
	    // ACCES_CONNEXION);
	    request.getRequestDispatcher(ACCES_CONNEXION).forward(request,
		    response);

	} else {
	    /* Affichage de la page restreinte */
	    chain.doFilter(request, response);
	}
    }

    @Override
    public void destroy() {
	// TODO Auto-generated method stub

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
	// TODO Auto-generated method stub

    }
}
