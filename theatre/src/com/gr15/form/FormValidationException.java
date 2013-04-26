package com.gr15.form;

public class FormValidationException extends Exception {
    private static final long serialVersionUID = -4524277784820208077L;

    /*
     * Constructeur
     */
    public FormValidationException(String message) {
	super(message);
    }
}
