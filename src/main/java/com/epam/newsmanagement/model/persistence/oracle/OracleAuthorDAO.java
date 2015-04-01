package com.epam.newsmanagement.model.persistence.oracle;


import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static com.epam.newsmanagement.model.persistence.oracle.PersistenceConstants.AUTHOR_ID;

@Component
public class OracleAuthorDAO implements AuthorDAO, DAOHelper<Author> {

    @Autowired
    private GenericDAOUtil<Author> daoUtil;

    @Autowired
    private DataSource dataSource;

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
    private final String SELECT_AUTHOR_BY_ID_QUERY = "SELECT Author.author_id, Author.author_name, Author.expired FROM Author WHERE author_id = ?";
    private final String SELECT_AUTHOR_BY_NAME_QUERY = "SELECT Author.author_id, Author.author_name, Author.expired FROM Author WHERE author_name = ?";
    private final String SELECT_AUTHOR_BY_NEWS_ID_QUERY = "SELECT Author.author_id, Author.author_name, Author.expired FROM Author " +
            "INNER JOIN News_Author on Author.author_id = News_Author.author_id " +
            "WHERE news_id = ?";


    @Override
    public PreparedStatement prepareStatementForUpdate(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setLong(2, author.getId());
        return preparedStatement;
    }

    public PreparedStatement prepareStatementForUpdateExpired(Connection connection, long authorId, Date expiredDate) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXPIRED_AUTHOR);
        preparedStatement.setTimestamp(1, new Timestamp(expiredDate.getTime()));
        preparedStatement.setLong(2, authorId);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForInsert(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR_QUERY, new String[]{AUTHOR_ID});
        preparedStatement.setString(1, author.getName());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForDelete(Connection connection, long authorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_QUERY);
        preparedStatement.setLong(1, authorId);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    public PreparedStatement prepareStatementForFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME_QUERY);
        preparedStatement.setString(1, name);
        return preparedStatement;
    }

    public PreparedStatement prepareStatementForFindByNewsID(Connection connection, long newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NEWS_ID_QUERY);
        preparedStatement.setLong(1, newsId);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Author> parseResultSet(ResultSet resultSet) throws SQLException {
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
    public Author findById(long authorId) {
        return null;
    }

    @Override
    public Author findByNewsId(long newsId) throws DAOException {
        List<Author> items;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = prepareStatementForFindByNewsID(connection, newsId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            items = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        if (items.isEmpty()) return null;
        return items.get(0);
    }

    @Override
    public Author findByName(String name) throws DAOException {
        List<Author> items;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = prepareStatementForFindByName(connection, name);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            items = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        if (items.isEmpty()) return null;
        return items.get(0);
    }

    @Override
    public long insert(Author author) throws DAOException {
        Author foundAuthor = findByName(author.getName());
        if (foundAuthor != null) return foundAuthor.getId();
        return daoUtil.insert(author, this);
    }

    @Override
    public void update(Author item) throws DAOException {
        daoUtil.update(item, this);
    }

    @Override
    public void delete(long authorId) throws DAOException {
        daoUtil.delete(authorId, this);
    }

    @Override
    public List<Author> findAll() throws DAOException {
        return daoUtil.findAll(this);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void update(long authorId, Date expirationDate) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = prepareStatementForUpdateExpired(connection, authorId, expirationDate)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

}
