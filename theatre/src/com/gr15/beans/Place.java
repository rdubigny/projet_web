package com.gr15.beans;

public class Place {
    private int id;
    private int zone;
    private boolean occupe;

    public Place(int id, int zone) {
	super();
	this.id = id;
	this.zone = zone;
	occupe = false;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getZone() {
	return zone;
    }

    public void setZone(int zone) {
	this.zone = zone;
    }

    public boolean isOccupe() {
	return occupe;
    }

    public void setOccupe() {
	this.occupe = true;
    }

    public void setLibre() {
	this.occupe = false;
    }
}
