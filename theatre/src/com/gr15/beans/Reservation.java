package com.gr15.beans;

public class Reservation {
	
	private int id;
	private String representation;
    private String spectacle;
    private int rang;
    private int siege;
    private String zone;
    private float prix;

    // Le constructeur
    public Reservation(){
    	super();
    }
    
    // Les Getters et Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
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

}
