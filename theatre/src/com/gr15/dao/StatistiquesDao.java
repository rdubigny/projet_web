package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Spectacle;
import com.gr15.beans.Utilisateur;

public interface StatistiquesDao {

    public Utilisateur clientOr();

    public void placesVenduesParSpectacle( List<Spectacle> listeSpectacle );

    public int totalPlacesVendues();

    public void listerSpectacleRentabilite( List<Spectacle> listeSpectacle );

    public String spectacleLePlusRentable();
}
