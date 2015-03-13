package com.epam.news.model.persistence.oracle;


import com.epam.news.model.persistence.interfaces.NewsDAO;
import com.epam.news.model.domain.News;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OracleNewsDAO implements NewsDAO {

    private DataSource dataSource;
    private final String INSERT_NEWS_QUERY = "INSERT INTO News " +
            "(short_text, full_text, title, creation_date, modification_date)" +
            " VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_NEWS_QUERY = "UPDATE News " +
        "set short_text = ?, full_text = ?, title = ?, creation_date = ?, modification_date = ?" +
        "WHERE news_id = ?";
    private final String DELETE_NEWS_QUERY = "DELETE News WHERE news_id = ?";
    private final String SELECT_ALL_NEWS_QUERY = "SELECT * FROM News";

    @Override
    public News create() {
        return null;
    }

    @Override
    public void insert(News news) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_QUERY);
            preparedStatement.setString(1, news.getShortText());
            preparedStatement.setString(2, news.getFullText());
            preparedStatement.setString(3, news.getTitle());
            preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
            preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
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

    @Override
    public void update(News news) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NEWS_QUERY);
            preparedStatement.setString(1, news.getShortText());
            preparedStatement.setString(2, news.getFullText());
            preparedStatement.setString(3, news.getTitle());
            preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
            preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
            preparedStatement.setInt(6, news.getId());
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

    @Override
    public void delete(News news) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NEWS_QUERY);
            preparedStatement.setInt(1, news.getId());
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

    @Override
    public List<News> listAll() {
        List<News> newsList = new LinkedList<News>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NEWS_QUERY);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                News news = new News();
                news.setId(rs.getInt("news_id"));
                news.setShortText(rs.getString("short_text"));
                news.setFullText(rs.getString("full_text"));
                news.setTitle(rs.getString("title"));
                news.setCreationDate(new java.util.Date(rs.getTimestamp("creation_date").getTime()));
                news.setModificationDate(new java.util.Date(rs.getDate("modification_date").getTime()));
                newsList.add(news);
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
        return newsList;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
