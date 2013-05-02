package com.gr15.beans;

public class Reservation {
	
	private int id;
	private String representation;
    private String spectacle;
    private int rang;
    private int siege;
    private String zone;
    private float basePrix;
    private float pourcentage;

    // Le constructeur
    public Reservation(String representation,
	    String spectacle,
	    int rang,
	    int siege,
	    String zone,
	    float basePrix,
	    float pourcentage) {
		super();
		this.representation = representation;
		this.spectacle = spectacle;
		this.rang = rang;
		this.siege = siege;
		this.zone = zone;
		this.basePrix = basePrix;
		this.pourcentage = pourcentage;
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

	public float getBasePrix() {
		return basePrix;
	}

	public void setBasePrix(float basePrix) {
		this.basePrix = basePrix;
	}

	public float getPourcentage() {
		return pourcentage;
	}

	public void setPourcentage(float pourcentage) {
		this.pourcentage = pourcentage;
	}

}
