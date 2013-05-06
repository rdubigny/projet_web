package com.gr15.dao;

import java.util.LinkedList;
import java.util.List;

import com.gr15.beans.AssociePlaceRepresentation;
import com.gr15.beans.Place;
import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Zone;

public interface PlaceDao {

    /**
     * génère la matrice des places à partir de la table zone.
     * 
     * @return
     */
    public Place[][] genererPlan();

    /**
     * check la disponibilité des places en consultants les tables reservation
     * et achat.
     * 
     * @param matricePlace
     * @param representation
     */
    public void updateDisponibilite(Place[][] matricePlace,
	    Representation representation);

    /**
     * ajoute une entrée dans la table réservation
     * 
     * @param idUtilisateur
     * @param idRepresentation
     * @param idPlaces
     */
    public void reserver(int idUtilisateur, int idRepresentation, int[] idPlaces);

    /**
     * 
     * ajoute une entrée dans la table achat. Effectue en une seule transaction
     * : 1. création d'un dossier 2. création des tickets avec moment de la
     * représentation 3. création des achats avec id de la représentation 4. si
     * estReserve vaut vrai supprime les reservations éventuelles
     * 
     * @param idUtilisateur
     * @param associeplacerepresentations
     * @param tickets
     * @param estReserve
     */
    public void acheter(int idUtilisateur,
	    LinkedList<AssociePlaceRepresentation> associeplacerepresentations,
	    List<Ticket> tickets, boolean estReserve);

    /**
     * Construit l'association des places à acheter en fonction de la
     * representation en fonction d'un tableau d'identifiant qui est considéré
     * non-vide. Si estReserve vaut faux le parametre idRepresentation n'est pas
     * utilisé.
     * 
     * @param ids
     * @param associeplacerepresentations
     * @param estReserve
     * @param idRepresentation
     */
    public void associer(int[] ids,
	    LinkedList<AssociePlaceRepresentation> associeplacerepresentations,
	    boolean estReserve, int idRepresentation);

    /**
     * liste les zones d'après le contenu de la table zone
     * 
     * @param zones
     * @param idRepresentation
     */
    public void listerZone(List<Zone> zones, int idRepresentation);

}
