package com.gr15.beans;

public class Spectacle {
    private int    id;
    private String nom;
    private float  basePrix;
    private int    placesVendues;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public float getBasePrix() {
        return basePrix;
    }

    public void setBasePrix( float basePrix ) {
        this.basePrix = basePrix;
    }

    public int getPlacesVendues() {
        return placesVendues;
    }

    public void setPlacesVendues( int placesVendues ) {
        this.placesVendues = placesVendues;
    }
}
