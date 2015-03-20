package com.epam.newsmanagement.model.persistence.oracle;


import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
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
            " VALUES (?) returning author_id into author_id";
    private final String INSERT_NEWS_AUTHOR = "INSERT INTO News_Author " +
            "(news_id, author_id)" +
            " VALUES (?, ?)";
    private final String DELETE_AUTHOR_QUERY = "DELETE Author WHERE author_id = ?";
    private final String SELECT_AUTHOR_BY_ID_QUERY = "SELECT * FROM Author WHERE author_id = ?";
    private final String SELECT_AUTHOR_BY_NAME_QUERY = "SELECT * FROM Author WHERE name = ?";
    private final String SELECT_AUTHOR_BY_NEWS_ID_QUERY = "SELECT Author.author_id, Author.name FROM Author " +
            "INNER JOIN News_Author on Author.author_id = News_Author.author_id " +
            "WHERE news_id = ?";


    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setInt(2, author.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR_QUERY, new String[]{"author_id"});
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
    protected PreparedStatement prepareStatementForFindById(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME_QUERY);
        preparedStatement.setString(1, name);
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForFindByNewsID(Connection connection, int newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NEWS_ID_QUERY);
        preparedStatement.setInt(1, newsId);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
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
    public Author findByNewsId(int newsId) {
        List<Author> items = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindByNewsID(connection, newsId);
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

    @Override
    public int insert(Author author, int newsId) throws DAOException {
        int authorId = insert(author);
        insertNewsAuthor(newsId, authorId);
        return authorId;
    }

    private int insertNewsAuthor(int newsId, int authorId) {
        Connection connection = null;
        int lastInsertId = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForInsertIntoNewsAuthor(connection, newsId, authorId);
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

    private PreparedStatement prepareStatementForInsertIntoNewsAuthor(Connection connection, int newsId, int authorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_AUTHOR);
        preparedStatement.setInt(1, newsId);
        preparedStatement.setInt(2, authorId);
        return preparedStatement;
    }
}
