package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.domain.User;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import static com.epam.newsmanagement.model.persistence.oracle.PersistenceConstants.USER_ID;

@Component
public class OracleUserDAO implements UserDAO, DAOHelper<User> {

    @Autowired
    private GenericDAOUtil<User> daoUtil;

    @Autowired
    private DataSource dataSource;

    private final String UPDATE_USER_QUERY = "UPDATE News_User " +
            "set user_name = ?, login = ?, password = ? " +
            "WHERE user_id = ?";
    private final String INSERT_USER_QUERY = "INSERT INTO News_User " +
            "(name, login, password)" +
            " VALUES (?) returning user_id into user_id";
    private final String DELETE_USER_QUERY = "DELETE News_User WHERE user_id = ?";
    private final String SELECT_USER_BY_ID_QUERY = "SELECT User.user_id, User.user_name, User.login, User.password " +
            "FROM News_User WHERE user_id = ?";
    private final String SELECT_ALL_USERS_QUERY = "SELECT User.user_id, User.user_name, User.login, User.password " +
            "FROM News_User";

    @Override
    public PreparedStatement prepareStatementForUpdate(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setLong(4, user.getId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForInsert(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY, new String[]{USER_ID});
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getLogin());
        preparedStatement.setString(3, user.getPassword());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForDelete(Connection connection, long userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
        preparedStatement.setLong(1, userId);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_QUERY);
        return preparedStatement;
    }

    @Override
    public List<User> parseResultSet(ResultSet resultSet) throws SQLException {
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

    @Override
    public long insert(User item) throws DAOException {
        return daoUtil.insert(item, this);
    }

    @Override
    public void update(User item) throws DAOException {
        daoUtil.update(item, this);
    }

    @Override
    public void delete(long userId) throws DAOException {
        daoUtil.delete(userId, this);
    }

    @Override
    public List<User> findAll() throws DAOException {
        return daoUtil.findAll(this);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public User findById(long userId) throws DAOException {
        return daoUtil.findById(userId, this);
    }
}
