package com.epam.newsmanagement.dao.implementation;


import com.epam.newsmanagement.domain.Comment;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import static com.epam.newsmanagement.dao.implementation.DAOConstants.COMMENT_ID;

@Component
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private DataSource dataSource;
    
    private final static String UPDATE_COMMENTS_QUERY = "UPDATE Comments " +
            "SET comment_text = ?, creation_date = ?, news_id = ? " +
            "WHERE comment_id = ?";
    private final static String INSERT_COMMENTS_QUERY = "INSERT INTO Comments " +
            "(comment_text, creation_date, news_id)" +
            " VALUES (?, ?, ?)";
    private final static String DELETE_COMMENTS_QUERY = "DELETE Comments WHERE comment_id = ?";
    private final static String SELECT_COMMENTS_BY_ID_QUERY = "SELECT Comments.comment_id, Comments.news_id, " +
            "Comments.comment_text, Comments.creation_date " +
            "FROM Comments WHERE comment_id = ?";
    private final static String SELECT_ALL_COMMENTS_QUERY = "SELECT Comments.comment_id, Comments.news_id, " +
            "Comments.comment_text, Comments.creation_date " +
            "FROM Comments";
    private final static String SELECT_COMMENTS_BY_NEWS_ID_QUERY = "SELECT Comments.comment_id, Comments.news_id, " +
            "Comments.comment_text, Comments.creation_date " +
            "FROM Comments WHERE news_id = ?";

    private PreparedStatement prepareStatementForUpdate(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMENTS_QUERY);
        preparedStatement.setString(1, comment.getCommentText());
        preparedStatement.setTimestamp(2, new Timestamp(comment.getCreationDate().getTime()));
        preparedStatement.setLong(3, comment.getNewsId());
        preparedStatement.setLong(4, comment.getId());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsert(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENTS_QUERY, new String[]{COMMENT_ID});
        preparedStatement.setString(1, comment.getCommentText());
        preparedStatement.setTimestamp(2, new Timestamp(comment.getCreationDate().getTime()));
        preparedStatement.setLong(3, comment.getNewsId());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForDelete(Connection connection, long commentId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENTS_QUERY);
        preparedStatement.setLong(1, commentId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENTS_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindByNewsId(Connection connection, long newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENTS_BY_NEWS_ID_QUERY);
        preparedStatement.setLong(1, newsId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS_QUERY);
        return preparedStatement;
    }

    public long insert(Comment comment) throws DAOException {
        long lastInsertId = 0;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForInsert(connection, comment)) {
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
    public void update(Comment comment) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForUpdate(connection, comment)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void delete(long commentId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForDelete(connection, commentId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Comment> findAll() throws DAOException {
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
    public Comment findById(long commentId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement preparedStatement = prepareStatementForFindById(connection, commentId);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Long> insert(List<Comment> comments) throws DAOException {
        List<Long> idList = new LinkedList<>();
        for (Comment comment : comments) {
            long commentId = insert(comment);
            idList.add(commentId);
        }
        return idList;
    }

    @Override
    public List<Comment> findByNewsId(long newsId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindByNewsId(connection, newsId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    private List<Comment> parseResultSetToList(ResultSet resultSet) throws SQLException {
        List<Comment> list = new LinkedList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setId(resultSet.getLong("comment_id"));
            comment.setCommentText(resultSet.getString("comment_text"));
            comment.setCreationDate(new Date(resultSet.getTimestamp("creation_date").getTime()));
            comment.setNewsId(resultSet.getLong("news_id"));
            list.add(comment);
        }
        return list;
    }

    private Comment parseResultSetToObject(ResultSet resultSet) throws SQLException {
        Comment comment = null;
        while(resultSet.next()) {
            comment = new Comment();
            comment.setId(resultSet.getLong("comment_id"));
            comment.setCommentText(resultSet.getString("comment_text"));
            comment.setCreationDate(new Date(resultSet.getTimestamp("creation_date").getTime()));
            comment.setNewsId(resultSet.getLong("news_id"));
        }
        return comment;
    }

}
