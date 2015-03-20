package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleTagDAO extends AbstractOracleDAO<Tag> implements TagDAO {

    private final String UPDATE_TAG_QUERY = "UPDATE Tag " +
            "set tag_name = ? " +
            "WHERE tag_id = ?";
    private final String INSERT_TAG_QUERY = "INSERT INTO TAG (TAG_NAME) VALUES (?)";
    private final String DELETE_TAG_QUERY = "DELETE Tag WHERE tag_id = ?";
    private final String SELECT_TAG_BY_ID_QUERY = "SELECT * FROM Tag WHERE tag_id = ?";
    private final String SELECT_TAG_BY_NAME_QUERY = "SELECT * FROM Tag WHERE tag_name = ?";
    private final String INSERT_NEWS_TAG_QUERY = "INSERT INTO News_Tag " +
            "(news_id, tag_id)" +
            " VALUES (?, ?)";
    private final String SELECT_TAGS_BY_NEWS_ID_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag " +
            "INNER JOIN News_Tag on Tag.tag_id = News_Tag.news_id " +
            "WHERE news_id = ?";

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TAG_QUERY);
        preparedStatement.setString(1, tag.getName());
        preparedStatement.setInt(2, tag.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAG_QUERY, new String[]{"tag_id"});
        preparedStatement.setString(1, tag.getName());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TAG_QUERY);
        preparedStatement.setInt(1, tag.getId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindById(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_ID_QUERY);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    protected PreparedStatement prepareStatementForFindByNewsID(Connection connection, int newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_ID_QUERY);
        preparedStatement.setInt(1, newsId);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
    }

    private PreparedStatement prepareStatementForFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_NAME_QUERY);
        preparedStatement.setString(1, name);
        return preparedStatement;
    }

    @Override
    protected List<Tag> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Tag> list = new LinkedList<>();
        while (resultSet.next()) {
            Tag tag = new Tag();
            tag.setId(resultSet.getInt("tag_id"));
            tag.setName(resultSet.getString("tag_name"));
            list.add(tag);
        }
        return list;
    }

    @Override
    public List<Tag> findByNewsId(int newsId) {
        List<Tag> items = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindByNewsID(connection, newsId);
            ResultSet rs = preparedStatement.executeQuery();
            items = parseResultSet(rs);
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
        return items;
    }

    @Override
    public Tag findByName(String name) {
        List<Tag> items = new LinkedList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForFindByName(connection, name);
            ResultSet rs = preparedStatement.executeQuery();
            items = parseResultSet(rs);
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
        if (items.isEmpty()) return null;
        return items.get(0);
    }

    @Override
    public void insert(List<Tag> tags, int newsId) throws DAOException {
        for (Tag tag : tags) {
            int tagId = insert(tag);
            insertNewsTag(newsId, tagId);
        }
    }

    private void insertNewsTag(int newsId, int tagId) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = prepareStatementForInsertIntoNewsTag(connection, newsId, tagId);
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

    private PreparedStatement prepareStatementForInsertIntoNewsTag(Connection connection, int newsId, int tagId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_TAG_QUERY);
        preparedStatement.setInt(1, newsId);
        preparedStatement.setInt(2, tagId);
        return preparedStatement;
    }

}
