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

    public void acheter(int idUtilisateur, int idRepresentation,
	    String[] ids, List<Ticket> tickets, boolean estReserve);

    public void reserver(int idUtilisateur, int idRepresentation,String[] ids);

}
