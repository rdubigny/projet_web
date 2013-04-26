package com.gr15.beans;

public class Utilisateur {
    private long id;
    private String login;
    private String nom;
    private String prenom;
    private String email;
    private Boolean estadmin;
    private String motdepasse;

    public long getId() {
	return id;
    }

    public void setId(long id) {
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

    public Boolean getEstAdmin() {
	return estadmin;
    }

    public void setEstAdmin(Boolean estAdmin) {
	estadmin = estAdmin;
    }

    /**
     * @param motdepasse
     * @return true si le mot de passe est correct
     */
    public boolean ValidateMotdepasse(String motdepasse) {
	// Remarque : on pourrai utiliser de l'encriptage pour plus de sécurité
	return this.motdepasse.equals(motdepasse);
    }

    public void setMotdepasse(String motdepasse) {
	this.motdepasse = motdepasse;
    }
}
