package com.epam.newsmanagement.model.persistence.oracle;


import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.interfaces.NewsDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class OracleNewsDAO extends AbstractOracleDAO<News> implements NewsDAO {

    private final String INSERT_NEWS_QUERY = "INSERT INTO News " +
            "(short_text, full_text, title, creation_date, modification_date)" +
            " VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_NEWS_QUERY = "UPDATE News " +
        "set short_text = ?, full_text = ?, title = ?, creation_date = ?, modification_date = ?" +
        "WHERE news_id = ?";
    private final String DELETE_NEWS_QUERY = "DELETE News WHERE news_id = ?";
    private final String SELECT_ALL_NEWS_QUERY = "SELECT News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date,COUNT(Comments.comment_id) AS NumberOfComments FROM News " +
            "LEFT JOIN Comments " +
            "ON Comments.news_id=News.news_id " +
            "GROUP BY News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date ORDER BY NumberOfComments DESC";
    private final String SELECT_NEWS_BY_ID_QUERY = "SELECT * FROM News WHERE news_id = ?";
    private final String FIND_NEWS_BY_AUTHOR_NAME_QUERY = "SELECT News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date " +
            "FROM News" +
            "INNER JOIN News_author ON News_author.news_id = News.news_id" +
            "INNER JOIN Author ON Author.author_id = News_author.AUTHOR_ID" +
            "WHERE Author.name = ?";
    private final String FIND_NEWS_BY_AUTHOR_ID_QUERY = "SELECT News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date " +
            "FROM News" +
            "INNER JOIN News_author ON News_author.news_id = News.news_id" +
            "INNER JOIN Author ON Author.author_id = News_author.AUTHOR_ID" +
            "WHERE Author.id = ?";
    private final String FIND_NEWS_BY_TAG_NAME_QUERY = "SELECT News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date " +
            "FROM News" +
            "INNER JOIN News_Tag ON News_Tag.news_id = News.news_id" +
            "INNER JOIN Tag ON Tag.tag_id = News_Tag.tag_id" +
            "WHERE Tag.tag_name = ?";
    private final String FIND_NEWS_BY_TAG_ID_QUERY = "SELECT News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date " +
            "FROM News" +
            "INNER JOIN News_Tag ON News_Tag.news_id = News.news_id" +
            "INNER JOIN Tag ON Tag.tag_id = News_Tag.tag_id" +
            "WHERE Tag.tag_id = ?";

    private final String FIND_NEWS_BY_TAGS_NAME_QUERY_BEGIN = "SELECT News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date, " +
            "COUNT(Tag.TAG_ID) as NumerOfTags " +
            "FROM News INNER JOIN News_Tag ON News_Tag.news_id = News.news_id " +
            "INNER JOIN Tag ON Tag.tag_id = News_Tag.tag_id " +
            "WHERE Tag.tag_name in ";
    private final String FIND_NEWS_BY_TAGS_NAME_QUERY_END = " GROUP BY News.news_id, News.short_text, News.full_text, News.title, News.creation_date, News.modification_date ORDER BY NumerOfTags DESC";

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
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_QUERY, new String[]{"news_id"});
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
    protected PreparedStatement prepareStatementForFindById(Connection connection, int id) throws SQLException {
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

    protected PreparedStatement prepareStatementForFindNewsByAuthorName(Connection connection, String authorName) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_AUTHOR_NAME_QUERY);
        preparedStatement.setString(1, authorName);
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForFindNewsIdByAuthorId(Connection connection, int authorId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_AUTHOR_ID_QUERY);
        preparedStatement.setInt(1, authorId);
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForFindNewsByTagName(Connection connection, String tagName) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_TAG_NAME_QUERY);
        preparedStatement.setString(1, tagName);
        return preparedStatement;
    }

    protected PreparedStatement preparedStatementForFindNewsByTags(Connection connection, List<Tag> tags) throws SQLException{
        String tagNames = tags.stream().map(t -> "?").collect(Collectors.joining(", "));
        String query = FIND_NEWS_BY_TAGS_NAME_QUERY_BEGIN +
                "(" + tagNames + ")" + FIND_NEWS_BY_TAGS_NAME_QUERY_END;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < tags.size(); i++) preparedStatement.setString(i + 1, tags.get(i).getName());
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForFindNewsByTagId(Connection connection, int tagId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_TAG_ID_QUERY);
        preparedStatement.setInt(1, tagId);
        return preparedStatement;
    }


    @Override
    public List<News> findByAuthor(String authorName) {
        List<News> newsList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindNewsByAuthorName(connection, authorName);
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

    @Override
    public List<News> findByAuthor(int authorId) {
        List<News> newsList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindNewsIdByAuthorId(connection, authorId);
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

    @Override
    public List<News> findByTag(String tagName) {
        List<News> newsList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindNewsByTagName(connection, tagName);
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

    @Override
    public List<News> findByTag(int tagId) {
        List<News> newsList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindNewsByTagId(connection, tagId);
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

    @Override
    public List<News> findByTags(List<Tag> tags) {
        List<News> newsList = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = preparedStatementForFindNewsByTags(connection, tags);
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
        return newsList;
    }
}
