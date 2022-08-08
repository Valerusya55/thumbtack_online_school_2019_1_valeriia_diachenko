package net.thumbtack.school.elections.rest.request.user;

import javax.validation.constraints.Min;
import java.util.Objects;

public class NominateCandidateDtoRequest {
    @Min(value = 1)
    private int id;

    public NominateCandidateDtoRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NominateCandidateDtoRequest that = (NominateCandidateDtoRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
