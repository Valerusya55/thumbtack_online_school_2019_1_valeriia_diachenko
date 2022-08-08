package net.thumbtack.school.elections.rest.response.candidate;

import net.thumbtack.school.elections.model.Proposal;

import java.util.List;

public class GetProposalByUserIdDtoResponse {
    private List<Proposal> proposals;

    public GetProposalByUserIdDtoResponse(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
