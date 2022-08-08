package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpldatabase.UserDaoImplDataBase;
import net.thumbtack.school.elections.daoimpldatabase.VoterDaoImplDataBase;
import net.thumbtack.school.elections.commonresponse.CommonResponse;
import net.thumbtack.school.elections.dao.UserDao;
import net.thumbtack.school.elections.dao.VoterDao;
import net.thumbtack.school.elections.daoimplmybatis.UserDaoImplMyBatis;
import net.thumbtack.school.elections.daoimplmybatis.VoterDaoImplMyBatis;
import net.thumbtack.school.elections.rest.request.voter.*;
import net.thumbtack.school.elections.rest.response.voter.*;
import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.EvaluationProposal;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;
import net.thumbtack.school.elections.utils.ServiceUtils;
import net.thumbtack.school.elections.settings.Mode;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class VoterService extends ServiceBase {
    private VoterDao voterDao;
    private UserDao userDao;
    private static Gson gson;

    public VoterService(Mode mode) {
        if (mode.equals(Mode.SQL)) {
            this.voterDao = new VoterDaoImplMyBatis();
            this.userDao = new UserDaoImplMyBatis();
        } else {
            this.voterDao = new VoterDaoImplDataBase();
            this.userDao = new UserDaoImplDataBase();
        }
        gson = new Gson();
    }

    public Response addProposal(AddProposalDtoRequest addProposalDtoRequest, String token) {
        try {
            User user = userDao.getUserByUserToken(token);
            if (user == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            Proposal proposal = new Proposal(user, addProposalDtoRequest.getProposal());
            voterDao.addProposal(proposal);
            AddProposalDtoResponse response = new AddProposalDtoResponse(CommonResponse.ADD_PROPOSAL, proposal.getId());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response getProposalById(int id, String token) {
        try {
            User user = userDao.getUserByUserToken(token);
            if (user == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            GetProposalByIdDtoResponse response = new GetProposalByIdDtoResponse
                    (voterDao.getProposalById(id));
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response rateProposal(RateProposalDtoRequest rateProposalDtoRequest, String token) {
        try {
            Proposal proposal = voterDao.getProposalById(rateProposalDtoRequest.getIdProposal());
            if (proposal == null) {
                throw new ServerException(ServerErrorCode.WRONG_ID_PROPOSAL);
            }
            User user = userDao.getUserByUserToken(token);
            if (user == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            EvaluationProposal evaluationProposals = new EvaluationProposal(user, rateProposalDtoRequest.getMark());
            proposal.getRatings().add(evaluationProposals);
            voterDao.rateProposal(evaluationProposals, proposal);
            RateProposalDtoResponse response = new RateProposalDtoResponse(CommonResponse.RATE_PROPOSAL, proposal.getAverageRating());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    public Response cancelRateProposal(RateProposalDtoRequest cancelRateProposalDtoRequest, String token) {
        try {
            Proposal proposal = voterDao.getProposalById(cancelRateProposalDtoRequest.getIdProposal());
            if (proposal == null) {
                throw new ServerException(ServerErrorCode.WRONG_ID);
            }
            User user = userDao.getUserByUserToken(token);
            if (user == null) {
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
            removeRating(proposal, user);
            voterDao.cancelRateProposal(proposal, user);
            CancelRateProposalDtoResponse response = new CancelRateProposalDtoResponse
                    (CommonResponse.CANCEL_RATE_PROPOSAL, proposal.getAverageRating(), proposal.getId());
            return Response.ok(response, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex) {
            return ServiceUtils.failureResponse(ex);
        }
    }

    private void removeRating(Proposal proposal, User user) throws ServerException {
        List<EvaluationProposal> ratings = proposal.getRatings();
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i).getUser().getId() == user.getId()) {
                ratings.remove(i);
                return;
            }
        }
        throw new ServerException(ServerErrorCode.USER_NOT_RATE);
    }
}
