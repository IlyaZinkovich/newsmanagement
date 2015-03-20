package com.epam.newsmanagement.model.persistence.interfaces;

import com.epam.newsmanagement.model.entity.ComplexNews;
import com.epam.newsmanagement.model.persistence.exception.DAOException;

public interface ComplexNewsDAO {
    int insert(ComplexNews complexNews) throws DAOException;
    ComplexNews findById(int id);
}
