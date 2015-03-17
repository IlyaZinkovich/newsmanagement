package com.epam.news.model.entity;


import java.util.Date;

public class Comment implements Identified {

    private int id;
    private String commentText;
    private Date creationDate;
    private int newsId;

    public Comment() {
    }

    public Comment(String commentText, Date creationDate, int newsId) {
        this.commentText = commentText;
        this.creationDate = creationDate;
        this.newsId = newsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
}
