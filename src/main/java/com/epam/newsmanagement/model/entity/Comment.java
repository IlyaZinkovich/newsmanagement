package com.epam.newsmanagement.model.entity;


import java.util.Date;

public class Comment {

    private long id;
    private String commentText;
    private Date creationDate;
    private long newsId;

    public Comment() {
    }

    public Comment(String commentText, Date creationDate, long newsId) {
        this.commentText = commentText;
        this.creationDate = creationDate;
        this.newsId = newsId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (newsId != comment.newsId) return false;
        if (!commentText.equals(comment.commentText)) return false;
        if (!creationDate.equals(comment.creationDate)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + commentText.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (int) (newsId ^ (newsId >>> 32));
        return result;
    }
}
