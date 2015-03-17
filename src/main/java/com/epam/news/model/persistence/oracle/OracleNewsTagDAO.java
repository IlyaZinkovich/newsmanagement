package com.epam.news.model.persistence.oracle;

import com.epam.news.model.entity.NewsTag;
import com.epam.news.model.entity.Tag;
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

    private final String INSERT_NEWS_AUTHOR_WITH_AUTHOR_ID_QUERY = "INSERT INTO News_Author " +
            "(news_id, author_id)" +
            " VALUES (?, ?)";
    private final String INSERT_NEWS_AUTHOR_WITH_AUTHOR_NAME_QUERY = "INSERT INTO News_Author " +
            "(news_id, author_id)" +
            " VALUES (?, ?)";


    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, NewsTag newsTag) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, NewsTag newsTag) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_AUTHOR_WITH_AUTHOR_ID_QUERY);
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
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException();
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
    public void insertNewsTagWithTagNameNewsId(String tagName, int newsId) {
        Tag tag = new Tag();
        tag.setName(tagName);
        oracleTagDAO.insert(tag);
        tag = oracleTagDAO.findByName(tagName);
        NewsTag newsTag = new NewsTag(newsId, tag.getId());
        insert(newsTag);
    }

    @Override
    public void insertNewsTagWithTagNameNewsId(Map<String, Integer> tagNameNewsIdMap) {
        Set<String> tagNames = tagNameNewsIdMap.keySet();
        for (String tagName : tagNames) {
            insertNewsTagWithTagNameNewsId(tagName, tagNameNewsIdMap.get(tagName));
        }
    }

    @Override
    public void insert(List<NewsTag> newsTagList) {
        for (NewsTag newsTag : newsTagList) {
            insert(newsTag);
        }
    }

}
