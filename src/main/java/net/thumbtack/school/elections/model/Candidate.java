package net.thumbtack.school.elections.model;

import net.thumbtack.school.elections.enumformodel.ConsentToNomination;

import java.util.Objects;

public class Candidate {
    private User user;
    private ConsentToNomination consentToNomination;
    private Program program;

    public Candidate() {
    }

    public Candidate(User user, ConsentToNomination consentToNomination, Program program) {
        this.user = user;
        this.consentToNomination = consentToNomination;
        this.program = program;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConsentToNomination getConsentToNomination() {
        return consentToNomination;
    }

    public void setConsentToNomination(ConsentToNomination consentToNomination) {
        this.consentToNomination = consentToNomination;
    }

    public Program getProgram() {
        return program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(user, candidate.user) && consentToNomination
                == candidate.consentToNomination && Objects.equals(program, candidate.program);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, consentToNomination, program);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "user=" + user +
                ", consentToNomination=" + consentToNomination +
                ", program=" + program +
                '}';
    }
}
