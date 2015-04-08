package com.epam.newsmanagement.dao.implementation;

import com.epam.newsmanagement.domain.Tag;
import com.epam.newsmanagement.dao.exception.DAOException;
import com.epam.newsmanagement.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import static com.epam.newsmanagement.dao.implementation.DAOConstants.TAG_ID;

@Component
public class TagDAOImpl implements TagDAO {

    @Autowired
    private DataSource dataSource;

    private final static String UPDATE_TAG_QUERY = "UPDATE Tag " +
            "SET tag_name = ? " +
            "WHERE tag_id = ?";
    private final static String INSERT_TAG_QUERY = "INSERT INTO TAG (TAG_NAME) VALUES (?)";
    private final static String DELETE_TAG_QUERY = "DELETE Tag WHERE tag_id = ?";
    private final static String SELECT_ALL_TAGS_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag";
    private final static String SELECT_TAG_BY_ID_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag WHERE tag_id = ?";
    private final static String SELECT_TAG_BY_NAME_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag WHERE tag_name = ?";
    private final static String SELECT_TAGS_BY_NEWS_ID_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag " +
            "INNER JOIN News_Tag on Tag.tag_id = News_Tag.tag_id " +
            "WHERE news_id = ?";

    private PreparedStatement prepareStatementForUpdate(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TAG_QUERY);
        preparedStatement.setString(1, tag.getName());
        preparedStatement.setLong(2, tag.getId());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForInsert(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAG_QUERY, new String[]{TAG_ID});
        preparedStatement.setString(1, tag.getName());
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForDelete(Connection connection, long tagId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TAG_QUERY);
        preparedStatement.setLong(1, tagId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindByNewsId(Connection connection, Long newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAGS_BY_NEWS_ID_QUERY);
        preparedStatement.setLong(1, newsId);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TAGS_QUERY);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_NAME_QUERY);
        preparedStatement.setString(1, name);
        return preparedStatement;
    }

    private List<Tag> parseResultSetToList(ResultSet resultSet) throws SQLException {
        List<Tag> list = new LinkedList<>();
        while (resultSet.next()) {
            Tag tag = new Tag();
            tag.setId(resultSet.getInt("tag_id"));
            tag.setName(resultSet.getString("tag_name"));
            list.add(tag);
        }
        return list;
    }

    private Tag parseResultSetToObject(ResultSet resultSet) throws SQLException {
        Tag tag = null;
        while(resultSet.next()) {
            tag = new Tag();
            tag.setId(resultSet.getInt("tag_id"));
            tag.setName(resultSet.getString("tag_name"));
            break;
        }
        return tag;
    }

    @Override
    public List<Long> insert(List<Tag> tags) throws DAOException {
        List<Long> idList = new LinkedList<>();
        for (Tag t : tags) {
            long tagId = insert(t);
            idList.add(tagId);
        }
        return idList;
    }

    @Override
    public Tag findById(long tagId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindById(connection, tagId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public long insert(Tag tag) throws DAOException {
        Tag foundTag = findByName(tag.getName());
        if (foundTag != null) return foundTag.getId();
        long lastInsertId = 0;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForInsert(connection, tag)) {
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
    public void update(Tag tag) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForUpdate(connection, tag)) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public void delete(long tagId) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForDelete(connection, tagId)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public List<Tag> findAll() throws DAOException {
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
    public List<Tag> findByNewsId(long newsId) throws DAOException {
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

    @Override
    public Tag findByName(String name) throws DAOException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = prepareStatementForFindByName(connection, name);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return parseResultSetToObject(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

}
