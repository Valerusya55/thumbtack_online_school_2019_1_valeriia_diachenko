package net.thumbtack.school.elections.rest.request.vote;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class VoteForCandidateDtoRequest {
    @NotBlank
    private String tokenVoter;
    @NotBlank
    private String tokenCandidate;

    public VoteForCandidateDtoRequest(String tokenVoter, String tokenCandidate) {
        this.tokenVoter = tokenVoter;
        this.tokenCandidate = tokenCandidate;
    }

    public String getTokenVoter() {
        return tokenVoter;
    }

    public void setTokenVoter(String tokenVoter) {
        this.tokenVoter = tokenVoter;
    }

    public String getTokenCandidate() {
        return tokenCandidate;
    }

    public void setTokenCandidate(String tokenCandidate) {
        this.tokenCandidate = tokenCandidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteForCandidateDtoRequest that = (VoteForCandidateDtoRequest) o;
        return Objects.equals(tokenVoter, that.tokenVoter) && Objects.equals(tokenCandidate, that.tokenCandidate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenVoter, tokenCandidate);
    }
}
