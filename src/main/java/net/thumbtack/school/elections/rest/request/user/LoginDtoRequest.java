package net.thumbtack.school.elections.rest.request.user;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class LoginDtoRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    public LoginDtoRequest() {
    }

    public LoginDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDtoRequest that = (LoginDtoRequest) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
