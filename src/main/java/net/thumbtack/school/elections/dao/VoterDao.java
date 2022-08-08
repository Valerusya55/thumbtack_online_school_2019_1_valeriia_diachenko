package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.EvaluationProposal;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;

public interface VoterDao {
    void rateProposal(EvaluationProposal evaluationProposals, Proposal proposal);

    void cancelRateProposal(Proposal proposal, User user);

    void addProposal(Proposal proposal) throws ServerException;

    Proposal getProposalById(int idProposal);

}
