/*package net.thumbtack.school.elections.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Voter {
    private User user;
    private List<Proposal> proposals;

    public Voter(User user, List<Proposal> proposals) {
        this.user = user;
        this.proposals = proposals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Proposal> getProposals() {
        return Collections.unmodifiableList(proposals);
    }

    public void addProposal(Proposal proposal) {
        if (proposal != null && !proposals.contains(proposal)) {
            proposals.add(proposal);
        }
    }

    public void removeProposal(Proposal proposal){
        proposals.remove(proposal);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return Objects.equals(user, voter.user) &&
                Objects.equals(proposals, voter.proposals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, proposals);
    }

}
*/