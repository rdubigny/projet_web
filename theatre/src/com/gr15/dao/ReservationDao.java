package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Reservation;

public interface ReservationDao {

    /**
     * liste toutes les réservations contenues dans la table reservation
     * 
     * @param listeReservation
     */
    public void lister(List<Reservation> listeReservation);

    /**
     * liste les réservations associées à l'utilisateur dont l'identifiant est
     * fourni en paramêtre.
     * 
     * @param idUtilisateur
     * @param listeReservation
     */
    public void listerParReservation(int idUtilisateur,
	    List<Reservation> listeReservation);

    /**
     * Supprime une réservation en vérifiant que l'utilisateur dont
     * l'identifiant est fourni en paramêtre est l'initiateur de la réservation.
     * 
     * @param idUtilisateur
     * @param idReservation
     */
    void annulerReservation(int idUtilisateur, int idReservation);

    /**
     * Supprime la réservation.
     * 
     * @param idReservation
     */
    void annulerReservation(int idReservation);

}
