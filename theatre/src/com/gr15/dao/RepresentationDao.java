package com.gr15.dao;

import java.util.List;

import com.gr15.beans.Representation;

public interface RepresentationDao {

    public void lister( List<Representation> listeRepresentation );

    public Representation trouver( String id );

    public void supprimer( String idRepresentation );

	void listerParSpectacle(String idSpectacle, int idUtilisateur,
			List<Representation> listeRepresentation);

}
