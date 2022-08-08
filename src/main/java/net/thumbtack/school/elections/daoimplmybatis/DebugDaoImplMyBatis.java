package net.thumbtack.school.elections.daoimplmybatis;

import net.thumbtack.school.elections.dao.DebugDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugDaoImplMyBatis extends DaoImplBaseMyBatis implements DebugDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugDaoImplMyBatis.class);
    @Override
    public void clearDataBase() {
        LOGGER.debug("DAO delete user");
        try (SqlSession sqlSession = getSession()) {
            try {
                getDebugMapper(sqlSession).clearDataBase();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete user", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
