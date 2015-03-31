package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.entity.Role;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.RoleDAO;
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
public class OracleRoleDAO implements RoleDAO {

    @Autowired
    private GenericDAOUtil<Role> daoUtil;

    @Autowired
    private DataSource dataSource;

    private final String UPDATE_ROLE_QUERY = "UPDATE Role " +
            "set role_name = ? " +
            "WHERE user_id = ?";
    private final String INSERT_ROLE_QUERY = "INSERT INTO Role (role_name) VALUES (?)";
    private final String DELETE_ROLE_QUERY = "DELETE Role WHERE role_id = ?";
    private final String SELECT_ROLE_BY_USER_ID_QUERY = "SELECT Role.user_id, Role.role_name " +
            "FROM Role WHERE user_id = ?";

    @Override
    public PreparedStatement prepareStatementForUpdate(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_QUERY);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setLong(2, role.getUserId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForInsert(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE_QUERY);
        preparedStatement.setString(1, role.getName());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForDelete(Connection connection, Role role) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_QUERY);
        preparedStatement.setLong(1, role.getUserId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_USER_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Role> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Role> list = new LinkedList<>();
        while (resultSet.next()) {
            Role role = new Role();
            role.setUserId(resultSet.getInt("user_id"));
            role.setName(resultSet.getString("role_name"));
            list.add(role);
        }
        return list;
    }

    @Override
    public long insert(Role item) throws DAOException {
        return daoUtil.insert(item, this);
    }

    @Override
    public void update(Role item) throws DAOException {
        daoUtil.update(item, this);
    }

    @Override
    public void delete(Role item) throws DAOException {
        daoUtil.delete(item, this);
    }

    @Override
    public List<Role> findAll() {
        return daoUtil.findAll(this);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
