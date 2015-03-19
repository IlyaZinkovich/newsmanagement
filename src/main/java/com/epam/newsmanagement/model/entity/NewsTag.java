package com.epam.newsmanagement.model.entity;


public class NewsTag {

    private int id;
    private int newsId;
    private int tagId;

    public NewsTag(int newsId, int tagId) {
        this.newsId = newsId;
        this.tagId = tagId;
    }

    public NewsTag() {
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

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
