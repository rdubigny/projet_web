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

import com.gr15.beans.Utilisateur;

/**
 * Servlet Filter implementation class ResponsableFilter
 */
@WebFilter( urlPatterns = "/responsable/*" )
public class ResponsableFilter implements Filter {
    public static final String ACCES_ESPACE_CLIENT = "/espaceClient";
    public static final String ATT_SESSION_USER    = "sessionUtilisateur";

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter( ServletRequest req, ServletResponse res,
            FilterChain chain ) throws IOException, ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /*
         * Si l'objet utilisateur existe et n'est pas de type responsable, alors
         * l'utilisateur est redirigé.
         */
        Utilisateur utilisateur = (Utilisateur) session
                .getAttribute( ATT_SESSION_USER );
        if ( utilisateur != null && !utilisateur.estResponsable() ) {

            /* Redirection vers la page d'identification */
            response.sendRedirect( request.getContextPath()
                    + ACCES_ESPACE_CLIENT + "?redirect=1" );

        } else {

            /* accès à la zone restreinte */
            chain.doFilter( request, response );

        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init( FilterConfig fConfig ) throws ServletException {
    }

}
