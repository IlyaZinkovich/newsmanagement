package com.epam.newsmanagement.dao;

import com.epam.newsmanagement.domain.Role;
import com.epam.newsmanagement.dao.exception.DAOException;

/**
 * A DatabaseAccessObject interface that
 * provides access to roles data in the data source.
 */
public interface RoleDAO extends GenericDAO<Role> {
    Role findByUserId(long userId) throws DAOException;
}
