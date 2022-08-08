package net.thumbtack.school.elections.daoimplmybatis;
import net.thumbtack.school.elections.dao.VoterDao;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.EvaluationProposal;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoterDaoImplMyBatis extends DaoImplBaseMyBatis implements VoterDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoterDaoImplMyBatis.class);

    @Override
    public void rateProposal(EvaluationProposal evaluationProposals, Proposal proposal) {
        LOGGER.debug("DAO insert estimation {} {}", evaluationProposals, proposal);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVoterMapper(sqlSession).rateProposal(evaluationProposals, proposal);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert estimation {} {}",evaluationProposals, proposal, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void cancelRateProposal(Proposal proposal, User user) {
        LOGGER.debug("DAO delete estimation {} {}", proposal, user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVoterMapper(sqlSession).cancelRateProposal(proposal, user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete estimation {} {}",proposal, user, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addProposal(Proposal proposal) throws ServerException {
        LOGGER.debug("DAO insert proposal {}", proposal);
        try (SqlSession sqlSession = getSession()) {
            try {
                getVoterMapper(sqlSession).addProposal(proposal);
                getVoterMapper(sqlSession).addEvaluationProposal(proposal);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert proposal {}",proposal, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Proposal getProposalById(int idProposal) {
        LOGGER.debug("DAO get proposal by Id {}", idProposal);
        try (SqlSession sqlSession = getSession()) {
            return getVoterMapper(sqlSession).getProposalById(idProposal);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get proposal", ex);
            throw ex;
        }
    }
}
