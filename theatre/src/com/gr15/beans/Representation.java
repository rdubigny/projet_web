package com.gr15.beans;

import org.joda.time.DateTime;

public class Representation {
    private long id;
    private long idSpectacle;
    private String nomSpectacle;
    private DateTime date;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getIdSpectacle() {
	return idSpectacle;
    }

    public void setIdSpectacle(long id_spectacle) {
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
