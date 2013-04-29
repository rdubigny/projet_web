package com.gr15.beans;

public class Spectacle {
    private long   id;
    private String nom;
    private float  basePrix;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
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
}
