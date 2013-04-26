package com.gr15.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class RestrictionFilter. Filtre l'acc�s �
 * l'application.
 */
@WebFilter("/WEB-INF/*")
public class RestrictionFilter implements Filter {

    /**
     * Default constructor.
     */
    public RestrictionFilter() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
	// TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	// TODO Auto-generated method stub
	// place your code here

	// TODO v�rifier que la variable de session de l'utilisateur signale
	// qu'il s'est logu�

	// TODO s'il n'est pas logu� forward sur identification.jsp

	// TODO la page identification.jsp ne doit pas �tre filtr�e

	// pass the request along the filter chain
	chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	// TODO Auto-generated method stub
    }

}
