package com.epam.newsmanagement.model.entity;


import java.util.Date;

public class Author {

    private int id;
    private String name;
    private Date expired;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
