package com.gr15.dao;

import com.gr15.beans.Utilisateur;

public interface UtilisateurDao {

    Utilisateur trouver(String login) throws DAOException;
}
