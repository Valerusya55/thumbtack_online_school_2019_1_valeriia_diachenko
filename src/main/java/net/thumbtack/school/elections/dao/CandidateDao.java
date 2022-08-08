package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Program;
import net.thumbtack.school.elections.model.Proposal;

import java.util.List;
import java.util.Map;

public interface CandidateDao {
    void removeProposalFromProgram(Candidate candidate, int proposalId);

    void addProposalToProgram(Candidate candidate, int proposalId);

    void cancelNomination(Candidate candidate);

    List<Proposal> getProposalByUserId(Candidate candidate);

    Candidate getCandidateByUserToken(String token) throws ServerException;

    List<Candidate> getAllCandidatesWithProgram();

    Candidate getCandidateByUserId(Integer id) throws ServerException;
}
