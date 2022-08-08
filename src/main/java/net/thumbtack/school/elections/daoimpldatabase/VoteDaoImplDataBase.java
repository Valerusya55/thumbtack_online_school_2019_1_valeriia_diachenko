package net.thumbtack.school.elections.daoimpldatabase;

import net.thumbtack.school.elections.dao.VoteDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;

public class VoteDaoImplDataBase implements VoteDao {
    @Override
    public void voteForCandidate(User user, Candidate candidate) {
        DataBase.getDataBase().voteForCandidate(user, candidate);
    }

    @Override
    public void voteAgainstAll(User user) {
        DataBase.getDataBase().voteAgainstAll(user);
    }

    @Override
    public Candidate countingOfVotes() throws ServerException {
        return DataBase.getDataBase().countingOfVotes();
    }
}
