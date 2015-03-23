package com.epam.newsmanagement.model.entity;

public class Role {
    private int userId;

    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
