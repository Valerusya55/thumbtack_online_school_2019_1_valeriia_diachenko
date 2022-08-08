package net.thumbtack.school.elections.validator;

import net.thumbtack.school.elections.rest.request.candidate.*;
import net.thumbtack.school.elections.rest.request.user.*;
import net.thumbtack.school.elections.rest.request.vote.VoteAgainstAllDtoRequest;
import net.thumbtack.school.elections.rest.request.vote.VoteForCandidateDtoRequest;
import net.thumbtack.school.elections.rest.request.voter.*;
import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.exeption.ServerException;
/*
public class Validator {
    public static void validate(RegisterUserDtoRequest registerUserDtoRequest) throws ServerException {
        if (registerUserDtoRequest.getName() == null || registerUserDtoRequest.getName().length() <= 1) {
            throw new ServerException(ServerErrorCode.NAME_IS_INCORRECT);
        } else if (registerUserDtoRequest.getSurname() == null || registerUserDtoRequest.getSurname().length() <= 0) {
            throw new ServerException(ServerErrorCode.SURNAME_IS_INCORRECT);
        } else if (registerUserDtoRequest.getLogin() == null || registerUserDtoRequest.getLogin().length() <= 0) {
            throw new ServerException(ServerErrorCode.LOGIN_IS_INCORRECT);
        } else if (registerUserDtoRequest.getStreet() == null || registerUserDtoRequest.getStreet().length() <= 0) {
            throw new ServerException(ServerErrorCode.STREET_IS_INCORRECT);
        } else if (registerUserDtoRequest.getNumberHouse() == null || registerUserDtoRequest.getNumberHouse().length() <= 0) {
            throw new ServerException(ServerErrorCode.NUMBER_HOUSE_IS_INCORRECT);
        } else if (registerUserDtoRequest.getNumberFlat() == null || registerUserDtoRequest.getNumberFlat().length() <= 0) {
            throw new ServerException(ServerErrorCode.NUMBER_FLAT_IS_INCORRECT);
        } else if (registerUserDtoRequest.getPassword() == null || registerUserDtoRequest.getPassword().length() <= 6) {
            throw new ServerException(ServerErrorCode.PASSWORD_IS_INCORRECT);
        }
    }

    public static void validate(LoginDtoRequest loginDtoRequest) throws ServerException {
        if (loginDtoRequest.getLogin() == null || loginDtoRequest.getLogin().length() <= 0) {
            throw new ServerException(ServerErrorCode.LOGIN_IS_INCORRECT);
        } else if (loginDtoRequest.getPassword() == null || loginDtoRequest.getPassword().length() <= 6) {
            throw new ServerException(ServerErrorCode.PASSWORD_IS_INCORRECT);
        }
    }

    public static void validate(LogoutDtoRequest logoutDtoRequest) throws ServerException {
        if (logoutDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(DeleteUserRequest deleteUserRequest) throws ServerException {
        if (deleteUserRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(ConsentNominationDtoRequest consentNominationDtoRequest) throws ServerException {
        if (consentNominationDtoRequest.getIdUser() < 1) {
            throw new ServerException(ServerErrorCode.WRONG_ID);
        }
    }

    public static void validate(NominateYourselfDtoRequest nominateYourselfDtoRequest) throws ServerException {
        if (nominateYourselfDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(RemoveProposalFromProgramDtoRequest removeProposalFromProgramDtoRequest) throws ServerException {
        if (removeProposalFromProgramDtoRequest.getIdProposal() <= 0) {
            throw new ServerException(ServerErrorCode.WRONG_ID);
        } else if (removeProposalFromProgramDtoRequest.getTokenCandidate() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(NominateCandidateDtoRequest nominateCandidateDtoRequest) throws ServerException {
        if (nominateCandidateDtoRequest.getId() < 1) {
            throw new ServerException(ServerErrorCode.WRONG_ID);
        } else if (nominateCandidateDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(AddProposalToProgramDtoRequest addProposalToProgramDtoRequest) throws ServerException {
        if (addProposalToProgramDtoRequest.getIdProposal() < 1) {
            throw new ServerException(ServerErrorCode.WRONG_ID);
        } else if (addProposalToProgramDtoRequest.getTokenCandidate() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(CancelNominationDtoRequest cancelNominationDtoRequest) throws ServerException {
        if (cancelNominationDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(GetProposalByUserIdDtoRequest getProposalByUserIdDtoRequest) throws ServerException {
        if (getProposalByUserIdDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(AddProposalDtoRequest addProposalDtoRequest) throws ServerException {
        if (addProposalDtoRequest.getProposal() == null) {
            throw new ServerException(ServerErrorCode.THE_DATA_IS_INCORRECT);
        } else if (addProposalDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(GetProposalByIdDtoRequest getProposalByIdDtoRequest) throws ServerException {
        if (getProposalByIdDtoRequest.getIdProposal() < 1) {
            throw new ServerException(ServerErrorCode.WRONG_ID);
        } else if (getProposalByIdDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(GetAllProposalByUserDtoRequest getAllProposalByUserDtoRequest) throws ServerException {
        if (getAllProposalByUserDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        } else if (getAllProposalByUserDtoRequest.getUsersId() == null) {
            throw new ServerException(ServerErrorCode.THE_DATA_IS_INCORRECT);
        }
    }

    public static void validate(GetAllCandidatesWithProgramDtoRequest getAllCandidatesWithProgramDtoRequest) throws ServerException {
        if (getAllCandidatesWithProgramDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(GetAllSortedProposalsDtoRequest getAllSortedProposalsDtoRequest) throws ServerException {
        if (getAllSortedProposalsDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(RateProposalDtoRequest rateProposalDtoRequest) throws ServerException {
        if (rateProposalDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        } else if (rateProposalDtoRequest.getIdProposal() < 1) {
            throw new ServerException(ServerErrorCode.WRONG_ID);
        } else if (rateProposalDtoRequest.getMark() < 1) {
            throw new ServerException(ServerErrorCode.THE_DATA_IS_INCORRECT);
        }
    }

    public static void validate(CancelRateProposalDtoRequest cancelRateProposalDtoRequest) throws ServerException {
        if (cancelRateProposalDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        } else if (cancelRateProposalDtoRequest.getIdProposal() < 1) {
            throw new ServerException(ServerErrorCode.WRONG_ID);
        }
    }

    public static void validate(VoteForCandidateDtoRequest voteForCandidateDtoRequest) throws ServerException {
        if (voteForCandidateDtoRequest.getTokenCandidate() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        } else if (voteForCandidateDtoRequest.getTokenVoter() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }

    public static void validate(VoteAgainstAllDtoRequest voteAgainstAllDtoRequest) throws ServerException {
        if (voteAgainstAllDtoRequest.getToken() == null) {
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
    }
}
*/