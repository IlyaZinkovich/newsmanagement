package com.epam.newsmanagement.dao.implementation;


import com.epam.newsmanagement.domain.Author;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static com.epam.newsmanagement.dao.implementation.DAOConstants.AUTHOR_ID;

@Component
public class AuthorDAOImpl implements AuthorDAO {

    @Autowired
    private DataSource dataSource;

    private final static String UPDATE_AUTHOR_QUERY = "UPDATE Author " +
            "set author_name = ?, expired = ? " +
            "WHERE author_id = ?";
    private final static String INSERT_AUTHOR_QUERY = "INSERT INTO Author " +
            "(author_name)" +
            " VALUES (?)";
    private final static String UPDATE_EXPIRED_AUTHOR = "UPDATE Author " +
            "set expired = ? " +
            "WHERE author_id = ?";
    private final static String DELETE_AUTHOR_QUERY = "DELETE Author WHERE author_id = ?";
    private final static String SELECT_AUTHOR_BY_ID_QUERY = "SELECT Author.author_id, Author.author_name, Author.expired FROM Author WHERE author_id = ?";
    private final static String SELECT_ALL_AUTHORS_QUERY = "SELECT Author.author_id, Author.author_name, Author.expired FROM Author";
    private final static String SELECT_AUTHOR_BY_NAME_QUERY = "SELECT Author.author_id, Author.author_name, Author.expired FROM Author WHERE author_name = ?";
    private final static String SELECT_AUTHOR_BY_NEWS_ID_QUERY = "SELECT Author.author_id, Author.author_name, Author.expired FROM Author " +
            "INNER JOIN News_Author on Author.author_id = News_Author.author_id " +
            "WHERE news_id = ?";


    private PreparedStatement prepareStatementForUpdate(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);
        preparedStatement.setString(1, author.getName());
        if (author.getExpired() != null)
            preparedStatement.setTimestamp(2, new Timestamp(author.getExpired().getTime()));
        else
            preparedStatement.setTimestamp(2, null);
        preparedStatement.setLong(3, author.getId());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForUpdateExpired(Connection connection, long authorId, Date expiredDate) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXPIRED_AUTHOR);
        preparedStatement.setTimestamp(1, new Timestamp(expiredDate.getTime()));
        preparedStatement.setLong(2, authorId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsert(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR_QUERY, new String[]{AUTHOR_ID});
        preparedStatement.setString(1, author.getName());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForDelete(Connection connection, long authorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_QUERY);
        preparedStatement.setLong(1, authorId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME_QUERY);
        preparedStatement.setString(1, name);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindByNewsId(Connection connection, long newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NEWS_ID_QUERY);
        preparedStatement.setLong(1, newsId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTHORS_QUERY);
        return preparedStatement;
    }

    private List<Author> parseResultSetToList(ResultSet resultSet) throws SQLException {
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

    private Author parseResultSetToObject(ResultSet resultSet) throws SQLException {
        Author author = null;
        while(resultSet.next()) {
            author = new Author();
            author.setId(resultSet.getInt("author_id"));
            author.setName(resultSet.getString("author_name"));
            Timestamp expired = resultSet.getTimestamp("expired");
            if (expired != null) author.setExpired(new Date(expired.getTime()));
            break;
        }
        return author;
    }

    @Override
    public Author findById(long authorId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement preparedStatement = prepareStatementForFindById(connection, authorId);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Author findByNewsId(long newsId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindByNewsId(connection, newsId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Author findByName(String name) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindByName(connection, name);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public long insert(Author author) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        Author foundAuthor = findByName(author.getName());
        if (foundAuthor != null) return foundAuthor.getId();
        long lastInsertId = 0;
        try (PreparedStatement preparedStatement = prepareStatementForInsert(connection, author)) {
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
    public void update(Author author) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForUpdate(connection, author)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void delete(long authorId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForDelete(connection, authorId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Author> findAll() throws DAOException {
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
    public void update(long authorId, Date expirationDate) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForUpdateExpired(connection, authorId, expirationDate)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

}
