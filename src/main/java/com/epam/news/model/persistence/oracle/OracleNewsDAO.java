package com.epam.news.model.persistence.oracle;


import com.epam.news.model.persistence.interfaces.NewsDAO;
import com.epam.news.model.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Component
public class OracleNewsDAO extends AbstractOracleDAO<News> implements NewsDAO {

    private final String INSERT_NEWS_QUERY = "INSERT INTO News " +
            "(short_text, full_text, title, creation_date, modification_date)" +
            " VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_NEWS_QUERY = "UPDATE News " +
        "set short_text = ?, full_text = ?, title = ?, creation_date = ?, modification_date = ?" +
        "WHERE news_id = ?";
    private final String DELETE_NEWS_QUERY = "DELETE News WHERE news_id = ?";
    private final String SELECT_ALL_NEWS_QUERY = "SELECT * FROM News";
    private final String SELECT_NEWS_BY_ID_QUERY = "SELECT * FROM News WHERE news_id = ?";
    private final String FIND_NEWS_ID_BY_AUTHOR_QUERY = "SELECT News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date \n" +
            "FROM News" +
            "INNER JOIN News_author ON News_author.news_id = News.news_id" +
            "INNER JOIN Author ON Author.author_id = News_author.AUTHOR_ID" +
            "WHERE Author.name = ?";

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, News news) throws SQLException {
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
    protected PreparedStatement prepareStatementForInsert(Connection connection, News news) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_QUERY);
        preparedStatement.setString(1, news.getShortText());
        preparedStatement.setString(2, news.getFullText());
        preparedStatement.setString(3, news.getTitle());
        preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
        preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, News news) throws SQLException {
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
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NEWS_QUERY);
        return preparedStatement;
    }

    @Override
    protected List<News> parseResultSet(ResultSet resultSet) throws SQLException {
        List<News> list = new LinkedList<>();
        while (resultSet.next()) {
            News news = new News();
            news.setId(resultSet.getInt("news_id"));
            news.setShortText(resultSet.getString("short_text"));
            news.setFullText(resultSet.getString("full_text"));
            news.setTitle(resultSet.getString("title"));
            news.setCreationDate(new java.util.Date(resultSet.getTimestamp("creation_date").getTime()));
            news.setModificationDate(new java.util.Date(resultSet.getDate("modification_date").getTime()));
            list.add(news);
        }
        return list;
    }

    protected PreparedStatement preparedStatementForFindNewsIDByAuthorName(Connection connection, String authorName) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_ID_BY_AUTHOR_QUERY);
        preparedStatement.setString(1, authorName);
        return preparedStatement;
    }

    @Override
    public List<News> findByAuthorName(String authorName) {
        List<News> newsList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = preparedStatementForFindNewsIDByAuthorName(connection, authorName);
            ResultSet rs = preparedStatement.executeQuery();
            newsList = parseResultSet(rs);
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
        if (newsList.isEmpty()) return null;
        return newsList;
    }
}
