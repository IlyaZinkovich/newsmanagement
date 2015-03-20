package com.epam.newsmanagement.model.persistence.oracle;

import com.epam.newsmanagement.model.entity.ComplexNews;
import com.epam.newsmanagement.model.entity.News;
import com.epam.newsmanagement.model.persistence.exception.DAOException;
import com.epam.newsmanagement.model.persistence.interfaces.ComplexNewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OracleComplexNewsDAO extends AbstractOracleDAO<ComplexNews> implements ComplexNewsDAO {

    @Autowired
    private OracleNewsDAO oracleNewsDAO;

    @Autowired
    private OracleAuthorDAO oracleAuthorDAO;

    @Autowired
    private OracleTagDAO oracleTagDAO;

    @Autowired
    private OracleCommentDAO oracleCommentDAO;

    @Override
    protected PreparedStatement prepareStatementForUpdate(Connection connection, ComplexNews complexNews) throws SQLException {
        return oracleNewsDAO.prepareStatementForUpdate(connection, complexNews.getNews());
    }

    @Override
    protected PreparedStatement prepareStatementForInsert(Connection connection, ComplexNews complexNews) throws SQLException {
        return oracleNewsDAO.prepareStatementForInsert(connection, complexNews.getNews());
    }

    @Override
    protected PreparedStatement prepareStatementForDelete(Connection connection, ComplexNews complexNews) throws SQLException {
        return oracleNewsDAO.prepareStatementForDelete(connection, complexNews.getNews());
    }

    @Override
    protected PreparedStatement prepareStatementForFindById(Connection connection, int id) throws SQLException {
        return oracleNewsDAO.prepareStatementForFindById(connection, id);
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll(Connection connection) throws SQLException {
        return oracleNewsDAO.prepareStatementForFindAll(connection);
    }

    @Override
    protected List<ComplexNews> parseResultSet(ResultSet resultSet) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int insert(ComplexNews complexNews) throws DAOException {
        int newsId = oracleNewsDAO.insert(complexNews.getNews());
        oracleAuthorDAO.insert(complexNews.getAuthor(), newsId);
        oracleTagDAO.insert(complexNews.getTags(), newsId);
        oracleCommentDAO.insert(complexNews.getComments());
        return newsId;
    }

    @Override
    public void delete(ComplexNews complexNews) {
        oracleNewsDAO.delete(complexNews.getNews());
    }

    @Override
    public ComplexNews findById(int id) {
        ComplexNews complexNews = new ComplexNews();
        News news = oracleNewsDAO.findById(id);
        complexNews.setNews(news);
        complexNews.setAuthor(oracleAuthorDAO.findByNewsId(id));
        complexNews.setTags(oracleTagDAO.findByNewsId(id));
        complexNews.setComments(oracleCommentDAO.findByNewsId(id));
        return complexNews;
    }

    @Override
    public List<ComplexNews> findAll() {
        throw new UnsupportedOperationException();
    }
}
