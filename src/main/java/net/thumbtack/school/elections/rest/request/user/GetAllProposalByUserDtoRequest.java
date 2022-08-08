package net.thumbtack.school.elections.rest.request.user;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

public class GetAllProposalByUserDtoRequest {
    @NotEmpty
    private List<Integer> usersId;

    public GetAllProposalByUserDtoRequest(List<Integer> usersId) {
        this.usersId = usersId;
    }

    public List<Integer> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<Integer> usersId) {
        this.usersId = usersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetAllProposalByUserDtoRequest that = (GetAllProposalByUserDtoRequest) o;
        return Objects.equals(usersId, that.usersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId);
    }
}
