package com.epam.news.model.persistence.oracle;

import com.epam.news.model.entity.Author;
import com.epam.news.model.entity.NewsAuthor;
import com.epam.news.model.persistence.exception.DAOException;
import com.epam.news.model.persistence.interfaces.NewsAuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class OracleNewsAuthorDAO extends AbstractOracleDAO<NewsAuthor> implements NewsAuthorDAO {

    @Autowired
    private OracleAuthorDAO oracleAuthorDAO;

    private final String INSERT_NEWS_AUTHOR = "INSERT INTO News_Author " +
            "(news_id, author_id)" +
            " VALUES (?, ?)";

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, NewsAuthor newsAuthor) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, NewsAuthor newsAuthor) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS_AUTHOR);
        preparedStatement.setInt(1, newsAuthor.getNewsId());
        preparedStatement.setInt(2, newsAuthor.getAuthorId());
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, NewsAuthor newsAuthor) throws SQLException {
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
    protected List<NewsAuthor> parseResultSet(ResultSet resultSet) throws SQLException {
        List<NewsAuthor> list = new LinkedList<>();
        while (resultSet.next()) {
            NewsAuthor newsAuthor = new NewsAuthor();
            newsAuthor.setId(resultSet.getInt("news_author_id"));
            newsAuthor.setNewsId(resultSet.getInt("news_id"));
            newsAuthor.setAuthorId(resultSet.getInt("author_id"));
            list.add(newsAuthor);
        }
        return list;
    }


    public void insert(String authorName, int newsId) throws DAOException {
        Author author = new Author();
        author.setName(authorName);
        oracleAuthorDAO.insert(author);
        author = oracleAuthorDAO.findByName(authorName);
        NewsAuthor newsAuthor = new NewsAuthor(newsId, author.getId());
        insert(newsAuthor);
    }
}
