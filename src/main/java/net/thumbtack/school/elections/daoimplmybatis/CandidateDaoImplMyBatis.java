package net.thumbtack.school.elections.daoimplmybatis;
import net.thumbtack.school.elections.dao.CandidateDao;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Proposal;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CandidateDaoImplMyBatis extends DaoImplBaseMyBatis implements CandidateDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateDaoImplMyBatis.class);

    @Override
    public void removeProposalFromProgram(Candidate candidate, int proposalId) {
        LOGGER.debug("DAO update program {}, {}", candidate, proposalId);
        try (SqlSession sqlSession = getSession()) {
            try {
                getCandidateMapper(sqlSession).removeProposalFromProgram(candidate, proposalId);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update program {}, {} ", candidate,proposalId, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

   @Override
   public void addProposalToProgram(Candidate candidate, int proposalId) {
       LOGGER.debug("DAO insert program {},{}", candidate, proposalId);
       try (SqlSession sqlSession = getSession()) {
           try {
               getCandidateMapper(sqlSession).addProposalToProgram(candidate, proposalId);
           } catch (RuntimeException ex) {
               LOGGER.info("Can't insert program {}, {}", candidate, proposalId, ex);
               sqlSession.rollback();
               throw ex;
           }
           sqlSession.commit();
       }
   }

   @Override
   public void cancelNomination(Candidate candidate) {
       LOGGER.debug("DAO update candidate {} ", candidate);
       try (SqlSession sqlSession = getSession()) {
           try {
               getCandidateMapper(sqlSession).cancelNomination(candidate);
           } catch (RuntimeException ex) {
               LOGGER.info("Can't update candidate {} ", candidate, ex);
               sqlSession.rollback();
               throw ex;
           }
           sqlSession.commit();
       }
   }

    @Override
    public List<Proposal> getProposalByUserId(Candidate candidate) {
        LOGGER.debug("DAO get Proposal By UserId {}", candidate);
        try (SqlSession sqlSession = getSession()) {
            return getCandidateMapper(sqlSession).getProposalByUserId(candidate);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert get proposal", ex);
            throw ex;
        }
    }

   @Override
   public Candidate getCandidateByUserToken(String token){
       LOGGER.debug("DAO get candidate by token {}", token);
       try (SqlSession sqlSession = getSession()) {
           return getCandidateMapper(sqlSession).getCandidateByUserToken(token);
       } catch (RuntimeException ex) {
           LOGGER.info("Can't get candidate by token {}", token, ex);
           throw ex;
       }
   }

    @Override
    public List<Candidate> getAllCandidatesWithProgram() {
        LOGGER.debug("DAO get all lazy ");
        try (SqlSession sqlSession = getSession()) {
            return getCandidateMapper(sqlSession).getAllCandidatesWithProgram();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Lazy", ex);
            throw ex;
        }
    }

    @Override
    public Candidate getCandidateByUserId(Integer id) throws ServerException {
        LOGGER.debug("DAO get candidate by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getCandidateMapper(sqlSession).getCandidateByUserId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get candidate by token {}", id, ex);
            throw ex;
        }
    }

}
