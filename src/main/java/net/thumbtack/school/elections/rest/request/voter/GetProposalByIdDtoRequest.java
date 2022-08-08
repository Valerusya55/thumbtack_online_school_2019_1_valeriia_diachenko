package net.thumbtack.school.elections.rest.request.voter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class GetProposalByIdDtoRequest {
    @Min(value = 1)
    private int idProposal;

    public GetProposalByIdDtoRequest(int idProposal) {
        this.idProposal = idProposal;
    }

    public int getIdProposal() {
        return idProposal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetProposalByIdDtoRequest that = (GetProposalByIdDtoRequest) o;
        return idProposal == that.idProposal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProposal);
    }
}
