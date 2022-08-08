package net.thumbtack.school.elections.daoimplmybatis;

import net.thumbtack.school.elections.dao.SessionDao;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Session;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionDaoImplMyBatis extends DaoImplBaseMyBatis implements SessionDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDaoImplMyBatis.class);
    @Override
    public void addToken(Session session) {
        LOGGER.debug("DAO insert session {}", session);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).addToken(session);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert session {}, {}", session, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public User getUserByToken(String token) throws ServerException {
        LOGGER.debug("DAO get user by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getSessionMapper(sqlSession).getUserByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get user", ex);
            throw ex;
        }
    }
}
