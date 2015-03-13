package com.epam.news.model.persistence.oracle;


import com.epam.news.model.persistence.interfaces.NewsDAO;
import com.epam.news.model.domain.News;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class OracleNewsDAO implements NewsDAO {

    private JdbcTemplate jdbcTemplate;
    private final String INSERT_NEWS_QUERY = "INSERT INTO News " +
            "(news_id, short_text, full_text, title, creation_date, modification_date)" +
            " VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_NEWS_QUERY = "UPDATE News " +
        "set short_text = ?, full_text = ?, title = ?, creation_date = ?, modification_date = ?" +
        "WHERE news_id = ?";

    @Override
    public News create() {
        return null;
    }

    @Override
    public void insert(News news) {
        jdbcTemplate.update(INSERT_NEWS_QUERY, news.getId(), news.getShortText(), news.getFullText(),news.getTitle(),
                new Timestamp(news.getCreationDate().getTime()), new Date(news.getModificationDate().getTime()));
    }

    @Override
    public void update(News news) {
        jdbcTemplate.update(UPDATE_NEWS_QUERY, news.getShortText(), news.getFullText(),news.getTitle(),
                new Timestamp(news.getCreationDate().getTime()), new Date(news.getModificationDate().getTime()),
                news.getId());
    }

    @Override
    public void delete(News news) {

    }

    @Override
    public List<News> listAll() {
        return null;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    private DataSource dataSource;
//    private final String CREATE_NEWS_QUERY = "";
//    private final String INSERT_NEWS_QUERY = "INSERT INTO News " +
//            "(news_id, short_text, full_text, title, creation_date, modification_date)" +
//            " VALUES (?, ?, ?, ?, ?, ?)";
//    private final String UPDATE_NEWS_QUERY = "UPDATE News " +
//            "set short_text = ?, full_text = ?, title = ?, creation_date = ?, modification_date = ?" +
//            "WHERE news_id = ?";
//
//    @Override
//    public News create() {
//        return null;
//    }
//
//    @Override
//    public void insert(News news) {
//        Connection connection = null;
//        try {
//            connection = dataSource.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_QUERY);
//            preparedStatement.setInt(1, news.getId());
//            preparedStatement.setString(2, news.getShortText());
//            preparedStatement.setString(3, news.getFullText());
//            preparedStatement.setString(4, news.getTitle());
//            preparedStatement.setTimestamp(5, new Timestamp(news.getCreationDate().getTime()));
//            preparedStatement.setDate(6, new Date(news.getModificationDate().getTime()));
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void update(News news) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = dataSource.getConnection();
//            preparedStatement = connection.prepareStatement(UPDATE_NEWS_QUERY);
//            preparedStatement.setString(1, news.getShortText());
//            preparedStatement.setString(2, news.getFullText());
//            preparedStatement.setString(3, news.getTitle());
//            preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
//            preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
//            preparedStatement.setInt(6, news.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (connection != null) connection.close();
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    @Override
//    public void delete(News news) {
//
//    }
//
//    @Override
//    public List<News> listAll() {
//        return null;
//    }
}
