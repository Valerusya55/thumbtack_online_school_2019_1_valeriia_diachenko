package net.thumbtack.school.elections.rest.response.candidate;

import net.thumbtack.school.elections.model.Candidate;

import java.util.List;

public class GetAllCandidatesWithProgramDtoResponse {
    private List<Candidate> candidateProgram;

    public GetAllCandidatesWithProgramDtoResponse(List<Candidate> candidateProgramMap) {
        this.candidateProgram = candidateProgramMap;
    }

    public List<Candidate> getCandidateProgram() {
        return candidateProgram;
    }

    public void setCandidateProgram(List<Candidate>  candidateProgram) {
        this.candidateProgram = candidateProgram;
    }
}
