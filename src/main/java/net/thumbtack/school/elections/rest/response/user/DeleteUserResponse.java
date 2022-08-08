package net.thumbtack.school.elections.rest.response.user;

import net.thumbtack.school.elections.commonresponse.CommonResponse;

public class DeleteUserResponse {
    private CommonResponse result;

    public DeleteUserResponse(CommonResponse result) {
        this.result = result;
    }

    public CommonResponse getResult() {
        return result;
    }

    public void setResult(CommonResponse result) {
        this.result = result;
    }
}
