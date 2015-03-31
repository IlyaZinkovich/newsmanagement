package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.Role;
import com.epam.newsmanagement.model.entity.User;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

public interface UserDAO extends GenericDAO<User> {
    User findById(long userId) throws DAOException;
}
