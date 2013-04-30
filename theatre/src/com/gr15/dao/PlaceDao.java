package com.gr15.dao;

import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Utilisateur;

public interface PlaceDao {

    Place[][] genererPlan();

    public void updateDisponibilite(Place[][] matricePlace,
	    Representation representation);

    public void reserver(Utilisateur utilisateur,
	    Representation representation, String[] ids, boolean achat);

}
