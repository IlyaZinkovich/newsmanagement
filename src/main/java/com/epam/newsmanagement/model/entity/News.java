package com.epam.newsmanagement.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {

    private int id;
    private String shortText;
    private String fullText;
    private String title;
    private Date creationDate;
    private Date modificationDate;

    public News() {
    }

    public News(String shortText, String fullText, String title, Date creationDate, Date modificationDate) {
        this.shortText = shortText;
        this.fullText = fullText;
        this.title = title;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
