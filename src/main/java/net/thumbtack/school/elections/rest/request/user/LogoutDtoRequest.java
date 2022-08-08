package net.thumbtack.school.elections.rest.request.user;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class LogoutDtoRequest {
    @NotBlank
    private String token;

    public LogoutDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogoutDtoRequest that = (LogoutDtoRequest) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
