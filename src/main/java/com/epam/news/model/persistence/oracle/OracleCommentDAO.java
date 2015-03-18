package com.epam.news.model.persistence.oracle;


import com.epam.news.model.entity.Comment;
import com.epam.news.model.persistence.exception.DAOException;
import com.epam.news.model.persistence.exception.NewsWithThisIdDoesNotExistException;
import com.epam.news.model.persistence.interfaces.CommentDAO;
import com.epam.news.model.persistence.interfaces.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleCommentDAO extends AbstractOracleDAO<Comment> implements CommentDAO {

    @Autowired
    private NewsDAO newsDAO;

    private final String UPDATE_COMMENTS_QUERY = "UPDATE Comments " +
            "set comment_text = ?, creation_date = ?, news_id = ? " +
            "WHERE comment_id = ?";
    private final String INSERT_COMMENTS_QUERY = "INSERT INTO Comments " +
            "(comment_text, creation_date, news_id)" +
            " VALUES (?, ?, ?)";
    private final String DELETE_COMMENTS_QUERY = "DELETE Comments WHERE comment_id = ?";
    private final String SELECT_COMMENTS_BY_ID_QUERY = "SELECT * FROM Comments WHERE comment_id = ?";
    private final String SELECT_LAST_INSERTED = "SELECT * FROM Comments WHERE COMMENT_ID=(SELECT MAX(COMMENT_ID) from Comments)";



    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMENTS_QUERY);
        preparedStatement.setString(1, comment.getCommentText());
        preparedStatement.setTimestamp(2, new Timestamp(comment.getCreationDate().getTime()));
        preparedStatement.setInt(3, comment.getNewsId());
        preparedStatement.setInt(4, comment.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENTS_QUERY);
        preparedStatement.setString(1, comment.getCommentText());
        preparedStatement.setTimestamp(2, new Timestamp(comment.getCreationDate().getTime()));
        preparedStatement.setInt(3, comment.getNewsId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, Comment comment) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENTS_QUERY);
        preparedStatement.setInt(1, comment.getNewsId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindByID(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENTS_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
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
    public void insert(Comment comment) throws DAOException {
        if (newsDAO.findById(comment.getNewsId()) == null) throw new NewsWithThisIdDoesNotExistException();
        super.insert(comment);
    }

    @Override
    protected List<Comment> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Comment> list = new LinkedList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setId(resultSet.getInt("comment_id"));
            comment.setCommentText(resultSet.getString("comment_text"));
            comment.setCreationDate(new Date(resultSet.getTimestamp("creation_date").getTime()));
            comment.setNewsId(resultSet.getInt("news_id"));
            list.add(comment);
        }
        return list;
    }
}
