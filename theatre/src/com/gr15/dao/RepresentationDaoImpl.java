package com.gr15.dao;

import static com.gr15.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gr15.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.gr15.beans.Representation;

public class RepresentationDaoImpl implements RepresentationDao {
    private DAOFactory          daoFactory;
    private static final String SQL_SELECT        = "SELECT * FROM representation WHERE id_spectacle=?";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM representation WHERE id_representation=?";
    private static final String SQL_SELECT_CHRONO = "SELECT id_representation, s.nom_spectacle, r.moment_representation FROM projweb_db.spectacle s, projweb_db.representation r WHERE r.id_spectacle = s.id_spectacle ORDER BY r.moment_representation";

    RepresentationDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    public void lister( List<Representation> listeRepresentation ) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_SELECT_CHRONO, false );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            while ( resultSet.next() ) {
                listeRepresentation.add( map_admin( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

    }

    @Override
    public void listerParSpectacle( long idSpectacle,
            List<Representation> listeRepresentation ) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_SELECT, false, idSpectacle );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            while ( resultSet.next() ) {
                /* on ne s'intéresse qu'aux représentation à venir */
                if ( !resultSet.getTimestamp( "moment_representation" ).before(
                        DateTime.now().plusHours( 1 ).toDate() ) )
                    listeRepresentation.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
    }

    @Override
    public Representation trouver( String id ) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Representation representation = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_SELECT_PAR_ID, false, id );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if ( resultSet.next() ) {
                representation = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return representation;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des Représentation (un
     * ResultSet) et un bean Représentation.
     */
    private static Representation map( ResultSet resultSet ) throws SQLException {
        Representation representation = new Representation();
        representation.setId( resultSet.getLong( "id_representation" ) );
        representation.setIdSpectacle( resultSet.getLong( "id_spectacle" ) );
        representation.setDate( new DateTime( resultSet
                .getTimestamp( "moment_representation" ) ) );
        return representation;
    }

    /*
     * A terme il serait peut être judicieux de fusionner ces deux méthodes
     * utilitaires en une seule. en effet, il faudrait faire un test avant
     * chaque setter pour vérifier que dans la requête on a bien récuperer le
     * champ correspondant ...
     */

    private static Representation map_admin( ResultSet resultSet ) throws SQLException {
        Representation representation = new Representation();
        representation.setId( resultSet.getLong( "id_representation" ) );
        representation.setDate( new DateTime( resultSet
                .getTimestamp( "moment_representation" ) ) );
        representation.setNomSpectacle( resultSet.getString( "nom_spectacle" ) );
        return representation;
    }

}
