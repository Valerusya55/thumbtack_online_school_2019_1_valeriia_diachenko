package net.thumbtack.school.elections.daoimpldatabase;

import net.thumbtack.school.elections.dao.CandidateDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Proposal;

import java.util.List;

public class CandidateDaoImplDataBase implements CandidateDao {
    @Override
    public void removeProposalFromProgram(Candidate candidate, int proposalId) {
        DataBase.getDataBase().removeProposalFromProgram(candidate, proposalId);
    }

    @Override
    public void addProposalToProgram(Candidate candidate, int proposalId) {
        DataBase.getDataBase().addProposalToProgram(candidate, proposalId);
    }

    @Override
    public void cancelNomination(Candidate candidate) {
        DataBase.getDataBase().cancelNomination(candidate);
    }

    @Override
    public List<Proposal> getProposalByUserId(Candidate candidate) {
        return DataBase.getDataBase().getProposalByUserId(candidate);
    }

    @Override
    public Candidate getCandidateByUserToken(String token) throws ServerException {
        return DataBase.getDataBase().getCandidateByUserToken(token);
    }

    @Override
    public List<Candidate> getAllCandidatesWithProgram() {
        return DataBase.getDataBase().getAllCandidatesWithProgram();
    }

    @Override
    public Candidate getCandidateByUserId(Integer id) throws ServerException {
        return DataBase.getDataBase().getCandidateByUserId(id);
    }
}
