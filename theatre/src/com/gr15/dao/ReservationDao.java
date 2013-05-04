package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Reservation;

public interface ReservationDao {

    public void lister( List<Reservation> listeReservation );

    public void listerParReservation( int idUtilisateur, List<Reservation> listeReservation );
    
	void annulerReservation(int idUtilisateur, int idReservation);
	
	void annulerReservation(int  idReservation );

}
