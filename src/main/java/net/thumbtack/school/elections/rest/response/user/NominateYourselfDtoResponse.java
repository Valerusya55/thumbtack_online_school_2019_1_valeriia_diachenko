package net.thumbtack.school.elections.rest.response.user;

import net.thumbtack.school.elections.commonresponse.CommonResponse;

public class NominateYourselfDtoResponse {
    private CommonResponse result;

    public NominateYourselfDtoResponse(CommonResponse result) {
        this.result = result;
    }

    public CommonResponse getResult() {
        return result;
    }

    public void setResult(CommonResponse result) {
        this.result = result;
    }
}
