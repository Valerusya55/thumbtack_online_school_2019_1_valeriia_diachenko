package net.thumbtack.school.elections.rest.response.user;

public class RegisterUserDtoResponse {
    private String token;
    private int id;

    public RegisterUserDtoResponse(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
