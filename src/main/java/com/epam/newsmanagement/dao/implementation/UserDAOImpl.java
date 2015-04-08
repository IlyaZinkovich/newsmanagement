package com.epam.newsmanagement.dao.implementation;

import com.epam.newsmanagement.domain.User;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.UserDAO;
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
import static com.epam.newsmanagement.dao.implementation.DAOConstants.USER_ID;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private DataSource dataSource;

    private final static String UPDATE_USER_QUERY = "UPDATE News_User " +
            "set user_name = ?, login = ?, password = ? " +
            "WHERE user_id = ?";
    private final static String INSERT_USER_QUERY = "INSERT INTO News_User " +
            "(name, login, password)" +
            " VALUES (?) returning user_id into user_id";
    private final static String DELETE_USER_QUERY = "DELETE News_User WHERE user_id = ?";
    private final static String SELECT_USER_BY_ID_QUERY = "SELECT User.user_id, User.user_name, User.login, User.password " +
            "FROM News_User WHERE user_id = ?";
    private final static String SELECT_ALL_USERS_QUERY = "SELECT User.user_id, User.user_name, User.login, User.password " +
            "FROM News_User";

    private PreparedStatement prepareStatementForUpdate(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setLong(4, user.getId());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsert(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY, new String[]{USER_ID});
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForDelete(Connection connection, long userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
        preparedStatement.setLong(1, userId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_QUERY);
        return preparedStatement;
    }

    private List<User> parseResultSetToList(ResultSet resultSet) throws SQLException {
        List<User> list = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            list.add(user);
        }
        return list;
    }

    private User parseResultSetToObject(ResultSet resultSet) throws SQLException {
        User user = null;
        while(resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            break;
        }
        return user;
    }

    @Override
    public long insert(User user) throws DAOException {
        long lastInsertId = 0;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForInsert(connection, user)) {
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet != null && resultSet.next())
                    lastInsertId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        return lastInsertId;
    }

    @Override
    public void update(User user) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForUpdate(connection, user)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void delete(long userId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForDelete(connection, userId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<User> findAll() throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindAll(connection);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public User findById(long userId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindById(connection, userId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}
