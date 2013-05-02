package com.gr15.dao;

import com.gr15.beans.Reservation;

import java.util.List;

public interface ReservationDao {
	
    public List<Reservation> lister(int idClient);
    
    public void annuler(int idReservation);
	
}
