package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.GenericDAO;
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

    public long insert(Item item, GenericDAO dao) throws DAOException {
        Connection connection = null;
        long lastInsertId = 0;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = dao.prepareStatementForInsert(connection, item);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                lastInsertId = resultSet.getLong(1);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lastInsertId;
    }

    public void update(Item item, GenericDAO dao) {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = dao.prepareStatementForUpdate(connection, item);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Item item, GenericDAO dao) {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = dao.prepareStatementForDelete(connection, item);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Item findById(long id, GenericDAO dao) {
        if (id == NULL_ID) return null;
        List<Item> items = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = dao.prepareStatementForFindById(connection, id);
            resultSet = preparedStatement.executeQuery();
            items = dao.parseResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (items.isEmpty()) return null;
        return items.get(0);
    }

    public List<Item> findAll(GenericDAO dao) {
        List<Item> items = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = dao.prepareStatementForFindAll(connection);
            resultSet = preparedStatement.executeQuery();
            items = dao.parseResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

}
