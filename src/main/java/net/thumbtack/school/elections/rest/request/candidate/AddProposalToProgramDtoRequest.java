package net.thumbtack.school.elections.rest.request.candidate;

import javax.validation.constraints.Min;
import java.util.Objects;

public class AddProposalToProgramDtoRequest {
    @Min(value = 1)
    private int idProposal;

    public AddProposalToProgramDtoRequest(int idProposal) {
        this.idProposal = idProposal;
    }

    public int getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(int idProposal) {
        this.idProposal = idProposal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddProposalToProgramDtoRequest that = (AddProposalToProgramDtoRequest) o;
        return idProposal == that.idProposal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProposal);
    }
}
