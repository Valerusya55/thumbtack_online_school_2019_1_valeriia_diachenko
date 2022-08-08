package net.thumbtack.school.elections.rest.response.voter;

import net.thumbtack.school.elections.commonresponse.CommonResponse;

public class AddProposalDtoResponse {
    private CommonResponse result;
    private int idProposal;

    public AddProposalDtoResponse(CommonResponse result, int idProposal) {
        this.result = result;
        this.idProposal = idProposal;
    }

    public CommonResponse getResult() {
        return result;
    }

    public void setResult(CommonResponse result) {
        this.result = result;
    }

    public int getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(int idProposal) {
        this.idProposal = idProposal;
    }
}
