package com.gr15.dao;

import com.gr15.beans.Utilisateur;

public interface UtilisateurDao {

    /**
     * retourne l'utilisateur en fonction du login
     * 
     * @param login
     * @return
     * @throws DAOException
     */
    public Utilisateur trouver(String login) throws DAOException;
}
