package net.thumbtack.school.elections.model;

import java.util.Objects;

public class Session {
    private int id;
    private String token;
    private User user;

    public Session(String token, User user) {
        this(0,token,user);
    }

    public Session(int id, String token, User user) {
        this.id = id;
        this.token = token;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return getId() == session.getId() && Objects.equals(getToken(), session.getToken()) && Objects.equals(getUser(), session.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getToken(), getUser());
    }
}
