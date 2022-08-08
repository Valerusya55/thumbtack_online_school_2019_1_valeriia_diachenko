package net.thumbtack.school.elections.daoimplmybatis;

import net.thumbtack.school.elections.dao.VoteDao;
//import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoteDaoImplMyBatis extends DaoImplBaseMyBatis implements VoteDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteDaoImplMyBatis.class);

    @Override
    public void voteForCandidate(User user, Candidate candidate) {
        LOGGER.debug("DAO insert vote {} {} ", user, candidate);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVoteMapper(sqlSession).voteForCandidate(user, candidate);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert vote {} {}", user, candidate, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void voteAgainstAll(User user) {
        LOGGER.debug("DAO insert vote {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVoteMapper(sqlSession).voteAgainstAll(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert vote {} {}", user, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Candidate countingOfVotes() throws ServerException {
        LOGGER.debug("DAO get candidate winner");
        try (SqlSession sqlSession = getSession()) {
            return getVoteMapper(sqlSession).countingOfVotes();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get candidate winner", ex);
            throw ex;
        }
    }
}
