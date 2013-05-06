package com.gr15.beans;

public class Zone {
	private String nom;
	private float prix;

	public Zone(String nom, float prix) {
		super();
		this.nom = nom;
		this.prix = prix;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

}
