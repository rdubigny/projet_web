package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Spectacle;

public interface SpectacleDao {

    /**
     * Liste l'ensemble des spectacles présent dans la table spectacle
     * 
     * @param listeSpectacle
     */
    public void lister(List<Spectacle> listeSpectacle);

    /**
     * renvoie le spectacle dont l'identifiant est fourni en paramêtre
     * 
     * @param id
     * @return
     */
    public Spectacle trouver(String id);

}
