package com.gr15.beans;

public class Utilisateur {
    private Integer id;
    private String login;
    private String nom;
    private String prenom;
    private String email;
    private String motdepasse;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public String getPrenom() {
	return prenom;
    }

    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public boolean ValidateMotdepasse(String motdepasse) {
	return this.motdepasse.equals(motdepasse);
    }

    public void setMotdepasse(String motdepasse) {
	this.motdepasse = motdepasse;
    }
}
