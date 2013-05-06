package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Spectacle;
import com.gr15.beans.Utilisateur;

public interface StatistiquesDao {

    /**
     * renvoie l'utilisateur qui à dépensé le plus d'argent dans l'achat de
     * place.
     * 
     * @return
     */
    public Utilisateur clientOr();

    /**
     * renvoie la liste des spectacles en prenant soin de completer le champ
     * placesVendues des beans Spectacle
     * 
     * @param listeSpectacle
     */
    public void placesVenduesParSpectacle(List<Spectacle> listeSpectacle);

    /**
     * renvoie le nombre de places total vendues
     * 
     * @return
     */
    public int totalPlacesVendues();

    /**
     * renvoie la liste des spectacles en prenant soin de completer le champ
     * recette des beans Spectacle
     * 
     * @param listeSpectacle
     */
    public void listerSpectacleRentabilite(List<Spectacle> listeSpectacle);

    /**
     * retourne le spectacle le plus rentable
     * 
     * @return
     */
    public String spectacleLePlusRentable();
}
