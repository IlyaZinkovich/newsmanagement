package com.epam.newsmanagement.dao.implementation;


import com.epam.newsmanagement.domain.News;
import com.epam.newsmanagement.domain.Tag;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import static com.epam.newsmanagement.dao.implementation.DAOConstants.NEWS_ID;

@Component
public class NewsDAOImpl implements NewsDAO {

    @Autowired
    private DataSource dataSource;

    private final static String INSERT_NEWS_QUERY = "INSERT INTO News " +
            "(short_text, full_text, title, creation_date, modification_date)" +
            " VALUES (?, ?, ?, ?, ?)";
    private final static String INSERT_NEWS_AUTHOR = "INSERT INTO News_Author " +
            "(news_id, author_id)" +
            " VALUES (?, ?)";
    private final static String INSERT_NEWS_TAG_QUERY = "INSERT INTO News_Tag " +
            "(news_id, tag_id)" +
            " VALUES (?, ?)";
    private final static String UPDATE_NEWS_QUERY = "UPDATE News " +
        "SET short_text = ?, full_text = ?, title = ?, creation_date = ?, modification_date = ? " +
        "WHERE news_id = ?";
    private final static String DELETE_NEWS_QUERY = "DELETE News WHERE news_id = ?";
    private final static String SELECT_ALL_NEWS_QUERY = "SELECT News.news_id, News.short_text, News.full_text, News.title, " +
            "News.creation_date, News.modification_date,COUNT(Comments.comment_id) AS NumberOfComments " +
            "FROM News " +
            "LEFT JOIN Comments " +
            "ON Comments.news_id=News.news_id " +
            "GROUP BY News.news_id, News.short_text, News.full_text, News.title, News.creation_date, " +
            "News.modification_date ORDER BY NumberOfComments DESC";
    private final static String SELECT_NEWS_BY_ID_QUERY = "SELECT * FROM News WHERE news_id = ?";
    private final static String FIND_NEWS_BY_AUTHOR_NAME_QUERY = "SELECT News.news_id, News.short_text, News.full_text, " +
            "News.title, News.creation_date, News.modification_date " +
            "FROM News " +
            "INNER JOIN News_author ON News_author.news_id = News.news_id " +
            "INNER JOIN Author ON Author.author_id = News_author.AUTHOR_ID " +
            "WHERE Author.author_name = ?";
    private final static String FIND_NEWS_BY_AUTHOR_ID_QUERY = "SELECT News.news_id, News.short_text, News.full_text, " +
            "News.title, News.creation_date, News.modification_date, Author.author_id " +
            "FROM News " +
            "INNER JOIN News_author ON News_author.news_id = News.news_id " +
            "INNER JOIN Author ON Author.author_id = News_author.AUTHOR_ID " +
            "WHERE Author.AUTHOR_ID=?";
    private final static String FIND_NEWS_BY_TAG_NAME_QUERY = "SELECT News.news_id, News.short_text, News.full_text, " +
            "News.title, News.creation_date, News.modification_date " +
            "FROM News " +
            "INNER JOIN News_Tag ON News_Tag.news_id = News.news_id " +
            "INNER JOIN Tag ON Tag.tag_id = News_Tag.tag_id " +
            "WHERE Tag.tag_name = ?";
    private final static String FIND_NEWS_BY_TAG_ID_QUERY = "SELECT News.news_id, News.short_text, News.full_text, " +
            "News.title, News.creation_date, News.modification_date " +
            "FROM News " +
            "INNER JOIN News_Tag ON News_Tag.news_id = News.news_id " +
            "INNER JOIN Tag ON Tag.tag_id = News_Tag.tag_id " +
            "WHERE Tag.tag_id = ?";

    private final static String FIND_NEWS_BY_TAGS_NAME_QUERY_BEGIN = "SELECT News.news_id, News.short_text, " +
            "News.full_text, News.title, News.creation_date, News.modification_date, " +
            "COUNT(Tag.TAG_ID) as NumerOfTags " +
            "FROM News INNER JOIN News_Tag ON News_Tag.news_id = News.news_id " +
            "INNER JOIN Tag ON Tag.tag_id = News_Tag.tag_id " +
            "WHERE Tag.tag_name in ";
    private final static String FIND_NEWS_BY_TAGS_NAME_QUERY_END = " GROUP BY News.news_id, News.short_text, " +
            "News.full_text, News.title, News.creation_date, News.modification_date " +
            "ORDER BY NumerOfTags DESC";

    private PreparedStatement prepareStatementForUpdate(Connection connection, News news) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NEWS_QUERY);
        preparedStatement.setString(1, news.getShortText());
        preparedStatement.setString(2, news.getFullText());
        preparedStatement.setString(3, news.getTitle());
        preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
        preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
        preparedStatement.setLong(6, news.getId());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsert(Connection connection, News news) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_QUERY, new String[]{NEWS_ID});
        preparedStatement.setString(1, news.getShortText());
        preparedStatement.setString(2, news.getFullText());
        preparedStatement.setString(3, news.getTitle());
        preparedStatement.setTimestamp(4, new Timestamp(news.getCreationDate().getTime()));
        preparedStatement.setDate(5, new Date(news.getModificationDate().getTime()));
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForDelete(Connection connection, long newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NEWS_QUERY);
        preparedStatement.setLong(1, newsId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NEWS_QUERY);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindNewsByAuthorName(Connection connection, String authorName) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_AUTHOR_NAME_QUERY);
        preparedStatement.setString(1, authorName);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindNewsByAuthorId(Connection connection, long authorId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_AUTHOR_ID_QUERY);
        preparedStatement.setLong(1, authorId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindNewsByTagName(Connection connection, String tagName) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_TAG_NAME_QUERY);
        preparedStatement.setString(1, tagName);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindNewsByTags(Connection connection, List<Tag> tags) throws SQLException{
        String tagNames = tags.stream().map(t -> "?").collect(Collectors.joining(", "));
        String query = FIND_NEWS_BY_TAGS_NAME_QUERY_BEGIN +
                "(" + tagNames + ")" + FIND_NEWS_BY_TAGS_NAME_QUERY_END;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < tags.size(); i++) preparedStatement.setString(i + 1, tags.get(i).getName());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindNewsByTagId(Connection connection, long tagId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_NEWS_BY_TAG_ID_QUERY);
        preparedStatement.setLong(1, tagId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsertIntoNewsTag(Connection connection, long newsId, long tagId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_TAG_QUERY);
        preparedStatement.setLong(1, newsId);
        preparedStatement.setLong(2, tagId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsertIntoNewsAuthor(Connection connection, long newsId, long authorId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_AUTHOR);
        preparedStatement.setLong(1, newsId);
        preparedStatement.setLong(2, authorId);
        return preparedStatement;
    }

    private List<News> parseResultSetToList(ResultSet resultSet) throws SQLException {
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

    private News parseResultSetToObject(ResultSet resultSet) throws SQLException {
        News news = null;
        while(resultSet.next()) {
            news = new News();
            news.setId(resultSet.getInt("news_id"));
            news.setShortText(resultSet.getString("short_text"));
            news.setFullText(resultSet.getString("full_text"));
            news.setTitle(resultSet.getString("title"));
            news.setCreationDate(new java.util.Date(resultSet.getTimestamp("creation_date").getTime()));
            news.setModificationDate(new java.util.Date(resultSet.getDate("modification_date").getTime()));
            break;
        }
        return news;
    }

    @Override
    public void insertNewsAuthor(long newsId, long authorId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForInsertIntoNewsAuthor(connection, newsId, authorId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void insertNewsTag(long newsId, long tagId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForInsertIntoNewsTag(connection, newsId, tagId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void insertNewsTags(long newsId, List<Long> tagIdList) throws DAOException {
        for (Long tagId : tagIdList) insertNewsTag(newsId, tagId);
    }

    @Override
    public News findById(long newsId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement preparedStatement = prepareStatementForFindById(connection, newsId);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }


    @Override
    public List<News> findByAuthor(String authorName) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindNewsByAuthorName(connection, authorName);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<News> findByAuthor(long authorId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindNewsByAuthorId(connection, authorId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<News> findByTag(String tagName) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindNewsByTagName(connection, tagName);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<News> findByTag(long tagId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindNewsByTagId(connection, tagId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public long insert(News news) throws DAOException {
        long lastInsertId = 0;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForInsert(connection, news)) {
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
    public void update(News news) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForUpdate(connection, news)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void delete(long newsId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForDelete(connection, newsId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<News> findAll() throws DAOException {
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
    public List<News> findByTags(List<Tag> tags) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindNewsByTags(connection, tags);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToList(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

}