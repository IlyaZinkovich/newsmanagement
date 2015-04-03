package com.epam.newsmanagement.model.domain;


import java.util.Date;

public class Author {

    private long id;
    private String name;
    private Date expired;

    public Author() {
    }

    public Author(long id, String name, Date expired) {
        this.id = id;
        this.name = name;
        this.expired = expired;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (id != author.id) return false;
        if (expired != null ? !expired.equals(author.expired) : author.expired != null) return false;
        if (!name.equals(author.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expired=" + expired +
                '}';
    }
}
