package com.epam.news.model.persistence.oracle;

import com.epam.news.model.entity.NewsTag;
import com.epam.news.model.entity.Tag;
import com.epam.news.model.persistence.exception.DAOException;
import com.epam.news.model.persistence.interfaces.NewsTagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OracleNewsTagDAO extends AbstractOracleDAO<NewsTag> implements NewsTagDAO {

    @Autowired
    private OracleTagDAO oracleTagDAO;

    private final String INSERT_NEWS_TAG_QUERY = "INSERT INTO News_Tag " +
            "(news_id, tag_id)" +
            " VALUES (?, ?)";
    private final String SELECT_NEWS_TAG_BY_ID_QUERY = "SELECT * FROM News_Tag WHERE news_tag_id = ?";
    private final String SELECT_LAST_INSERTED = "SELECT * FROM News_Tag WHERE NEWS_TAG_ID=(SELECT MAX(NEWS_TAG_ID) from News_Tag)";

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, NewsTag newsTag) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, NewsTag newsTag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_TAG_QUERY);
        preparedStatement.setInt(1, newsTag.getNewsId());
        preparedStatement.setInt(2, newsTag.getTagId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, NewsTag newsTag) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement prepareStatementForFindByID(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWS_TAG_BY_ID_QUERY);
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
    protected List<NewsTag> parseResultSet(ResultSet resultSet) throws SQLException {
        List<NewsTag> list = new LinkedList<>();
        while (resultSet.next()) {
            NewsTag newsTag = new NewsTag();
            newsTag.setId(resultSet.getInt("news_tag_id"));
            newsTag.setNewsId(resultSet.getInt("news_id"));
            newsTag.setTagId(resultSet.getInt("tag_id"));
            list.add(newsTag);
        }
        return list;
    }

    @Override
    public void insert(String tagName, int newsId) throws DAOException {
        Tag tag = new Tag();
        tag.setName(tagName);
        oracleTagDAO.insert(tag);
        tag = oracleTagDAO.findByName(tagName);
        NewsTag newsTag = new NewsTag(newsId, tag.getId());
        insert(newsTag);
    }

    @Override
    public void insert(Map<String, Integer> tagNameNewsIdMap) throws DAOException {
        Set<String> tagNames = tagNameNewsIdMap.keySet();
        for (String tagName : tagNames) {
            insert(tagName, tagNameNewsIdMap.get(tagName));
        }
    }

    @Override
    public void insert(List<Tag> tags, int newsId) throws DAOException {
        for (Tag tag : tags) {
            insert(tag.getName(), newsId);
        }
    }

    @Override
    public void insert(List<NewsTag> newsTagList) throws DAOException {
        for (NewsTag newsTag : newsTagList) {
            insert(newsTag);
        }
    }

}
