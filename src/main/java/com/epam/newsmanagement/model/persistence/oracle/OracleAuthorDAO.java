package com.epam.newsmanagement.model.persistence.oracle;


import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleAuthorDAO extends AbstractOracleDAO<Author> implements AuthorDAO {

    @Autowired
    private NewsDAO newsDAO;

    private final String UPDATE_AUTHOR_QUERY = "UPDATE Author " +
            "set author_name = ? " +
            "WHERE author_id = ?";
    private final String INSERT_AUTHOR_QUERY = "INSERT INTO Author " +
            "(author_name)" +
            " VALUES (?)";
    private final String UPDATE_EXPIRED_AUTHOR = "UPDATE Author " +
            "set expired = ? " +
            "WHERE author_id = ?";
    private final String DELETE_AUTHOR_QUERY = "DELETE Author WHERE author_id = ?";
    private final String SELECT_AUTHOR_BY_ID_QUERY = "SELECT * FROM Author WHERE author_id = ?";
    private final String SELECT_AUTHOR_BY_NAME_QUERY = "SELECT * FROM Author WHERE author_name = ?";
    private final String SELECT_AUTHOR_BY_NEWS_ID_QUERY = "SELECT Author.author_id, Author.author_name FROM Author " +
            "INNER JOIN News_Author on Author.author_id = News_Author.author_id " +
            "WHERE news_id = ?";


    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setInt(2, author.getId());
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForUpdateExpired(Connection connection, int authorId, Date expiredDate) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXPIRED_AUTHOR);
        preparedStatement.setTimestamp(1, new Timestamp(expiredDate.getTime()));
        preparedStatement.setInt(2, authorId);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR_QUERY, new String[]{"AUTHOR_ID"});
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
            author.setName(resultSet.getString("author_name"));
            Timestamp expired = resultSet.getTimestamp("expired");
            if (expired != null) author.setExpired(new Date(expired.getTime()));
            list.add(author);
        }
        return list;
    }

    @Override
    public Author findByNewsId(int newsId) {
        List<Author> items = new LinkedList<>();
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
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
            connection = DataSourceUtils.getConnection(dataSource);
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
    public int insert(Author author) throws DAOException {
        Author foundAuthor = findByName(author.getName());
        if (foundAuthor != null) return foundAuthor.getId();
        return super.insert(author);
    }

    @Override
    public void update(int authorId, Date expirationDate) {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = prepareStatementForUpdateExpired(connection, authorId, expirationDate);
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

}
