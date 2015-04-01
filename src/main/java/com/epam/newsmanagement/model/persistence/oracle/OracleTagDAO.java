package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.entity.Tag;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.DAOHelper;
import com.epam.newsmanagement.model.persistence.interfaces.TagDAO;
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
import static com.epam.newsmanagement.model.persistence.oracle.PersistenceConstants.TAG_ID;

@Component
public class OracleTagDAO implements TagDAO, DAOHelper<Tag> {

    @Autowired
    private GenericDAOUtil<Tag> daoUtil;

    @Autowired
    private DataSource dataSource;

    private final String UPDATE_TAG_QUERY = "UPDATE Tag " +
            "SET tag_name = ? " +
            "WHERE tag_id = ?";
    private final String INSERT_TAG_QUERY = "INSERT INTO TAG (TAG_NAME) VALUES (?)";
    private final String DELETE_TAG_QUERY = "DELETE Tag WHERE tag_id = ?";
    private final String SELECT_ALL_TAGS_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag";
    private final String SELECT_TAG_BY_ID_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag WHERE tag_id = ?";
    private final String SELECT_TAG_BY_NAME_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag WHERE tag_name = ?";
    private final String SELECT_TAGS_BY_NEWS_ID_QUERY = "SELECT Tag.tag_id, Tag.tag_name FROM Tag " +
            "INNER JOIN News_Tag on Tag.tag_id = News_Tag.tag_id " +
            "WHERE news_id = ?";

    @Override
    public PreparedStatement prepareStatementForUpdate(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TAG_QUERY);
        preparedStatement.setString(1, tag.getName());
        preparedStatement.setLong(2, tag.getId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForInsert(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAG_QUERY, new String[]{TAG_ID});
        preparedStatement.setString(1, tag.getName());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForDelete(Connection connection, Tag tag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TAG_QUERY);
        preparedStatement.setLong(1, tag.getId());
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_ID_QUERY);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    public PreparedStatement prepareStatementForFindByNewsId(Connection connection, Long newsId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAGS_BY_NEWS_ID_QUERY);
        preparedStatement.setLong(1, newsId);
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TAGS_QUERY);
        return preparedStatement;
    }

    private PreparedStatement prepareStatementForFindByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAG_BY_NAME_QUERY);
        preparedStatement.setString(1, name);
        return preparedStatement;
    }

    @Override
    public List<Tag> parseResultSet(ResultSet resultSet) throws SQLException {
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
    public List<Long> insert(List<Tag> tags) throws DAOException {
        List<Long> idList = new LinkedList<>();
        for (Tag t : tags) {
            long tagId = insert(t);
            idList.add(tagId);
        }
        return idList;
    }

    @Override
    public Tag findById(long id) throws DAOException {
        return daoUtil.findById(id, this);
    }

    @Override
    public long insert(Tag tag) throws DAOException {
        Tag foundTag = findByName(tag.getName());
        if (foundTag != null) return foundTag.getId();
        return daoUtil.insert(tag, this);
    }

    @Override
    public void update(Tag item) throws DAOException {
        daoUtil.update(item, this);
    }

    @Override
    public void delete(Tag item) throws DAOException {
        daoUtil.delete(item, this);
    }

    @Override
    public List<Tag> findAll() throws DAOException {
        return daoUtil.findAll(this);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<Tag> findByNewsId(long newsId) {
        List<Tag> items = new LinkedList<>();
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = prepareStatementForFindByNewsId(connection, newsId)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    items = parseResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return items;
    }

    @Override
    public Tag findByName(String name) {
        List<Tag> items = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = prepareStatementForFindByName(connection, name);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            items = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        if (items.isEmpty()) return null;
        return items.get(0);
    }

}
