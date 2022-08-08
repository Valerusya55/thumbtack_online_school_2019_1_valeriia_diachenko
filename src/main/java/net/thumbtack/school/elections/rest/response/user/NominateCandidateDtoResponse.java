package net.thumbtack.school.elections.rest.response.user;

import net.thumbtack.school.elections.commonresponse.CommonResponse;

public class NominateCandidateDtoResponse {
    private CommonResponse result;
    private String tokenNominated;

    public NominateCandidateDtoResponse() {
    }

    public NominateCandidateDtoResponse(CommonResponse result, String token) {
        this.result = result;
        this.tokenNominated = token;
    }

    public String getTokenNominated() {
        return tokenNominated;
    }

    public void setTokenNominated(String tokenNominated) {
        this.tokenNominated = tokenNominated;
    }

    public CommonResponse getResult() {
        return result;
    }

    public void setResult(CommonResponse result) {
        this.result = result;
    }
}
