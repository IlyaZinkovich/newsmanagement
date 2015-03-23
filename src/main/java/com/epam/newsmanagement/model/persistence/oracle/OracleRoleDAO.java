package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.entity.Role;
import com.epam.newsmanagement.model.persistence.interfaces.RoleDAO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleRoleDAO extends AbstractOracleDAO<Role> implements RoleDAO {

    private final String UPDATE_ROLE_QUERY = "UPDATE Role " +
            "set role_name = ? " +
            "WHERE user_id = ?";
    private final String INSERT_ROLE_QUERY = "INSERT INTO Role (role_name) VALUES (?)";
    private final String DELETE_ROLE_QUERY = "DELETE Role WHERE role_id = ?";
    private final String SELECT_ROLE_BY_USER_ID_QUERY = "SELECT * FROM Role WHERE user_id = ?";

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_QUERY);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setInt(2, role.getUserId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE_QUERY);
        preparedStatement.setString(1, role.getName());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_QUERY);
        preparedStatement.setInt(1, role.getUserId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindById(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_USER_ID_QUERY);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<Role> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Role> list = new LinkedList<>();
        while (resultSet.next()) {
            Role role = new Role();
            role.setUserId(resultSet.getInt("user_id"));
            role.setName(resultSet.getString("role_name"));
            list.add(role);
        }
        return list;
    }
}
