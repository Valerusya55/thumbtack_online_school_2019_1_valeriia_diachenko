package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpldatabase.CandidateDaoImplDataBase;
import net.thumbtack.school.elections.daoimpldatabase.UserDaoImplDataBase;
import net.thumbtack.school.elections.daoimpldatabase.VoteDaoImplDataBase;
import net.thumbtack.school.elections.daoimpldatabase.VoterDaoImplDataBase;
import net.thumbtack.school.elections.commonresponse.CommonResponse;
import net.thumbtack.school.elections.dao.CandidateDao;
import net.thumbtack.school.elections.dao.UserDao;
import net.thumbtack.school.elections.dao.VoteDao;
import net.thumbtack.school.elections.dao.VoterDao;
import net.thumbtack.school.elections.daoimplmybatis.CandidateDaoImplMyBatis;
import net.thumbtack.school.elections.daoimplmybatis.UserDaoImplMyBatis;
import net.thumbtack.school.elections.daoimplmybatis.VoteDaoImplMyBatis;
import net.thumbtack.school.elections.daoimplmybatis.VoterDaoImplMyBatis;
import net.thumbtack.school.elections.rest.request.vote.*;
import net.thumbtack.school.elections.rest.response.vote.*;
import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;
import net.thumbtack.school.elections.utils.ServiceUtils;
import net.thumbtack.school.elections.settings.Mode;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class VoteService extends ServiceBase {
    private VoteDao voteDao;
    private VoterDao voterDao;
    private CandidateDao candidateDao;
    private UserDao userDao;
    private static Gson gson;

    public VoteService(Mode mode) {
        if (mode.equals(Mode.SQL)) {
            this.voteDao = new VoteDaoImplMyBatis();
            this.voterDao = new VoterDaoImplMyBatis();
            this.candidateDao = new CandidateDaoImplMyBatis();
            this.userDao = new UserDaoImplMyBatis();
        } else {
            this.voteDao = new VoteDaoImplDataBase();
            this.voterDao = new VoterDaoImplDataBase();
            this.candidateDao = new CandidateDaoImplDataBase();
            this.userDao = new UserDaoImplDataBase();
        }
        gson = new Gson();
    }

    public Response voteForCandidate(String tokenVoter, int idCandidate) {
        try {
            User user = userDao.getUserByUserToken(tokenVoter);
            if (user == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            Candidate candidate = candidateDao.getCandidateByUserId(idCandidate);
            if (candidate == null) {
                throw new ServerException(ServerErrorCode.WRONG_JSON);
            }
            voteDao.voteForCandidate(user, candidate);
            VoteForCandidateDtoResponse response = new VoteForCandidateDtoResponse(CommonResponse.VOTED_FOR_CANDIDATE);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response voteAgainstAll(String token) {
        try {
            User user = userDao.getUserByUserToken(token);
            if (user == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            voteDao.voteAgainstAll(user);
            VoteAgainstAllDtoResponse response = new VoteAgainstAllDtoResponse(CommonResponse.VOTED_AGAINST_ALL);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response countingOfVotes(String json) {
        try {
            CountingOfVotesDtoResponse response = new CountingOfVotesDtoResponse(voteDao.countingOfVotes());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }
}
