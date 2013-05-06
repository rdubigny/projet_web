package com.gr15.dao;

import java.util.List;
import java.util.LinkedList;

import com.gr15.beans.AssociePlaceRepresentation;
import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Zone;

public interface PlaceDao {

    Place[][] genererPlan();

    public void updateDisponibilite(Place[][] matricePlace,
	    Representation representation);

    public void reserver(int idUtilisateur, int idRepresentation, int[] idPlaces);
	

    public void acheter(int idUtilisateur, LinkedList<AssociePlaceRepresentation> associeplacerepresentations,
			List<Ticket> tickets, boolean estReserve);

    public void associer(int[] ids,
			LinkedList<AssociePlaceRepresentation> associeplacerepresentations,
			boolean estReserve, int idRepresentation);
	
    public void listerZone(List<Zone> zones, int idRepresentation);

}
