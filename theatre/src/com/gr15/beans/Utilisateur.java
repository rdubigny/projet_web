package com.gr15.beans;

public class Utilisateur {
    private int id;
    private String login;
    private String motdepasse;
    private String nom;
    private String prenom;
    private String email;
    private String typeUtilisateur;

    public int getId() {
	return id;
    }

    public void setId(int id) {
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

    /**
     * @param motdepasse
     * @return true si le mot de passe est correct
     */
    public boolean ValidateMotdepasse(String motdepasse) {
	// Remarque : on pourrai utiliser de l'encriptage pour plus de s�curit�
	return this.motdepasse.equals(motdepasse);
    }

    public void setMotdepasse(String motdepasse) {
	this.motdepasse = motdepasse;
    }

    public String getTypeUtilisateur() {
	return typeUtilisateur;
    }

    public void setTypeUtilisateur(String typeUtilisateur) {
	this.typeUtilisateur = typeUtilisateur;
    }

    public boolean estResponsable() {
	return typeUtilisateur.equals("responsable");
    }

    public boolean estGuichet() {
	return typeUtilisateur.equals("guichet");
    }
}
