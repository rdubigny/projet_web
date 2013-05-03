package com.gr15.dao;

import com.gr15.beans.Reservation;

import java.util.List;

public interface ReservationDao {
	
    public void listerParReservation(int idUtilisateur,List<Reservation> listeReservation);
    
	void annulerReservation(int idReservation);
	
}
