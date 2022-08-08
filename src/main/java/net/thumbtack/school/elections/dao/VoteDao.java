package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;


public interface VoteDao {
    void voteForCandidate(User user, Candidate candidate);

    void voteAgainstAll(User user);

    public Candidate countingOfVotes() throws ServerException;
}
