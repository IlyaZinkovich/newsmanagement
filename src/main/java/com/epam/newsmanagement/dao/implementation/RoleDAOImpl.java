package com.epam.newsmanagement.dao.implementation;

import com.epam.newsmanagement.domain.Role;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.RoleDAO;
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

@Component
public class RoleDAOImpl implements RoleDAO{

    @Autowired
    private DataSource dataSource;

    private final static String UPDATE_ROLE_QUERY = "UPDATE Role " +
            "SET role_name = ? " +
            "WHERE user_id = ?";
    private final static String INSERT_ROLE_QUERY = "INSERT INTO Role (role_name) VALUES (?)";
    private final static String DELETE_ROLE_QUERY = "DELETE Role WHERE role_id = ?";
    private final static String SELECT_ROLE_BY_USER_ID_QUERY = "SELECT Role.user_id, Role.role_name " +
            "FROM Role WHERE user_id = ?";

    private PreparedStatement prepareStatementForUpdate(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_QUERY);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setLong(2, role.getUserId());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsert(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE_QUERY);
        preparedStatement.setString(1, role.getName());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForDelete(Connection connection, long roleId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_QUERY);
        preparedStatement.setLong(1, roleId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindByUserId(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_USER_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    private List<Role> parseResultSetToList(ResultSet resultSet) throws SQLException {
        List<Role> list = new LinkedList<>();
        while (resultSet.next()) {
            Role role = new Role();
            role.setUserId(resultSet.getInt("user_id"));
            role.setName(resultSet.getString("role_name"));
            list.add(role);
        }
        return list;
    }

    private Role parseResultSetToObject(ResultSet resultSet) throws SQLException {
        Role role = null;
        while(resultSet.next()) {
            role = new Role();
            role.setUserId(resultSet.getInt("user_id"));
            role.setName(resultSet.getString("role_name"));
            break;
        }
        return role;
    }

    @Override
    public long insert(Role role) throws DAOException {
        long lastInsertId = 0;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForInsert(connection, role)) {
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
    public void update(Role role) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForUpdate(connection, role)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void delete(long roleId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForDelete(connection, roleId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Role> findAll() throws DAOException {
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
    public Role findById(long id) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role findByUserId(long userId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindByUserId(connection, userId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}
