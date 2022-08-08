package net.thumbtack.school.elections.rest.response.user;

import net.thumbtack.school.elections.model.Proposal;

import java.util.List;

public class GetAllProposalByUserDtoResponse {
    private List<Proposal> proposals;

    public GetAllProposalByUserDtoResponse(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
