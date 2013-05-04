package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Spectacle;

public interface StatistiquesDao {

    public void placesVenduesParSpectacle( List<Spectacle> listeSpectacle );

    public int totalPlacesVendues();

    public void listerSpectacleRentabilite( List<Spectacle> listeSpectacle );

    public String spectacleLePlusRentable();
}
