package net.thumbtack.school.elections.daoimpldatabase;

import net.thumbtack.school.elections.dao.VoterDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.EvaluationProposal;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;

public class VoterDaoImplDataBase implements VoterDao {
    @Override
    public void rateProposal(EvaluationProposal evaluationProposal, Proposal proposal) {
        DataBase.getDataBase().rateProposal(evaluationProposal, proposal);
    }

    @Override
    public void cancelRateProposal(Proposal proposal, User user) {
        DataBase.getDataBase().cancelRateProposal(proposal, user);
    }

    @Override
    public void addProposal(Proposal proposal) throws ServerException {
        DataBase.getDataBase().addProposal(proposal);
    }

    @Override
    public Proposal getProposalById(int idProposal) {
        return DataBase.getDataBase().getProposalById(idProposal);
    }
}
