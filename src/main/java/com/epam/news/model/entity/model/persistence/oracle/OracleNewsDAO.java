package com.epam.news.model.entity.model.persistence.oracle;


import com.epam.news.model.entity.model.persistence.interfaces.NewsDAO;
import com.epam.news.model.entity.model.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Component
public class OracleNewsDAO extends AbstractOracleDAO<News> implements NewsDAO {

    @Autowired
    private DataSource dataSource;

    private final String INSERT_NEWS_QUERY = "INSERT INTO News " +
            "(short_text, full_text, title, creation_date, modification_date)" +
            " VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_NEWS_QUERY = "UPDATE News " +
        "set short_text = ?, full_text = ?, title = ?, creation_date = ?, modification_date = ?" +
        "WHERE news_id = ?";
    private final String DELETE_NEWS_QUERY = "DELETE News WHERE news_id = ?";
    private final String SELECT_ALL_NEWS_QUERY = "SELECT * FROM News";
    private final String SELECT_NEWS_BY_ID_QUERY = "SELECT * FROM News WHERE news_id = ?";

    @Override
    public News create() {
        return null;
    }


    @Override
    public PreparedStatement prepareStatementForUpdate(Connection connection, News news) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NEWS_QUERY);
        preparedStatement.setString(1, news.getShortText());
        preparedStatement.setString(2, news.getFullText());
        preparedStatement.setString(3, news.getTitle());
        preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
        preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
        preparedStatement.setInt(6, news.getId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForInsert(Connection connection, News news) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_QUERY);
        preparedStatement.setString(1, news.getShortText());
        preparedStatement.setString(2, news.getFullText());
        preparedStatement.setString(3, news.getTitle());
        preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
        preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForDelete(Connection connection, News news) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NEWS_QUERY);
        preparedStatement.setInt(1, news.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindByID(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NEWS_QUERY);
        return preparedStatement;
    }

    @Override
    protected List<News> parseResultSet(ResultSet rs) throws SQLException {
        List<News> list = new LinkedList<>();
        while (rs.next()) {
            News news = new News();
            news.setId(rs.getInt("news_id"));
            news.setShortText(rs.getString("short_text"));
            news.setFullText(rs.getString("full_text"));
            news.setTitle(rs.getString("title"));
            news.setCreationDate(new java.util.Date(rs.getTimestamp("creation_date").getTime()));
            news.setModificationDate(new java.util.Date(rs.getDate("modification_date").getTime()));
            list.add(news);
        }
        return list;
    }

}
