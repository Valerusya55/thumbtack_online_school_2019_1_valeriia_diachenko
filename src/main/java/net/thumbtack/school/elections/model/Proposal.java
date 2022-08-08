package net.thumbtack.school.elections.model;

import java.util.ArrayList;
import java.util.List;

public class Proposal implements Comparable<Proposal>{
    private User author;
    private String proposal;
    private List<EvaluationProposal> ratings = new ArrayList<>();
    private int id;
    private static final int MAX_RATING = 5;

    public Proposal(User author, String proposal) {
        this.author = author;
        this.proposal = proposal;
        this.ratings = new ArrayList<>();
        ratings.add(new EvaluationProposal(author, MAX_RATING));
    }

    public Proposal() {
    }

    public List<EvaluationProposal> getRatings() {
        return ratings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public double getAverageRating() {
        if(ratings.size() == 0) {
            return 0;
        }
        double rating = 0;
        for (EvaluationProposal evaluationProposals : ratings) {
            rating += evaluationProposals.getRating();
        }
        return (rating / ratings.size());
    }

    @Override
    public int compareTo(Proposal o) {
        return this.getId() - o.getId();
    }
}
