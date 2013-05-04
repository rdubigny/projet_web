package com.gr15.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gr15.beans.Representation;
import com.gr15.beans.Ticket;
import com.gr15.beans.Utilisateur;
import com.gr15.dao.DAOException;
import com.gr15.dao.PlaceDao;

public class PlaceForm {
	public static final String PARAM_PLACE_ID = "id";
	public static final String PARAM_ACTION = "action";
	public static final String PARAM_RESERVATION = "reservation";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
	public static final String ATT_REPRESENTATION = "representation";

	private String resultat;
	private String erreur = null;
	private PlaceDao placeDao;

	public PlaceForm(PlaceDao placeDao) {
		this.placeDao = placeDao;
	}

	public String getResultat() {
		return resultat;
	}

	public String getErreur() {
		return erreur;
	}

	public List<Ticket> reserver(HttpServletRequest request) {
		List<Ticket> listeTickets = new ArrayList<Ticket>();

		/* boolean spécifiant s'il s'agit d'une réservation */
		boolean isReservation = PARAM_RESERVATION.equals(request
				.getParameter(PARAM_ACTION));

		/* récupération des places sélectionnées en String et convertion en int */
		String[] idss = request.getParameterValues(PARAM_PLACE_ID);
		
		try {

			if (idss == null) {
				erreur = "Veuillez sélectionner au moins une place pour procéder à l'achat";
			} else {
				/*
				 * récupération de l'utilisateur et de la représentation en
				 * sélection
				 */
				HttpSession session = request.getSession();
				int idUtilisateur = ((Utilisateur) session
						.getAttribute(ATT_SESSION_USER)).getId();
				int[] idRepresentations = new int[1];
				idRepresentations[0] = ((Representation) session
						.getAttribute(ATT_REPRESENTATION)).getId();
				int[] ids = new int[idss.length]; 
				for(int i=0;i<idss.length;i++){
					ids[i] = Integer.parseInt(idss[i]);	
				}
				int[][] idPlaces = new int[1][ids.length];
				for(int i=0;i<ids.length;i++){
					idPlaces[0][i]=ids[i];
				}

				/* enregistrement de la réservation ou de l'achat */
				if (isReservation) {
					placeDao.reserver(idUtilisateur, idRepresentations[0], ids);
					listeTickets = null;
				} else
					placeDao.acheter(idUtilisateur, idRepresentations,
							idPlaces, listeTickets, false);
			}

			if (erreur == null) {
				if (idss.length > 1)
					resultat = isReservation ? "Vos places ont été réservées avec succès"
							: "Vos places ont été achetées avec succés";
				else
					resultat = isReservation ? "Votre place a été réservée avec succès"
							: "Votre place é été achetée avec succés";
			} else {
				resultat = isReservation ? "Echec de la réservation"
						: "Echec de l'achat";
			}
		} catch (DAOException e) {
			resultat = "Echec de l'opération : une erreur imprévue est survenue.";
			erreur = e.getMessage();
			e.printStackTrace();
		}

		return listeTickets;
	}
}
