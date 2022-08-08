package net.thumbtack.school.elections.rest.request.user;

import net.thumbtack.school.elections.enumformodel.Nomination;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class RegisterUserDtoRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    private String patronymic;
    @NotBlank
    private String street;
    @NotBlank
    private String numberHouse;
    @NotBlank
    private String numberFlat;
    @NotBlank
    private String login;
    @NotBlank
    private String password;

    private Nomination nomination;

    public RegisterUserDtoRequest(String name, String surname, String patronymic, String street, String numberHouse, String numberFlat, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.street = street;
        this.numberHouse = numberHouse;
        this.numberFlat = numberFlat;
        this.login = login;
        this.password = password;
        this.nomination = Nomination.NOT_NOMINATED;
    }

    public RegisterUserDtoRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumberHouse() {
        return numberHouse;
    }

    public void setNumberHouse(String numberHouse) {
        this.numberHouse = numberHouse;
    }

    public String getNumberFlat() {
        return numberFlat;
    }

    public void setNumberFlat(String numberFlat) {
        this.numberFlat = numberFlat;
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
        if (!(o instanceof RegisterUserDtoRequest)) return false;
        RegisterUserDtoRequest that = (RegisterUserDtoRequest) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getSurname(), that.getSurname()) && Objects.equals(getPatronymic(), that.getPatronymic()) && Objects.equals(getStreet(), that.getStreet()) && Objects.equals(getNumberHouse(), that.getNumberHouse()) && Objects.equals(getNumberFlat(), that.getNumberFlat()) && Objects.equals(getLogin(), that.getLogin()) && Objects.equals(getPassword(), that.getPassword()) && nomination == that.nomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getPatronymic(), getStreet(), getNumberHouse(), getNumberFlat(), getLogin(), getPassword(), nomination);
    }
}
