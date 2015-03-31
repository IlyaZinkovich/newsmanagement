package com.epam.newsmanagement.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News {

    private long id;
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

    public News(long id, String shortText, String fullText, String title, Date creationDate, Date modificationDate) {
        this.id = id;
        this.shortText = shortText;
        this.fullText = fullText;
        this.title = title;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;

        News news = (News) o;

        if (id != news.id) return false;
        if (!creationDate.equals(news.creationDate)) return false;
        if (!fullText.equals(news.fullText)) return false;
        if (!modificationDate.equals(news.modificationDate)) return false;
        if (!shortText.equals(news.shortText)) return false;
        if (!title.equals(news.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + shortText.hashCode();
        result = 31 * result + fullText.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + modificationDate.hashCode();
        return result;
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
