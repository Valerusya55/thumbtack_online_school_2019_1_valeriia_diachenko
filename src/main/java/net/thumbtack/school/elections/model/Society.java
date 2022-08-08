package net.thumbtack.school.elections.model;

import java.util.List;
import java.util.Objects;

public class Society {
    private int id = 1;
    private List<Proposal> proposals;

    public Society(int id, List<Proposal> proposals) {
        this.id = id;
        this.proposals = proposals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Society society = (Society) o;
        return id == society.id && Objects.equals(proposals, society.proposals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, proposals);
    }
}
