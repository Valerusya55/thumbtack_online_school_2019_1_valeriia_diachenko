package net.thumbtack.school.elections.rest.request.user;

import javax.validation.constraints.Min;
import java.util.Objects;

public class ConsentNominationDtoRequest {
    @Min(value = 1)
    private int idUser;

    public ConsentNominationDtoRequest(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsentNominationDtoRequest that = (ConsentNominationDtoRequest) o;
        return idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }
}
