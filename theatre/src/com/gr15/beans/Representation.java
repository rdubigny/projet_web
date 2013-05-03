package com.gr15.beans;

import org.joda.time.DateTime;

public class Representation {
    private int id;
    private int idSpectacle;
    private String nomSpectacle;
    private DateTime date;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getIdSpectacle() {
	return idSpectacle;
    }

    public void setIdSpectacle(int id_spectacle) {
	this.idSpectacle = id_spectacle;
    }

    public DateTime getDate() {
	return date;
    }

    public void setDate(DateTime date) {
	this.date = date;
    }

    public String getNomSpectacle() {
	return nomSpectacle;
    }

    public void setNomSpectacle(String nomSpectacle) {
	this.nomSpectacle = nomSpectacle;
    }
}
