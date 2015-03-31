package com.epam.newsmanagement.model.persistence.oracle;


import com.epam.newsmanagement.model.entity.Comment;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.CommentDAO;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import static com.epam.newsmanagement.model.persistence.oracle.PersistenceConstants.COMMENT_ID;

@Component
public class OracleCommentDAO implements CommentDAO {

    @Autowired
    private GenericDAOUtil<Comment> daoUtil;

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private NewsDAO newsDAO;

    private final String UPDATE_COMMENTS_QUERY = "UPDATE Comments " +
            "set comment_text = ?, creation_date = ?, news_id = ? " +
            "WHERE comment_id = ?";
    private final String INSERT_COMMENTS_QUERY = "INSERT INTO Comments " +
            "(comment_text, creation_date, news_id)" +
            " VALUES (?, ?, ?)";
    private final String DELETE_COMMENTS_QUERY = "DELETE Comments WHERE comment_id = ?";
    private final String SELECT_COMMENTS_BY_ID_QUERY = "SELECT Comments.comment_id, Comments.news_id, " +
            "Comments.comment_text, Comments.creation_date " +
            "FROM Comments WHERE comment_id = ?";
    private final String SELECT_ALL_COMMENTS_QUERY = "SELECT Comments.comment_id, Comments.news_id, " +
            "Comments.comment_text, Comments.creation_date " +
            "FROM Comments";
    private final String SELECT_COMMENTS_BY_NEWS_ID_QUERY = "SELECT Comments.comment_id, Comments.news_id, " +
            "Comments.comment_text, Comments.creation_date " +
            "FROM Comments WHERE news_id = ?";

    @Override
    public PreparedStatement prepareStatementForUpdate(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMENTS_QUERY);
        preparedStatement.setString(1, comment.getCommentText());
        preparedStatement.setTimestamp(2, new Timestamp(comment.getCreationDate().getTime()));
        preparedStatement.setLong(3, comment.getNewsId());
        preparedStatement.setLong(4, comment.getId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForInsert(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENTS_QUERY, new String[]{COMMENT_ID});
        preparedStatement.setString(1, comment.getCommentText());
        preparedStatement.setTimestamp(2, new Timestamp(comment.getCreationDate().getTime()));
        preparedStatement.setLong(3, comment.getNewsId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForDelete(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENTS_QUERY);
        preparedStatement.setLong(1, comment.getId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENTS_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    public PreparedStatement prepareStatementForFindByNewsId(Connection connection, long newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENTS_BY_NEWS_ID_QUERY);
        preparedStatement.setLong(1, newsId);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS_QUERY);
        return preparedStatement;
    }

    @Override
    public long insert(Comment item) throws DAOException {
        return daoUtil.insert(item, this);
    }

    @Override
    public void update(Comment item) throws DAOException {
        daoUtil.update(item, this);
    }

    @Override
    public void delete(Comment item) throws DAOException {
        daoUtil.delete(item, this);
    }

    @Override
    public List<Comment> findAll() {
        return daoUtil.findAll(this);
    }

    @Override
    public Comment findById(long id) {
        return daoUtil.findById(id, this);
    }

    @Override
    public void insert(List<Comment> comments) throws DAOException {
        for (Comment comment : comments) {
            insert(comment);
        }
    }

    @Override
    public List<Comment> findByNewsId(long newsId) {
        List<Comment> items = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = prepareStatementForFindById(connection, newsId);
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
        return items;
    }

    @Override
    public List<Comment> parseResultSet(ResultSet resultSet) throws SQLException {
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

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
