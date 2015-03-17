package com.epam.news.model.entity;

public class NewsAuthor implements Identified {

    private int id;
    private int newsId;
    private int authorId;

    public NewsAuthor() {
    }

    public NewsAuthor(int newsId, int authorId) {
        this.newsId = newsId;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

}
