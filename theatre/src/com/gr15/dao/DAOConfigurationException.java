package com.gr15.dao;

public class DAOConfigurationException extends RuntimeException {
    private static final long serialVersionUID = -2972809858332730767L;

    /*
     * Constructeurs
     */
    public DAOConfigurationException(String message) {
	super(message);
    }

    public DAOConfigurationException(String message, Throwable cause) {
	super(message, cause);
    }

    public DAOConfigurationException(Throwable cause) {
	super(cause);
    }
}
