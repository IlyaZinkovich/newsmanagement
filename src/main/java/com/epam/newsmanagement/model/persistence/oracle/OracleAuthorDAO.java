package com.epam.newsmanagement.model.persistence.oracle;


import com.epam.newsmanagement.model.entity.Author;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static com.epam.newsmanagement.model.persistence.oracle.PersistenceConstants.AUTHOR_ID;

@Component
public class OracleAuthorDAO implements AuthorDAO {

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
    public PreparedStatement prepareStatementForDelete(Connection connection, Author author) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_QUERY);
        preparedStatement.setLong(1, author.getId());
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
    public Author findByNewsId(long newsId) {
        List<Author> items = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = prepareStatementForFindByNewsID(connection, newsId);
            resultSet = preparedStatement.executeQuery();
            items = parseResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (connection != null) connection.close();
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
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = prepareStatementForFindByName(connection, name);
            resultSet = preparedStatement.executeQuery();
            items = parseResultSet(resultSet);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
    public void delete(Author item) throws DAOException {
        daoUtil.delete(item, this);
    }

    @Override
    public List<Author> findAll() {
        return daoUtil.findAll(this);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void update(long authorId, Date expirationDate) {
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
