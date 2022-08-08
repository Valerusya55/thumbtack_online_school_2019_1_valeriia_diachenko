package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import javax.ws.rs.core.Response;
import net.thumbtack.school.elections.daoimpldatabase.CandidateDaoImplDataBase;
import net.thumbtack.school.elections.daoimpldatabase.SessionDaoImplDataBase;
import net.thumbtack.school.elections.daoimpldatabase.UserDaoImplDataBase;
import net.thumbtack.school.elections.commonresponse.CommonResponse;
import net.thumbtack.school.elections.dao.CandidateDao;
import net.thumbtack.school.elections.dao.SessionDao;
import net.thumbtack.school.elections.dao.UserDao;
import net.thumbtack.school.elections.daoimplmybatis.CandidateDaoImplMyBatis;
import net.thumbtack.school.elections.daoimplmybatis.SessionDaoImplMyBatis;
import net.thumbtack.school.elections.daoimplmybatis.UserDaoImplMyBatis;
import net.thumbtack.school.elections.rest.request.candidate.*;
import net.thumbtack.school.elections.rest.response.candidate.*;
import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;
import net.thumbtack.school.elections.utils.ServiceUtils;
import net.thumbtack.school.elections.settings.Mode;

import javax.ws.rs.core.MediaType;

public class CandidateService extends ServiceBase {
    private UserDao userDao;
    private SessionDao sessionDao;
    private CandidateDao candidateDao;
    private static Gson gson;

    public CandidateService(Mode mode) {
        if (mode.equals(Mode.SQL)) {
            this.userDao = new UserDaoImplMyBatis();
            this.candidateDao = new CandidateDaoImplMyBatis();
            this.sessionDao = new SessionDaoImplMyBatis();
        } else {
            this.userDao = new UserDaoImplDataBase();
            this.candidateDao = new CandidateDaoImplDataBase();
            this.sessionDao = new SessionDaoImplDataBase();
        }
        gson = new Gson();
    }

    public Response removeProposalFromProgram(int id, String token) {
        try {
            Candidate candidate = candidateDao.getCandidateByUserToken(token);
            if (candidate == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            candidateDao.removeProposalFromProgram(candidate, id);
            RemoveProposalFromProgramDtoResponse response =
                    new RemoveProposalFromProgramDtoResponse(CommonResponse.REMOVE_PROPOSAL);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response addProposalToProgram(int id, String token) {
        try {
            Candidate candidate = candidateDao.getCandidateByUserToken(token);
            if (candidate == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            candidateDao.addProposalToProgram(candidate, id);
            AddProposalToProgramDtoResponse response =
                    new AddProposalToProgramDtoResponse(CommonResponse.ADD_PROPOSAL_TO_PROGRAM);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response cancelNomination(String token) {
        try {
            Candidate candidate = candidateDao.getCandidateByUserToken(token);
            if (candidate == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            candidateDao.cancelNomination(candidate);
            CancelNominationDtoResponse response = new CancelNominationDtoResponse(CommonResponse.YOU_NO_CANDIDATE);
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response getProposalByUserId(String token) {
        try {
            Candidate candidate = candidateDao.getCandidateByUserToken(token);
            if (candidate == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            GetProposalByUserIdDtoResponse response =
                    new GetProposalByUserIdDtoResponse(candidateDao.getProposalByUserId(candidate));
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response getAllCandidatesWithProgram(String token) {
        try {
            User user = sessionDao.getUserByToken(token);
            if (user == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            GetAllCandidatesWithProgramDtoResponse response =
                    new GetAllCandidatesWithProgramDtoResponse(candidateDao.getAllCandidatesWithProgram());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }
}
