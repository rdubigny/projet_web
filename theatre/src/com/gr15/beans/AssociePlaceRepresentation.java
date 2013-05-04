package com.gr15.beans;

public class AssociePlaceRepresentation {
	private int idRepresentation;
	private int idPlace;
	
	public AssociePlaceRepresentation(int idRepresentation, int idPlace) {
		super();
		this.idRepresentation = idRepresentation;
		this.idPlace = idPlace;
	}
	
	public int getIdPlace() {
		return idPlace;
	}
	public void setIdPlace(int idPlace) {
		this.idPlace = idPlace;
	}
	public int getIdRepresentation() {
		return idRepresentation;
	}
	public void setIdRepresentation(int idRepresentation) {
		this.idRepresentation = idRepresentation;
	}
}
