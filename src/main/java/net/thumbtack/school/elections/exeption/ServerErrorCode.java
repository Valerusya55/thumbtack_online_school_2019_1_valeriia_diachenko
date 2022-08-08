package net.thumbtack.school.elections.exeption;

public enum ServerErrorCode {
    NAME_IS_INCORRECT("Name is incorrect"),
    SURNAME_IS_INCORRECT("Surname is incorrect"),
    LOGIN_IS_INCORRECT("Login is incorrect"),
    STREET_IS_INCORRECT("Street is incorrect"),
    NUMBER_HOUSE_IS_INCORRECT("Number house is incorrect"),
    NUMBER_FLAT_IS_INCORRECT("Number flat is incorrect"),
    PASSWORD_IS_INCORRECT("Password is incorrect"),
    WRONG_JSON("Wrong json"),
    WRONG_ID("Wrong id"),
    WRONG_ID_PROPOSAL("Wrong id proposal"),
    WRONG_TOKEN("Wrong token"),
    USER_ALREADY_EXISTS("This user already exists"),
    THE_DATA_IS_INCORRECT("The data is incorrect"),
    ELECTION_NOT_HELD("the election was not held"),
    USER_NOT_RATE("The user did not rate this proposal"),
    VALIDATION_ERROR("Validation error");

    private String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
