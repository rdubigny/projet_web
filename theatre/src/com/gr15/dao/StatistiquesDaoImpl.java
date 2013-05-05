package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gr15.beans.Spectacle;
import com.gr15.beans.Utilisateur;

public class StatistiquesDaoImpl implements StatistiquesDao {

    private DAOFactory          daoFactory;
    private static final String SQL_TOTAL_PLACES_VENDUES               = "select (count(*)) as total from projweb_db.achat a ";
    private static final String SQL_TOTAL_PLACES_VENDUES_PAR_SPECTACLE = "select s.id_spectacle, s.nom_spectacle, (count(*)) as total from projweb_db.achat a ,"
                                                                               +
                                                                               " projweb_db.spectacle s, projweb_db.representation r where a.id_representation = r.id_representation "
                                                                               +
                                                                               "and r.id_spectacle = s.id_spectacle group by s.nom_spectacle";

    private static final String SQL_LISTE_SPECTACLE_RENTABLE           = "select s.id_spectacle, s.nom_spectacle,(sum(s.base_prix*z.base_pourcentage_prix/100)) as tc "
                                                                               +
                                                                               "from projweb_db.zone z, projweb_db.place p, projweb_db.achat a , projweb_db.spectacle s, projweb_db.representation r "
                                                                               +
                                                                               "where a.id_representation = r.id_representation "
                                                                               +
                                                                               "and r.id_spectacle = s.id_spectacle "
                                                                               +
                                                                               "and  p.id_zone = z.id_zone and a.id_place = p.id_place "
                                                                               +
                                                                               "group by s.nom_spectacle order by tc DESC";
	private static final String SQL_SPECTACLE_LE_PLUS_RENTABLE         = "select s.id_spectacle, s.nom_spectacle,(sum(s.base_prix*z.base_pourcentage_prix/100)) as tc "
																		  +
																		  "from projweb_db.zone z, projweb_db.place p, projweb_db.achat a , projweb_db.spectacle s, projweb_db.representation r "
																          +
																          "where a.id_representation = r.id_representation "
																          +
																          "and r.id_spectacle = s.id_spectacle "
																          +
																          "and  p.id_zone = z.id_zone and a.id_place = p.id_place "
																          +
																          "group by s.nom_spectacle order by tc DESC limit 1";


    private static final String SQL_CLIENT_OR                          = "select  u.nom, u.prenom, ((sum(s.base_prix*z.base_pourcentage_prix/100))) "
                                                                               +
                                                                               "as montant_total from projweb_db.utilisateur u, projweb_db.zone z, "
                                                                               +
                                                                               "projweb_db.place p, projweb_db.achat a , projweb_db.spectacle s, projweb_db.representation r "
                                                                               +
                                                                               "where a.id_representation = r.id_representation and r.id_spectacle = s.id_spectacle "
                                                                               +
                                                                               "and u.id_utilisateur = a.id_utilisateur and u.type_utilisateur != 'guichet' and p.id_zone = z.id_zone  and a.id_place = p.id_place group by u.id_utilisateur order by  (sum(s.base_prix*z.base_pourcentage_prix/100)) desc limit 1";

    StatistiquesDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    public Utilisateur clientOr() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;
        try {
            /* R�cup�ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_CLIENT_OR, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de donn�es de l'�ventuel ResulSet
             * retourn�
             */
            while ( resultSet.next() ) {
                utilisateur = mapClient( resultSet );
            }

            return utilisateur;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }

    public String spectacleLePlusRentable() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String spectacle = null;
        try {
            /* R�cup�ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_SPECTACLE_LE_PLUS_RENTABLE, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de donn�es de l'�ventuel ResulSet
             * retourn�
             */
            while ( resultSet.next() ) {
                spectacle = resultSet.getString( "nom_spectacle" );
            }

            return spectacle;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }

    public void placesVenduesParSpectacle( List<Spectacle> listeSpectacle ) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            /* R�cup�ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_TOTAL_PLACES_VENDUES_PAR_SPECTACLE, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de donn�es de l'�ventuel ResulSet
             * retourn�
             */
            while ( resultSet.next() ) {
                listeSpectacle.add( mapPlaces( resultSet ) );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

    }

    public int totalPlacesVendues() {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int placesVendues = 0;
        try {
            /* R�cup�ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_TOTAL_PLACES_VENDUES, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de donn�es de l'�ventuel ResulSet
             * retourn�
             */
            while ( resultSet.next() ) {
                placesVendues = resultSet.getInt( "total" );
            }

            return placesVendues;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }

    public void listerSpectacleRentabilite( List<Spectacle> listeSpectacle ) {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            /* R�cup�ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_LISTE_SPECTACLE_RENTABLE, false );
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de donn�es de l'�ventuel ResulSet
             * retourn�
             */
            while ( resultSet.next() ) {
                listeSpectacle.add( mapRentable( resultSet ) );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

    }

    private static Spectacle mapRentable( ResultSet resultSet ) throws SQLException {
        Spectacle spectacle = new Spectacle();
        spectacle.setId( resultSet.getInt( "id_spectacle" ) );
        spectacle.setNom( resultSet.getString( "nom_spectacle" ) );
        spectacle.setRecette( resultSet.getFloat( "tc" ) );
        return spectacle;
    }

    private static Spectacle mapPlaces( ResultSet resultSet ) throws SQLException {
        Spectacle spectacle = new Spectacle();
        spectacle.setId( resultSet.getInt( "id_spectacle" ) );
        spectacle.setNom( resultSet.getString( "nom_spectacle" ) );
        spectacle.setPlacesVendues( resultSet.getInt( "total" ) );
        return spectacle;
    }

    private static Utilisateur mapClient( ResultSet resultSet ) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom( resultSet.getString( "nom" ) );
        utilisateur.setPrenom( resultSet.getString( "prenom" ) );
        return utilisateur;
    }

}
