package com.epam.newsmanagement.model.domain;

import java.util.List;

public class ComplexNews {
    private News news;
    private Author author;
    private List<Tag> tags;
    private List<Comment> comments;

    public ComplexNews() {
    }

    public ComplexNews(News news, Author author, List<Tag> tags, List<Comment> comments) {
        this.news = news;
        this.author = author;
        this.tags = tags;
        this.comments = comments;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
