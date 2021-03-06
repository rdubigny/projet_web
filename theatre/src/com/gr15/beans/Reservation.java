package com.gr15.beans;

import org.joda.time.DateTime;

public class Reservation {

    private int id;
    private String spectacle;
    private int rang;
    private int siege;
    private String zone;
    private float prix;
    private String prenomClient;
    private String nomClient;
    private DateTime date;

    public Reservation() {
	super();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getSpectacle() {
	return spectacle;
    }

    public void setSpectacle(String spectacle) {
	this.spectacle = spectacle;
    }

    public int getRang() {
	return rang;
    }

    public void setRang(int rang) {
	this.rang = rang;
    }

    public int getSiege() {
	return siege;
    }

    public void setSiege(int siege) {
	this.siege = siege;
    }

    public String getZone() {
	return zone;
    }

    public void setZone(String zone) {
	this.zone = zone;
    }

    public float getPrix() {
	return prix;
    }

    public void setPrix(float basePrix) {
	this.prix = basePrix;
    }

    public String getPrenomClient() {
	return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
	this.prenomClient = prenomClient;
    }

    public String getNomClient() {
	return nomClient;
    }

    public void setNomClient(String nomClient) {
	this.nomClient = nomClient;
    }

    public DateTime getDate() {
	return date;
    }

    public void setDate(DateTime date) {
	this.date = date;
    }

}
