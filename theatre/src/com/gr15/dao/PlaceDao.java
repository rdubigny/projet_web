package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Utilisateur;

public interface PlaceDao {

    Place[][] genererPlan();

    public void updateDisponibilite(Place[][] matricePlace,
	    Representation representation);

    public void acheter(Utilisateur utilisateur, Representation representation,
	    String[] ids, List<Ticket> tickets);

    public void reserver(Utilisateur utilisateur,
	    Representation representation, String[] ids);

}
