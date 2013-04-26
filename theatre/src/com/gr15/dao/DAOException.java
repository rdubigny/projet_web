package com.gr15.dao;

public class DAOException extends RuntimeException {
    private static final long serialVersionUID = -8261753600454863777L;

    /*
     * Constructeurs
     */
    public DAOException(String message) {
	super(message);
    }

    public DAOException(String message, Throwable cause) {
	super(message, cause);
    }

    public DAOException(Throwable cause) {
	super(cause);
    }
}
