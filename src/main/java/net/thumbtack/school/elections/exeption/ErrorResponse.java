package net.thumbtack.school.elections.exeption;

public class ErrorResponse {
    private ServerErrorCode serverErrorCode;

    public ErrorResponse(ServerException serverException) {
        this.serverErrorCode = serverException.getErrorCode();
    }

    public String getErrorCode() {
        return serverErrorCode.getErrorString();
    }
}
