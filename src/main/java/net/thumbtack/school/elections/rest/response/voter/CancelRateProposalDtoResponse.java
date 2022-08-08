package net.thumbtack.school.elections.rest.response.voter;

import net.thumbtack.school.elections.commonresponse.CommonResponse;

public class CancelRateProposalDtoResponse {
    private CommonResponse result;
    private double rating;
    private int idProposal;

    public CancelRateProposalDtoResponse(CommonResponse result, double rating, int idProposal) {
        this.result = result;
        this.rating = rating;
        this.idProposal = idProposal;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(int idProposal) {
        this.idProposal = idProposal;
    }

    public CommonResponse getResult() {
        return result;
    }

    public void setResult(CommonResponse result) {
        this.result = result;
    }
}
