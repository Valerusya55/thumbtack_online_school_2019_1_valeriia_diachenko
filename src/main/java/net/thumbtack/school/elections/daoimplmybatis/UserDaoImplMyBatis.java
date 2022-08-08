package net.thumbtack.school.elections.daoimplmybatis;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.dao.UserDao;
//import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImplMyBatis extends DaoImplBaseMyBatis implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImplMyBatis.class);

    @Override
    public void registerUser(User user) throws ServerException {
        LOGGER.debug("DAO insert user {} ", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).registerUser(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert user {} ", user, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void logoutUser(String token) {
        LOGGER.debug("DAO delete user {} ", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).logoutUser(token);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete user {} ", token, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void consentNomination(User user) {
        LOGGER.debug("DAO update candidate {} ", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).consentNomination(user);
                getUserMapper(sqlSession).addCandidate(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update candidate {} ", user, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void nominateCandidate(User nominated, User suggest) {
        LOGGER.debug("DAO update user {} ", nominated);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).nominateCandidate(nominated, suggest);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update user {} ", nominated, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void nominateYourself(User nominated) {
        LOGGER.debug("DAO update user {} ", nominated);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).addCandidate(nominated);
                getUserMapper(sqlSession).nominateYourself(nominated);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update user {} ", nominated, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<Proposal> getAllProposalByUser(List<Integer> userList) {
        LOGGER.debug("DAO get all from list {}", userList);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getAllProposalByUser(userList);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't insert get All from list", ex);
            throw ex;
        }
    }

    @Override
    public User getUserById(int idUser) throws ServerException {
        LOGGER.debug("DAO get user by id {}", idUser);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getUserById(idUser);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get user by id {}", idUser, ex);
            throw ex;
        }
    }

    @Override
    public User getUserByLogin(String login, String password) throws ServerException {
        LOGGER.debug("DAO get user by login and password {} {}", login, password);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getUserByLogin(login, password);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get user by login and password {} {}", login, password, ex);
            throw ex;
        }
    }

    @Override
    public User getUserByUserToken(String token) {
        LOGGER.debug("DAO get user by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getUserByUserToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get user by token {}", token, ex);
            throw ex;
        }
    }

    @Override
    public List<Proposal> getAllSortedProposals() {
        LOGGER.debug("DAO get all");
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getAllSortedProposals();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get all", ex);
            throw ex;
        }
    }
}
