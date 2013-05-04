package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gr15.beans.Spectacle;

public class StatistiquesDaoImpl implements StatistiquesDao {

    private DAOFactory          daoFactory;
    private static final String SQL_TOTAL_PLACES_VENDUES               = "select (count(*)) as total from projweb_db.achat a ";
    private static final String SQL_TOTAL_PLACES_VENDUES_PAR_SPECTACLE = "select s.id_spectacle, s.nom_spectacle, (count(*)) as total from projweb_db.achat a ,"
                                                                               +
                                                                               " projweb_db.spectacle s, projweb_db.representation r where a.id_representation = r.id_representation "
                                                                               +
                                                                               "and r.id_spectacle = s.id_spectacle group by s.nom_spectacle";

    StatistiquesDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
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
                listeSpectacle.add( map( resultSet ) );
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

    private static Spectacle map( ResultSet resultSet ) throws SQLException {
        Spectacle spectacle = new Spectacle();
        spectacle.setId( resultSet.getInt( "id_spectacle" ) );
        spectacle.setNom( resultSet.getString( "nom_spectacle" ) );
        spectacle.setPlacesVendues( resultSet.getInt( "total" ) );
        return spectacle;
    }

}
