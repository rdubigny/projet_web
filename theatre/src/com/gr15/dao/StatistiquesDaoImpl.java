package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatistiquesDaoImpl implements StatistiquesDao {

    private DAOFactory          daoFactory;
    private static final String SQL_TOTAL_PLACES_VENDUES = "select (count(*)) as total from projweb_db.achat a ";

    StatistiquesDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
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
}
