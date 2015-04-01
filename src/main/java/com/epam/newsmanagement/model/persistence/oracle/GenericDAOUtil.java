package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.epam.newsmanagement.model.persistence.oracle.PersistenceConstants.NULL_ID;

@Component
public class GenericDAOUtil<Item> {

    @Autowired
    private DataSource dataSource;

    public GenericDAOUtil() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long insert(Item item, DAOHelper<Item> dao) throws DAOException {
        long lastInsertId = 0;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
             PreparedStatement preparedStatement = dao.prepareStatementForInsert(connection, item)) {
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet != null && resultSet.next())
                    lastInsertId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return lastInsertId;
    }

    public void update(Item item, DAOHelper<Item> dao) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = dao.prepareStatementForUpdate(connection, item)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void delete(long itemId, DAOHelper<Item> dao) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = dao.prepareStatementForDelete(connection, itemId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Item findById(long id, DAOHelper<Item> dao) throws DAOException {
        if (id == NULL_ID) return null;
        List<Item> items = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = dao.prepareStatementForFindById(connection, id);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            items = dao.parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        if (items.isEmpty()) return null;
        return items.get(0);
    }

    public List<Item> findAll(DAOHelper<Item> dao) throws DAOException {
        List<Item> items = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = dao.prepareStatementForFindAll(connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            items = dao.parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return items;
    }
}