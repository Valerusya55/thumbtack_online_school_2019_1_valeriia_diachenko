package net.thumbtack.school.elections.rest.request.voter;

import javax.validation.constraints.Min;
import java.util.Objects;

public class RateProposalDtoRequest {
    private Integer mark;
    @Min(value = 1)
    private int idProposal;

    public RateProposalDtoRequest() {
    }

    public RateProposalDtoRequest(Integer mark, int idProposal) {
        this.mark = mark;
        this.idProposal = idProposal;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
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
        RateProposalDtoRequest that = (RateProposalDtoRequest) o;
        return idProposal == that.idProposal && Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, idProposal);
    }
}
