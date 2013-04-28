package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Spectacle;

public interface SpectacleDao {

    public void lister(List<Spectacle> listeSpectacle);

    public Spectacle trouver(String id);

}
