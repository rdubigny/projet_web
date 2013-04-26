package com.gr15.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gr15.beans.Utilisateur;

public class IdentificationForm {
    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_PASS = "motdepasse";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public String getResultat() {
	return resultat;
    }

    public Map<String, String> getErreurs() {
	return erreurs;
    }

    public Utilisateur connecterUtilisateur(HttpServletRequest request) {
	/* Récupération des champs du formulaire */
	String email = getValeurChamp(request, CHAMP_EMAIL);
	String motDePasse = getValeurChamp(request, CHAMP_PASS);

	Utilisateur utilisateur = new Utilisateur();

	return null;
    }
}
