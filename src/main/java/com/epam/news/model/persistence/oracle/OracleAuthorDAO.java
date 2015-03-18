package com.epam.news.model.persistence.oracle;


import com.epam.news.model.entity.Author;
import com.epam.news.model.persistence.interfaces.AuthorDAO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleAuthorDAO extends AbstractOracleDAO<Author> implements AuthorDAO {

    private final String UPDATE_AUTHOR_QUERY = "UPDATE Author " +
            "set name = ? " +
            "WHERE author_id = ?";
    private final String INSERT_AUTHOR_QUERY = "INSERT INTO Author " +
            "(name)" +
            " VALUES (?)";
    private final String DELETE_AUTHOR_QUERY = "DELETE Author WHERE author_id = ?";
    private final String SELECT_AUTHOR_BY_ID_QUERY = "SELECT * FROM Author WHERE author_id = ?";
    private final String SELECT_AUTHOR_BY_NAME_QUERY = "SELECT * FROM Author WHERE name = ?";
    private final String SELECT_LAST_INSERTED = "SELECT * FROM Author WHERE AUTHOR_ID=(SELECT MAX(AUTHOR_ID) from Author)";


    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setInt(2, author.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR_QUERY);
        preparedStatement.setString(1, author.getName());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_QUERY);
        preparedStatement.setInt(1, author.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindByID(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME_QUERY);
        preparedStatement.setString(1, name);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement prepareStatementForFindLastInserted(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LAST_INSERTED);
        return preparedStatement;
    }

    @Override
    protected List<Author> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Author> list = new LinkedList<>();
        while (resultSet.next()) {
            Author author = new Author();
            author.setId(resultSet.getInt("author_id"));
            author.setName(resultSet.getString("name"));
            list.add(author);
        }
        return list;
    }

    @Override
    public Author findByName(String name) {
        List<Author> items = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindByName(connection, name);
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
}
