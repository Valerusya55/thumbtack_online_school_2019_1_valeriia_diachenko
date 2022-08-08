package net.thumbtack.school.elections.daoimplmybatis;
import net.thumbtack.school.elections.mappers.*;
import net.thumbtack.school.elections.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBaseMyBatis {
    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected CandidateMapper getCandidateMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(CandidateMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

    protected VoterMapper getVoterMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(VoterMapper.class);
    }

    protected VoteMapper getVoteMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(VoteMapper.class);
    }

    protected DebugMapper getDebugMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DebugMapper.class);
    }
}
