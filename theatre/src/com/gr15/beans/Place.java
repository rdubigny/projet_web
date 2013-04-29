package com.gr15.beans;

public class Place {
    private int zone;
    private boolean occupe;

    public Place() {
	super();
	occupe = false;
    }

    public Place(int zone) {
	super();
	this.zone = zone;
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

    public void setOccupe(boolean occupe) {
	this.occupe = occupe;
    }
}
