package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.entity.Role;
import com.epam.newsmanagement.model.entity.User;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.RoleDAO;
import com.epam.newsmanagement.model.persistence.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleUserDAO extends AbstractOracleDAO<User> implements UserDAO {

    private final String UPDATE_USER_QUERY = "UPDATE News_User " +
            "set user_name = ?, login = ?, password = ? " +
            "WHERE user_id = ?";
    private final String INSERT_USER_QUERY = "INSERT INTO News_User " +
            "(name, login, password)" +
            " VALUES (?) returning user_id into user_id";
    private final String DELETE_USER_QUERY = "DELETE News_User WHERE user_id = ?";
    private final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM News_User WHERE user_id = ?";
    private final String SELECT_ALL_USERS_QUERY = "SELECT * FROM News_User";

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setInt(4, user.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY, new String[]{"user_id"});
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
        preparedStatement.setInt(1, user.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindById(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_QUERY);
        return preparedStatement;
    }

    @Override
    protected List<User> parseResultSet(ResultSet resultSet) throws SQLException {
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
}
