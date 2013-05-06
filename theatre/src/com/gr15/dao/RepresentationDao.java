package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Representation;

public interface RepresentationDao {

    /**
     * liste les représentations contenue dans la table representation
     * 
     * @param listeRepresentation
     */
    public void lister(List<Representation> listeRepresentation);

    /**
     * retourne la représentation correspondant à l'id en paramêtre
     * 
     * @param id
     * @return
     */
    public Representation trouver(String id);

    /**
     * Supprime la représentation correspondant à l'id en paramêtre
     * 
     * @param idRepresentation
     */
    public void supprimer(String idRepresentation);

    /**
     * liste les représentations du spectacle fournis en paramêtre. Si en
     * fonction du type d'utilisateur certaine représentation sont cachées.
     * 
     * @param idSpectacle
     * @param idUtilisateur
     * @param listeRepresentation
     */
    void listerParSpectacle(String idSpectacle, int idUtilisateur,
	    List<Representation> listeRepresentation);

}
