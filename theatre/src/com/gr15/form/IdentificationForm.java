package com.gr15.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOException;
import com.gr15.dao.UtilisateurDao;

public class IdentificationForm {
    private static final String CHAMP_LOGIN = "login";
    private static final String CHAMP_PASS = "motdepasse";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    private UtilisateurDao utilisateurDao;

    public IdentificationForm(UtilisateurDao utilisateurDao) {
	this.utilisateurDao = utilisateurDao;
    }

    public String getResultat() {
	return resultat;
    }

    public Map<String, String> getErreurs() {
	return erreurs;
    }

    public Utilisateur connecterUtilisateur(HttpServletRequest request) {
	/* Récuperation des champs du formulaire */
	String login = getValeurChamp(request, CHAMP_LOGIN);
	String motDePasse = getValeurChamp(request, CHAMP_PASS);

	Utilisateur utilisateur = null;

	try {
	    utilisateur = traiterIdentifiants(login, motDePasse);
	    if (erreurs.isEmpty()) {
		resultat = "Succès de l'identification.";
	    } else {
		resultat = "Echec de l'identification.";
	    }
	} catch (DAOException e) {
	    resultat = "Echec de l'identification : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return utilisateur;
    }

    private Utilisateur traiterIdentifiants(String login, String motdepasse) {
	Utilisateur utilisateur = null;
	try {
	    utilisateur = validationLogin(login);
	    try {
		validationMotDePasse(motdepasse, utilisateur);
	    } catch (FormValidationException e) {
		setErreur(CHAMP_PASS, e.getMessage());
	    }
	} catch (FormValidationException e) {
	    setErreur(CHAMP_LOGIN, e.getMessage());
	}

	return utilisateur;
    }

    /* Validation du login */
    private Utilisateur validationLogin(String login)
	    throws FormValidationException {
	if (login != null) {
	    Utilisateur utilisateur = utilisateurDao.trouver(login);
	    if (utilisateur != null) {
		return utilisateur;
	    } else {
		throw new FormValidationException("Utilisateur inconnu.");
	    }
	} else {
	    throw new FormValidationException("Merci de saisir votre login.");
	}
    }

    /* Validation du mot de passe */
    private void validationMotDePasse(String motdepasse, Utilisateur utilisateur)
	    throws FormValidationException {
	if (motdepasse != null) {
	    if (!utilisateur.ValidateMotdepasse(motdepasse)) {
		throw new FormValidationException("Mot de passe incorrect.");
	    }
	} else {
	    throw new FormValidationException(
		    "Merci de saisir votre mot de passe.");
	}
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur(String champ, String message) {
	erreurs.put(champ, message);
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp(HttpServletRequest request,
	    String nomChamp) {
	String valeur = request.getParameter(nomChamp);
	if (valeur == null || valeur.trim().length() == 0) {
	    return null;
	} else {
	    return valeur;
	}
    }
}
