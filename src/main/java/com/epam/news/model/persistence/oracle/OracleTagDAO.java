package com.epam.news.model.persistence.oracle;

import com.epam.news.model.entity.Tag;
import com.epam.news.model.persistence.interfaces.TagDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OracleTagDAO extends AbstractOracleDAO<Tag> implements TagDAO {

    private final String UPDATE_TAG_QUERY = "UPDATE Tag " +
            "set tag_name = ? " +
            "WHERE tag_id = ?";
    private final String INSERT_TAG_QUERY = "INSERT INTO Tag " +
            "tag_name" +
            " VALUES ?";
    private final String DELETE_TAG_QUERY = "DELETE Tag WHERE tag_id = ?";
    private final String SELECT_TAG_BY_ID_QUERY = "SELECT * FROM Tag WHERE tag_id = ?";

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TAG_QUERY);
        preparedStatement.setString(1, tag.getName());
        preparedStatement.setInt(2, tag.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAG_QUERY);
        preparedStatement.setString(1, tag.getName());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TAG_QUERY);
        preparedStatement.setInt(1, tag.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindByID(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<Tag> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Tag> list = new LinkedList<>();
        while (resultSet.next()) {
            Tag tag = new Tag();
            tag.setId(resultSet.getInt("tag_id"));
            tag.setName(resultSet.getString("tag_name"));
            list.add(tag);
        }
        return list;
    }
}
