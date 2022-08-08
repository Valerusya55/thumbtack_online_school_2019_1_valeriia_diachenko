package net.thumbtack.school.elections.model;

import net.thumbtack.school.elections.enumformodel.Nomination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String name;
    private String surname;
    private String patronymic;
    private String street;
    private String numberHouse;
    private String numberFlat;
    private String login;
    private String password;
    private int id;
    private Nomination nomination;
    private List<Proposal> userProposals = new ArrayList<>();

    public User() {
    }

    public User(String name, String surname, String patronymic, String street, String numberHouse, String numberFlat, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.street = street;
        this.numberHouse = numberHouse;
        this.numberFlat = numberFlat;
        this.login = login;
        this.password = password;
        this.nomination = Nomination.NOT_NOMINATED;
        this.id = 0;
    }

    public Nomination getNomination() {
        return nomination;
    }

    public void setNomination(Nomination nomination) {
        this.nomination = nomination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                nomination == user.nomination &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(street, user.street) &&
                Objects.equals(numberHouse, user.numberHouse) &&
                Objects.equals(numberFlat, user.numberFlat) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, patronymic, street, numberHouse, numberFlat, login, password, id, nomination);
    }

    public void addProposal(Proposal proposal) {
        userProposals.add(proposal);
    }

    public List<Proposal> getUserProposals() {
        return userProposals;
    }

    public void setUserProposals(List<Proposal> userProposals) {
        this.userProposals = userProposals;
    }

}
