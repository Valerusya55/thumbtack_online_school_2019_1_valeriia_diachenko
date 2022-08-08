package net.thumbtack.school.elections.rest.response.user;

import net.thumbtack.school.elections.model.Proposal;

import java.util.List;

public class GetAllSortedProposalsDtoResponse {
    private List<Proposal> proposalDouble;

    public GetAllSortedProposalsDtoResponse(List<Proposal> proposalDoubleMap) {
        this.proposalDouble = proposalDoubleMap;
    }

    public List<Proposal> getProposalDouble() {
        return proposalDouble;
    }

    public void setProposalDouble(List<Proposal> proposalDouble) {
        this.proposalDouble = proposalDouble;
    }
}
