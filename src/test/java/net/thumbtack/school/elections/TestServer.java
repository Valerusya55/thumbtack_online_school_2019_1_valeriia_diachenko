package net.thumbtack.school.elections;

import com.google.gson.Gson;
import net.thumbtack.school.elections.client.MyClient;
import net.thumbtack.school.elections.commonresponse.CommonResponse;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exeption.ErrorResponse;
import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.rest.request.candidate.AddProposalToProgramDtoRequest;
import net.thumbtack.school.elections.rest.request.candidate.CancelNominationDtoRequest;
import net.thumbtack.school.elections.rest.request.candidate.RemoveProposalFromProgramDtoRequest;
import net.thumbtack.school.elections.rest.request.user.*;
import net.thumbtack.school.elections.rest.request.vote.VoteAgainstAllDtoRequest;
import net.thumbtack.school.elections.rest.request.voter.AddProposalDtoRequest;
import net.thumbtack.school.elections.rest.request.voter.CancelRateProposalDtoRequest;
import net.thumbtack.school.elections.rest.request.voter.RateProposalDtoRequest;
import net.thumbtack.school.elections.rest.response.candidate.AddProposalToProgramDtoResponse;
import net.thumbtack.school.elections.rest.response.candidate.CancelNominationDtoResponse;
import net.thumbtack.school.elections.rest.response.candidate.RemoveProposalFromProgramDtoResponse;
import net.thumbtack.school.elections.rest.response.user.*;
import net.thumbtack.school.elections.rest.response.vote.VoteAgainstAllDtoResponse;
import net.thumbtack.school.elections.rest.response.voter.AddProposalDtoResponse;
import net.thumbtack.school.elections.rest.response.voter.CancelRateProposalDtoResponse;
import net.thumbtack.school.elections.rest.response.voter.RateProposalDtoResponse;
import net.thumbtack.school.elections.server.Server;
import net.thumbtack.school.elections.settings.Settings;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestServer {
    private Server server = new Server();
    private Gson gson = new Gson();
    protected static MyClient client = new MyClient();
    private static String baseURL;

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServer.class);
    /*@BeforeEach
    public void clearDataBase() throws ClassNotFoundException, IOException {
        server.startServer(null);
        server.clearDataBase();
    }*/

    private static void setBaseUrl() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            LOGGER.debug("Can't determine my own host name", e);
        }
        baseURL = "http://" + hostName + ":" + Settings.getRestHTTPPort() + "/api";
    }

    @BeforeAll
    public static void startServer() {
        setBaseUrl();
        Server.createServer();
    }

    @AfterAll
    public static void stopServer() {
        Server.stopServer();
    }

    @BeforeEach
    public void clearDataBase() {
        DataBase.getDataBase().clearDataBase();
    }

    public static String getBaseURL() {
        return baseURL;
    }

    protected void checkFailureResponse(Object response, ServerErrorCode expectedStatus) {
        assertTrue(response instanceof ErrorResponse);
        ErrorResponse failureResponseObject = (ErrorResponse) response;
        assertEquals(expectedStatus.getErrorString(), failureResponseObject.getErrorCode());
    }

    @Test
    public void testRegisterUser() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        //String register = server.registerUser(gson.toJson(registerUserDtoRequest));
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        Assertions.assertNotNull(registerUserDtoResponse.getToken());
    }

    @Test
    public void testRegisterUserNullName() throws IOException, ClassCastException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest(null, "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        if (response instanceof RegisterUserDtoResponse) {
            RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
            assertNotNull(registerUserDtoResponse.getToken());
        } else {
            checkFailureResponse(response, ServerErrorCode.VALIDATION_ERROR);
        }
    }

    @Test
    public void testRegisterUserNullSurname() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "",
                "Olegovna", "Mira", "5", "5", "Masha", "1234");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        if (response instanceof RegisterUserDtoResponse) {
            RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
            assertNotNull(registerUserDtoResponse.getToken());
        } else {
            checkFailureResponse(response, ServerErrorCode.VALIDATION_ERROR);
        }
    }

    @Test
    public void testRegisterUserNullStreet() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Ivanovna", "", "5", "5", "Masha", "1234");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        if (response instanceof RegisterUserDtoResponse) {
            RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
            assertNotNull(registerUserDtoResponse.getToken());
        } else {
            checkFailureResponse(response, ServerErrorCode.VALIDATION_ERROR);
        }
    }

    @Test
    public void testRegisterUserNullLogin() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Ivanovna", "Mira", "5", "5", "", "1234");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        if (response instanceof RegisterUserDtoResponse) {
            RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
            assertNotNull(registerUserDtoResponse.getToken());
        } else {
            checkFailureResponse(response, ServerErrorCode.VALIDATION_ERROR);
        }
    }

    @Test
    public void testRegisterUserWrongPassword() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Ivanovna", "Mira", "5", "5", "Masha", "1234");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        if (response instanceof RegisterUserDtoResponse) {
            RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
            assertNotNull(registerUserDtoResponse.getToken());
        } else {
            checkFailureResponse(response, ServerErrorCode.VALIDATION_ERROR);
        }
    }

    @Test
    public void testLogoutUser() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(registerUserDtoRequest.getLogin(),
                registerUserDtoRequest.getPassword());
        Object responseLogin = client.post(baseURL + "/session/", loginDtoRequest, LoginDtoResponse.class, null);
        LoginDtoResponse loginDtoResponse = (LoginDtoResponse) responseLogin;
        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(loginDtoResponse.getToken());
        Object responseLogOut = client.delete(baseURL + "/session/", LogoutDtoResponse.class, logoutDtoRequest.getToken());
        LogoutDtoResponse logoutDtoResponse = (LogoutDtoResponse) responseLogOut;
        assertEquals(CommonResponse.YOU_lEFT_SERVER, logoutDtoResponse.getResult());
    }

    @Test
    public void testDeleteUser() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest(registerUserDtoResponse.getToken());
        Object responseDelete = client.delete(baseURL + "/user", DeleteUserResponse.class, deleteUserRequest.getToken());
        DeleteUserResponse deleteUserResponse = (DeleteUserResponse) responseDelete;
        assertEquals(CommonResponse.YOU_REMOVE_SERVER, deleteUserResponse.getResult());
    }

    @Test
    public void testNominateCandidateAndConsentNomination() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        NominateCandidateDtoRequest nominateCandidateDtoRequest = new NominateCandidateDtoRequest
                (registerUserDtoResponse1.getId());
        Object responseNominate = client.put(baseURL + "/nominate/" + registerUserDtoResponse1.getId(),
                nominateCandidateDtoRequest, NominateCandidateDtoResponse.class, registerUserDtoResponse.getToken());
        NominateCandidateDtoResponse nominateCandidateDtoResponse = (NominateCandidateDtoResponse) responseNominate;
        ConsentNominationDtoRequest consentNominationDtoRequest = new ConsentNominationDtoRequest
                (registerUserDtoResponse.getId());
        Object responseConsent = client.put(baseURL + "/nomination/",
                consentNominationDtoRequest, ConsentNominationDtoResponse.class, registerUserDtoResponse.getToken());
        ConsentNominationDtoResponse consentNominationDtoResponse = (ConsentNominationDtoResponse) responseConsent;
        assertEquals(CommonResponse.NOMINATE, nominateCandidateDtoResponse.getResult());
        assertEquals(registerUserDtoResponse.getToken(), nominateCandidateDtoResponse.getTokenNominated());
        assertEquals(CommonResponse.CONSENT_NOMINATION, consentNominationDtoResponse.getResult());
        assertEquals(registerUserDtoResponse.getToken(), consentNominationDtoResponse.getToken());
    }

    @Test
    public void testNominateNonExistentCandidate() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        NominateCandidateDtoRequest nominateCandidateDtoRequest = new NominateCandidateDtoRequest(25200);
        Object responseNominate = client.put(baseURL + "/nominate/" + nominateCandidateDtoRequest.getId(),
                nominateCandidateDtoRequest, NominateCandidateDtoResponse.class, registerUserDtoResponse.getToken());
        if (responseNominate instanceof NominateCandidateDtoResponse) {
            NominateCandidateDtoResponse nominateCandidateDtoResponse = (NominateCandidateDtoResponse) responseNominate;
            assertNotNull(nominateCandidateDtoResponse.getResult());
        } else {
            checkFailureResponse(responseNominate, ServerErrorCode.WRONG_ID);
        }
    }

    @Test
    public void testConsentNominationNonExistentCandidate() throws IOException, ClassNotFoundException {
        ConsentNominationDtoRequest consentNominationDtoRequest = new ConsentNominationDtoRequest(2520);
        Object responseConsent = client.put(baseURL + "/nomination/",
                consentNominationDtoRequest, ConsentNominationDtoResponse.class, "token");
        if (responseConsent instanceof ConsentNominationDtoResponse) {
            ConsentNominationDtoResponse consentNominationDtoResponse = (ConsentNominationDtoResponse) responseConsent;
            assertNotNull(consentNominationDtoResponse.getToken());
        } else {
            checkFailureResponse(responseConsent, ServerErrorCode.WRONG_ID);
        }
    }

    @Test
    public void testNominateYourself() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        NominateYourselfDtoRequest nominateYourselfDtoRequest = new NominateYourselfDtoRequest(registerUserDtoResponse.getToken());
        Object responseNominate = client.put(baseURL + "/nominate/",
                nominateYourselfDtoRequest, NominateYourselfDtoResponse.class, registerUserDtoResponse.getToken());
        NominateYourselfDtoResponse nominateYourselfDtoResponse = (NominateYourselfDtoResponse) responseNominate;
        assertEquals(CommonResponse.NOMINATE, nominateYourselfDtoResponse.getResult());
    }

    @Test
    public void testNominateYourselfNullUser() throws IOException, ClassNotFoundException {
        NominateYourselfDtoRequest nominateYourselfDtoRequest = new NominateYourselfDtoRequest("123456");
        Object responseNominate = client.put(baseURL + "/nominate/",
                nominateYourselfDtoRequest, NominateYourselfDtoResponse.class, "token");
        if (responseNominate instanceof NominateYourselfDtoResponse) {
            NominateYourselfDtoResponse nominateYourselfDtoResponse = (NominateYourselfDtoResponse) responseNominate;
            assertNotNull(nominateYourselfDtoResponse.getResult());
        } else {
            checkFailureResponse(responseNominate, ServerErrorCode.WRONG_TOKEN);
        }
    }

    @Test
    public void testAddProposalAndRateProposal() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, registerUserDtoResponse.getToken());
        AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
        RateProposalDtoRequest rateProposalDtoRequest = new RateProposalDtoRequest
                (4, addProposalDtoResponse.getIdProposal());
        Object responseRate = client.put(baseURL + "/proposal/",
                rateProposalDtoRequest, RateProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        RateProposalDtoResponse rateProposalDtoResponse = (RateProposalDtoResponse) responseRate;
        assertEquals(CommonResponse.ADD_PROPOSAL, addProposalDtoResponse.getResult());
        assertEquals(CommonResponse.RATE_PROPOSAL, rateProposalDtoResponse.getResult());
        assertEquals(4.5, rateProposalDtoResponse.getRating());
    }

    @Test
    public void testRateNullProposal() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        RateProposalDtoRequest rateProposalDtoRequest = new RateProposalDtoRequest(4, 2000);
        Object responseRate = client.put(baseURL + "/proposal/",
                rateProposalDtoRequest, RateProposalDtoResponse.class, registerUserDtoResponse.getToken());
        if (responseRate instanceof RateProposalDtoResponse) {
            RateProposalDtoResponse rateProposalDtoResponse = (RateProposalDtoResponse) responseRate;
            assertNotNull(rateProposalDtoResponse.getResult());
        } else {
            checkFailureResponse(responseRate, ServerErrorCode.WRONG_ID_PROPOSAL);
        }
    }

    @Test
    public void testAddProposalNullVoter() throws IOException, ClassNotFoundException {
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, "token");
        if (responseAdd instanceof AddProposalDtoResponse) {
            AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
            assertNotNull(addProposalDtoResponse.getResult());
        } else {
            checkFailureResponse(responseAdd, ServerErrorCode.WRONG_TOKEN);
        }
    }

    @Test
    public void testAddProposalToProgramNullCandidate() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
        AddProposalToProgramDtoRequest addProposalToProgramDtoRequest =
                new AddProposalToProgramDtoRequest(addProposalDtoResponse.getIdProposal());
        Object responseAddToProgram = client.post(baseURL + "/proposal/" + addProposalDtoResponse.getIdProposal(),
                addProposalToProgramDtoRequest, AddProposalToProgramDtoResponse.class, "token");
        if (responseAddToProgram instanceof AddProposalToProgramDtoResponse) {
            AddProposalToProgramDtoResponse addProposalToProgramDtoResponse =
                    (AddProposalToProgramDtoResponse) responseAddToProgram;
            assertNotNull(addProposalToProgramDtoResponse.getResult());
        } else {
            checkFailureResponse(responseAddToProgram, ServerErrorCode.WRONG_TOKEN);
        }
    }

    @Test
    public void testAddProposalToProgram() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
        NominateCandidateDtoRequest nominateCandidateDtoRequest = new NominateCandidateDtoRequest(registerUserDtoResponse1.getId());
        Object responseNominate = client.put(baseURL + "/nominate/" + registerUserDtoResponse1.getId(),
                nominateCandidateDtoRequest, NominateCandidateDtoResponse.class, registerUserDtoResponse.getToken());
        NominateCandidateDtoResponse nominateCandidateDtoResponse = (NominateCandidateDtoResponse) responseNominate;
        ConsentNominationDtoRequest consentNominationDtoRequest = new ConsentNominationDtoRequest(registerUserDtoResponse.getId());
        Object responseConsent = client.put(baseURL + "/nomination/",
                consentNominationDtoRequest, ConsentNominationDtoResponse.class, registerUserDtoResponse.getToken());
        ConsentNominationDtoResponse consentNominationDtoResponse = (ConsentNominationDtoResponse) responseConsent;
        AddProposalToProgramDtoRequest addProposalToProgramDtoRequest = new AddProposalToProgramDtoRequest(addProposalDtoResponse.getIdProposal());
        Object responseAddToProgram = client.post(baseURL + "/proposal/" + addProposalDtoResponse.getIdProposal(),
                addProposalToProgramDtoRequest, AddProposalToProgramDtoResponse.class, registerUserDtoResponse.getToken());
        AddProposalToProgramDtoResponse addProposalToProgramDtoResponse = (AddProposalToProgramDtoResponse) responseAddToProgram;
        assertEquals(CommonResponse.ADD_PROPOSAL_TO_PROGRAM, addProposalToProgramDtoResponse.getResult());
    }

    @Test
    public void testRemoveProposalFromProgram() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
        NominateCandidateDtoRequest nominateCandidateDtoRequest = new NominateCandidateDtoRequest(registerUserDtoResponse1.getId());
        Object responseNominate = client.put(baseURL + "/nominate/" + registerUserDtoResponse1.getId(),
                nominateCandidateDtoRequest, NominateCandidateDtoResponse.class, registerUserDtoResponse.getToken());
        NominateCandidateDtoResponse nominateCandidateDtoResponse = (NominateCandidateDtoResponse) responseNominate;
        ConsentNominationDtoRequest consentNominationDtoRequest = new ConsentNominationDtoRequest(registerUserDtoResponse.getId());
        Object responseConsent = client.put(baseURL + "/nomination/",
                consentNominationDtoRequest, ConsentNominationDtoResponse.class, registerUserDtoResponse.getToken());
        ConsentNominationDtoResponse consentNominationDtoResponse = (ConsentNominationDtoResponse) responseConsent;
        AddProposalToProgramDtoRequest addProposalToProgramDtoRequest = new AddProposalToProgramDtoRequest(addProposalDtoResponse.getIdProposal());
        Object responseAddToProgram = client.post(baseURL + "/proposal/" + addProposalDtoResponse.getIdProposal(),
                addProposalToProgramDtoRequest, AddProposalToProgramDtoResponse.class, registerUserDtoResponse.getToken());
        AddProposalToProgramDtoResponse addProposalToProgramDtoResponse = (AddProposalToProgramDtoResponse) responseAddToProgram;
        RemoveProposalFromProgramDtoRequest removeProposalFromProgramDtoRequest = new RemoveProposalFromProgramDtoRequest(addProposalDtoResponse.getIdProposal());
        Object responseRemove = client.delete(baseURL + "/proposal/" + addProposalDtoResponse.getIdProposal(),
                RemoveProposalFromProgramDtoResponse.class, registerUserDtoResponse.getToken());
        RemoveProposalFromProgramDtoResponse removeProposalFromProgramDtoResponse = (RemoveProposalFromProgramDtoResponse) responseRemove;
        assertEquals(CommonResponse.REMOVE_PROPOSAL, removeProposalFromProgramDtoResponse.getResult());
    }

    @Test
    public void testRemoveProposalFromProgramNullCandidate() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
        Object responseRemove = client.delete(baseURL + "/proposal/" + addProposalDtoResponse.getIdProposal(),
                RemoveProposalFromProgramDtoResponse.class, "token");
        if (responseRemove instanceof RemoveProposalFromProgramDtoResponse) {
            RemoveProposalFromProgramDtoResponse removeProposalFromProgramDtoResponse =
                    (RemoveProposalFromProgramDtoResponse) responseRemove;
            assertNotNull(removeProposalFromProgramDtoResponse.getResult());
        } else {
            checkFailureResponse(responseRemove, ServerErrorCode.WRONG_TOKEN);
        }
    }

    @Test
    public void testCancelNomination() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        NominateCandidateDtoRequest nominateCandidateDtoRequest = new NominateCandidateDtoRequest(registerUserDtoResponse1.getId());
        Object responseNominate = client.put(baseURL + "/nominate/" + registerUserDtoResponse1.getId(),
                nominateCandidateDtoRequest, NominateCandidateDtoResponse.class, registerUserDtoResponse.getToken());
        NominateCandidateDtoResponse nominateCandidateDtoResponse = (NominateCandidateDtoResponse) responseNominate;
        ConsentNominationDtoRequest consentNominationDtoRequest = new ConsentNominationDtoRequest(registerUserDtoResponse.getId());
        Object responseConsent = client.put(baseURL + "/nomination/",
                consentNominationDtoRequest, ConsentNominationDtoResponse.class, registerUserDtoResponse.getToken());
        ConsentNominationDtoResponse consentNominationDtoResponse = (ConsentNominationDtoResponse) responseConsent;
        CancelNominationDtoRequest cancelNominationDtoRequest = new CancelNominationDtoRequest(registerUserDtoResponse.getToken());
        Object responseCancel = client.put(baseURL + "/candidate/",
                cancelNominationDtoRequest, CancelNominationDtoResponse.class, registerUserDtoResponse.getToken());
        CancelNominationDtoResponse cancelNominationDtoResponse = (CancelNominationDtoResponse) responseCancel;
        assertEquals(CommonResponse.YOU_NO_CANDIDATE, cancelNominationDtoResponse.getResult());
    }

    @Test
    public void testCancelNominationNullCandidate() throws IOException, ClassNotFoundException {
        CancelNominationDtoRequest cancelNominationDtoRequest =
                new CancelNominationDtoRequest("Token");
        Object responseCancel = client.put(baseURL + "/candidate/",
                cancelNominationDtoRequest, CancelNominationDtoResponse.class, "Token");
        if (responseCancel instanceof CancelNominationDtoResponse) {
            CancelNominationDtoResponse cancelNominationDtoResponse =
                    (CancelNominationDtoResponse) responseCancel;
            assertNotNull(cancelNominationDtoResponse.getResult());
        } else {
            checkFailureResponse(responseCancel, ServerErrorCode.WRONG_TOKEN);
        }
    }

    @Test
    public void testCancelRateProposal() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, registerUserDtoResponse.getToken());
        AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
        RateProposalDtoRequest rateProposalDtoRequest = new RateProposalDtoRequest
                (4, addProposalDtoResponse.getIdProposal());
        Object responseRate = client.put(baseURL + "/proposal/",
                rateProposalDtoRequest, RateProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        RateProposalDtoResponse rateProposalDtoResponse = (RateProposalDtoResponse) responseRate;
        CancelRateProposalDtoRequest cancelRateProposalDtoRequest = new CancelRateProposalDtoRequest(addProposalDtoResponse.getIdProposal());
        Object responseCancelRate = client.put(baseURL + "/proposal/",
                cancelRateProposalDtoRequest, CancelRateProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        CancelRateProposalDtoResponse cancelRateProposalDtoResponse = (CancelRateProposalDtoResponse) responseCancelRate;
        assertEquals(CommonResponse.CANCEL_RATE_PROPOSAL, cancelRateProposalDtoResponse.getResult());
        assertEquals(5, cancelRateProposalDtoResponse.getRating());
    }

    @Test
    public void testCancelRateProposalNullProposal() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest1 = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response1 = client.post(baseURL + "/user", registerUserDtoRequest1, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse1 = (RegisterUserDtoResponse) response1;
        CancelRateProposalDtoRequest cancelRateProposalDtoRequest = new CancelRateProposalDtoRequest(0);
        Object responseCancelRate = client.put(baseURL + "/proposal/",
                cancelRateProposalDtoRequest, CancelRateProposalDtoResponse.class, registerUserDtoResponse1.getToken());
        if (responseCancelRate instanceof CancelRateProposalDtoResponse) {
            CancelRateProposalDtoResponse cancelRateProposalDtoResponse =
                    (CancelRateProposalDtoResponse) responseCancelRate;
            assertNotNull(cancelRateProposalDtoResponse.getResult());
        } else {
            checkFailureResponse(responseCancelRate, ServerErrorCode.VALIDATION_ERROR);
        }
    }

    @Test
    public void testCancelRateProposalNullVoter() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Masha", "Sidorova",
                "Olegovna", "Mira", "5", "5", "Masha", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        AddProposalDtoRequest addProposalDtoRequest = new AddProposalDtoRequest("Green the city");
        Object responseAdd = client.post(baseURL + "/proposal/",
                addProposalDtoRequest, AddProposalDtoResponse.class, registerUserDtoResponse.getToken());
        AddProposalDtoResponse addProposalDtoResponse = (AddProposalDtoResponse) responseAdd;
        CancelRateProposalDtoRequest cancelRateProposalDtoRequest = new CancelRateProposalDtoRequest(addProposalDtoResponse.getIdProposal());
        Object responseCancelRate = client.put(baseURL + "/proposal/",
                cancelRateProposalDtoRequest, CancelRateProposalDtoResponse.class, "token");
        if (responseCancelRate instanceof CancelRateProposalDtoResponse) {
            CancelRateProposalDtoResponse cancelRateProposalDtoResponse =
                    (CancelRateProposalDtoResponse) responseCancelRate;
            assertNotNull(cancelRateProposalDtoResponse.getResult());
        } else {
            checkFailureResponse(responseCancelRate, ServerErrorCode.WRONG_TOKEN);
        }
    }

    @Test
    public void testVoteAgainstAll() throws IOException, ClassNotFoundException {
        RegisterUserDtoRequest registerUserDtoRequest = new RegisterUserDtoRequest("Nastya", "Ivanova",
                "Olegovna", "Zarechnaya", "5", "1", "Nas", "1234567");
        Object response = client.post(baseURL + "/user", registerUserDtoRequest, RegisterUserDtoResponse.class, null);
        RegisterUserDtoResponse registerUserDtoResponse = (RegisterUserDtoResponse) response;
        VoteAgainstAllDtoRequest voteAgainstAllDtoRequest = new VoteAgainstAllDtoRequest(registerUserDtoResponse.getToken());
        Object responseVote = client.put(baseURL + "/vote", voteAgainstAllDtoRequest,
                VoteAgainstAllDtoResponse.class, registerUserDtoResponse.getToken());
        VoteAgainstAllDtoResponse voteAgainstAllDtoResponse = (VoteAgainstAllDtoResponse) responseVote;
        assertEquals(CommonResponse.VOTED_AGAINST_ALL, voteAgainstAllDtoResponse.getResult());
    }

    @Test
    public void testVoteAgainstAllNullVoter() throws IOException, ClassNotFoundException {
        VoteAgainstAllDtoRequest voteAgainstAllDtoRequest = new VoteAgainstAllDtoRequest("Token");
        Object responseVote = client.put(baseURL + "/vote", voteAgainstAllDtoRequest,
                VoteAgainstAllDtoResponse.class, "Token");
        if (responseVote instanceof VoteAgainstAllDtoResponse) {
            VoteAgainstAllDtoResponse voteAgainstAllDtoResponse =
                    (VoteAgainstAllDtoResponse) responseVote;
            assertNotNull(voteAgainstAllDtoResponse.getResult());
        } else {
            checkFailureResponse(responseVote, ServerErrorCode.WRONG_TOKEN);
        }
    }
}
