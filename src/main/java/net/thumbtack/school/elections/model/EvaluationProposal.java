package net.thumbtack.school.elections.model;

import java.util.Objects;

public class EvaluationProposal {
    private User user;
    private int rating;

    public EvaluationProposal(User voter, int rating) {
        this.user = voter;
        this.rating = rating;
    }

    public EvaluationProposal() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationProposal)) return false;
        EvaluationProposal that = (EvaluationProposal) o;
        return getRating() == that.getRating() && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getRating());
    }
}
