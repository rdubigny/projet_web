package com.gr15.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RestrictionFilter. Filtre l'acc�s �
 * l'application.
 */

@WebFilter(urlPatterns = "/*")
public class RestrictionFilter implements Filter {
    public static final String ACCES_CONNEXION = "/identification";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse res,
	    FilterChain chain) throws IOException, ServletException {
	/* Cast des objets request et response */
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) res;

	/* Non-filtrage des ressources statiques et de la page de login */
	String chemin = request.getRequestURI().substring(
		request.getContextPath().length());
	System.out.print(chemin);
	if (chemin.startsWith("/inc") || chemin.startsWith(ACCES_CONNEXION)
		|| chemin.equals("/")) {

	    System.out.println(" --> PUBLIC");
	    chain.doFilter(request, response);
	    return;
	}
	System.out.println(" --> RESTREINT");

	/* Récuperation de la session depuis la requête */
	HttpSession session = request.getSession();

	if (session.isNew()) {
	    /*
	     * si la session vient d'être créée, redirection vers la page
	     * d'identification
	     */
	    response.sendRedirect(request.getContextPath() + ACCES_CONNEXION
		    + "?redirect=0");
	} else if (session.getAttribute(ATT_SESSION_USER) == null) {
	    /*
	     * Si l'objet utilisateur n'existe pas dans la session en cours,
	     * redirection vers la page d'identification
	     */
	    response.sendRedirect(request.getContextPath() + ACCES_CONNEXION
		    + "?redirect=1");

	} else {

	    /* accès à la zone restreinte */
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
