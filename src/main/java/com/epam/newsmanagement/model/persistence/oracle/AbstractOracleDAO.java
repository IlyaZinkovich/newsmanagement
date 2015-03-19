package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.persistence.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public abstract class AbstractOracleDAO<Item> {

    @Autowired
    protected DataSource dataSource;

    protected abstract PreparedStatement prepareStatementForUpdate(Connection connection, Item item) throws SQLException;
    protected abstract PreparedStatement prepareStatementForInsert(Connection connection, Item item) throws SQLException;
    protected abstract PreparedStatement prepareStatementForDelete(Connection connection, Item item) throws SQLException;
    protected abstract PreparedStatement prepareStatementForFindByID(Connection connection, int id) throws SQLException;
    protected abstract PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException;
    protected abstract List<Item> parseResultSet(ResultSet resultSet) throws SQLException;

    public int insert(Item item) throws DAOException {
        Connection connection = null;
        int lastInsertId = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForInsert(connection, item);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet != null && resultSet.next()) {
                lastInsertId = resultSet.getInt(1);
            }
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
        return lastInsertId;
    }

    public void update(Item item) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForUpdate(connection, item);
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

    public void delete(Item item) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForDelete(connection, item);
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

    public Item findById(int id) {
        List<Item> items = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindByID(connection, id);
            ResultSet rs = preparedStatement.executeQuery();
            items = parseResultSet(rs);
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
        if (items.isEmpty()) return null;
        return items.get(0);
    }

    public List<Item> findAll() {
        List<Item> items = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindAll(connection);
            ResultSet rs = preparedStatement.executeQuery();
            items = parseResultSet(rs);
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
        return items;
    }


}
