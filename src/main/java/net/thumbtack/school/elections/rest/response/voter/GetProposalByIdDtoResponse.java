package net.thumbtack.school.elections.rest.response.voter;

import net.thumbtack.school.elections.model.Proposal;

public class GetProposalByIdDtoResponse {
    private Proposal proposal;

    public GetProposalByIdDtoResponse(Proposal proposal) {
        this.proposal = proposal;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }
}
