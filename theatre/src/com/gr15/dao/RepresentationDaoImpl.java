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
    private static final String SQL_SELECT_PAR_ID          = "SELECT r.id_representation, s.nom_spectacle, r.moment_representation"
                                                                   + " FROM projweb_db.representation r, projweb_db.spectacle s "
                                                                   + "WHERE s.id_spectacle = r.id_spectacle AND r.id_representation=?";

    private static final String SQL_SELECT_REPRESENTATIONS = "SELECT r.id_representation, s.nom_spectacle, r.moment_representation "
                                                                   + "	FROM projweb_db.representation r, projweb_db.utilisateur u, projweb_db.spectacle s"
                                                                   + "	WHERE s.id_spectacle = r.id_spectacle "
                                                                   + "		AND s.id_spectacle = ?"
                                                                   + "		AND u.id_utilisateur = ?"
                                                                   + "		AND CURTIME() < r.moment_representation "
                                                                   + "		AND ((u.type_utilisateur = 'client' AND CURTIME() < SUBTIME(r.moment_representation, '0 01:00:00') "
                                                                   + "				 OR (u.type_utilisateur = 'guichet'))) ORDER BY r.moment_representation";

    private static final String SQL_SELECT_CHRONO          = "SELECT id_representation, s.nom_spectacle, "
                                                                   + "r.moment_representation FROM projweb_db.spectacle s, projweb_db.representation r "
                                                                   + "WHERE r.id_spectacle = s.id_spectacle ORDER BY r.moment_representation";

    private static final String SQL_DELETE                 = "DELETE FROM projweb_db.representation where id_representation = ?";

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
    public void listerParSpectacle( String idSpectacle, long idUtilisateur,
            List<Representation> listeRepresentation ) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_SELECT_REPRESENTATIONS, false, idSpectacle,
                    idUtilisateur );
            resultSet = preparedStatement.executeQuery();
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
                representation = map_admin( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return representation;
    }

    // TODO notes à supprimer en temps voulu :
    // if ( !resultSet.getTimestamp( "moment_representation" ).before(
    // DateTime.now().plusHours( 1 ).toDate() ) )

    public Representation supprimer( String id ) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Representation representation = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion,
                    SQL_DELETE, false, id );
            resultSet = preparedStatement.executeQuery();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return representation;
    }

    private static Representation map_admin( ResultSet resultSet )

            throws SQLException {
        Representation representation = new Representation();
        representation.setId( resultSet.getLong( "id_representation" ) );
        representation.setIdSpectacle( resultSet.getLong( "id_spectacle" ) );
        representation.setDate( new DateTime( resultSet
                .getTimestamp( "moment_representation" ) ) );
        representation.setNomSpectacle( resultSet.getString( "nom_spectacle" ) );
        return representation;
    }

}
