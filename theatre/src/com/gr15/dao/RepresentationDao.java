package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Representation;

public interface RepresentationDao {

    public void listerParSpectacle(long idSpectacle,
	    List<Representation> listeRepresentation);

    public Representation trouver(String id);

}
