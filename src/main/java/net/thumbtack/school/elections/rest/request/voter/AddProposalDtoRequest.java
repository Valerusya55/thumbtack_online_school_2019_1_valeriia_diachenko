package net.thumbtack.school.elections.rest.request.voter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AddProposalDtoRequest {
    @NotBlank
    private String proposal;

    public AddProposalDtoRequest() {
    }

    public AddProposalDtoRequest(String proposal) {
        this.proposal = proposal;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddProposalDtoRequest that = (AddProposalDtoRequest) o;
        return Objects.equals(proposal, that.proposal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proposal);
    }
}
