package net.thumbtack.school.elections;

import net.thumbtack.school.elections.commonresponse.CommonResponse;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.enumformodel.ConsentToNomination;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;


public class TestDataBase {
    @Test
    public void testRegisterUserAndGetUserById() throws ServerException {
        User user = new User("Светлана", "Петрова", "Витальевна",
                "Сибирская", "7", null, "Svetlana", "123456");
        DataBase dataBase = new DataBase();
        dataBase.registerUser(user);
        assertEquals(user, dataBase.getUserById(user.getId()));
    }

    @Test
    public void testRegisterUserAndGetUserByLogin() throws ServerException {
        User user = new User("Мария", "Иванова", "Витальевна",
                "Сибирская", "7", null, "Masha", "123456");
        DataBase dataBase = new DataBase();
        dataBase.registerUser(user);
        assertEquals(user, dataBase.getUserByLogin(user.getLogin(), user.getPassword()));
    }

    @Test
    public void testRateProposal() throws ServerException {
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        Proposal proposal = new Proposal(user, "Озеленить город");
        User user1 = new User("Анна", "Катаман", "Александровна",
                "Майорова", "27", "99", "Anna", "1234567");
        EvaluationProposal evaluationProposal = new EvaluationProposal(user1, 4);
        DataBase dataBase = new DataBase();
        dataBase.addProposal(proposal);
        dataBase.rateProposal(evaluationProposal, proposal);
        assertEquals(4.5, proposal.getAverageRating(), 10 ^ -6);
    }

    @Test
    public void testCancelRateProposal() throws ServerException {
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        Proposal proposal = new Proposal(user, "Озеленить город");
        User user1 = new User("Анна", "Катаман", "Александровна",
                "Майорова", "27", "99", "Anna", "1234567");
        EvaluationProposal evaluationProposals = new EvaluationProposal(user1, 4);
        DataBase dataBase = new DataBase();
        dataBase.addProposal(proposal);
        dataBase.rateProposal(evaluationProposals, proposal);
        dataBase.cancelRateProposal(proposal, user1);
        assertEquals(5, proposal.getAverageRating(), 10 ^ -6);
    }

    @Test
    public void testGetAllSortedProposals() throws ServerException {
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        Proposal proposal = new Proposal(user, "Озеленить город");
        Proposal proposal1 = new Proposal(user, "Поставить светофоры");
        User user1 = new User("Анна", "Катаман", "Александровна",
                "Майорова", "27", "99", "Anna", "1234567");
        EvaluationProposal evaluationProposals = new EvaluationProposal(user1, 4);
        DataBase dataBase = new DataBase();
        dataBase.addProposal(proposal);
        dataBase.addProposal(proposal1);
        dataBase.rateProposal(evaluationProposals, proposal);
        List<Proposal> expected = new ArrayList<>();
        Collections.addAll(expected, proposal, proposal1);
        Collections.sort(expected);
        List<Proposal> actual = dataBase.getAllSortedProposals();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllProposalByUser() throws ServerException {
        DataBase dataBase = new DataBase();
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        dataBase.registerUser(user);
        User user1 = new User("Анна", "Катаман", "Александровна",
                "Майорова", "27", "99", "Anna", "1234567");
        dataBase.registerUser(user1);
        Proposal proposal = new Proposal(user, "Озеленить город");
        Proposal proposal1 = new Proposal(user1, "Поставить светофоры");
        dataBase.addProposal(proposal);
        dataBase.addProposal(proposal1);
        List<Integer> listId = new ArrayList<>();
        Collections.addAll(listId, user.getId(), user1.getId());
        List<Proposal> expected = new ArrayList<>();
        Collections.addAll(expected, proposal, proposal1);
        Collections.sort(expected);
        List<Proposal> actual = dataBase.getAllProposalByUser(listId);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testNominateCandidate() throws ServerException {
        DataBase dataBase = new DataBase();
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        dataBase.registerUser(user);
        User user1 = new User("Анна", "Катаман", "Александровна",
                "Майорова", "27", "99", "Anna", "1234567");
        dataBase.registerUser(user1);
        dataBase.nominateCandidate(user, user1);
        assertEquals(CommonResponse.YOU_CANDIDATE, dataBase.consentNomination(user));
    }

    @Test
    public void testGetAllCandidatesWithProgram() throws ServerException {
        DataBase dataBase = new DataBase();
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        dataBase.registerUser(user);
        User user1 = new User("Анна", "Катаман", "Александровна",
                "Майорова", "27", "99", "Anna", "1234567");
        dataBase.registerUser(user1);
        dataBase.nominateCandidate(user, user1);
        dataBase.consentNomination(user);
        dataBase.nominateYourself(user1);
        dataBase.consentNomination(user1);
        Proposal proposal = new Proposal(user, "Озеленить город");
        dataBase.addProposal(proposal);
        Proposal proposal1 = new Proposal(user, "Обустроить парки");
        dataBase.addProposal(proposal1);
        Candidate candidate = new Candidate(user, ConsentToNomination.AGREE, new Program(new ArrayList<>()));
        dataBase.addProposalToProgram(candidate, proposal.getId());
        Candidate candidate1 = new Candidate(user1, ConsentToNomination.AGREE, new Program(new ArrayList<>()));
        dataBase.addProposalToProgram(candidate1, proposal1.getId());
        List<Candidate> expected = new ArrayList<>();
        Collections.addAll(expected, candidate1, candidate);
        List<Candidate> actual = dataBase.getAllCandidatesWithProgram();
        expected.sort((o1, o2) -> Integer.compare(o1.getUser().getId(), o2.getUser().getId()));
        actual.sort((o1, o2) -> Integer.compare(o1.getUser().getId(), o2.getUser().getId()));
        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveProposalFromProgram() throws ServerException {
        DataBase dataBase = new DataBase();
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        dataBase.registerUser(user);
        dataBase.nominateYourself(user);
        dataBase.consentNomination(user);
        Proposal proposal = new Proposal(user, "Озеленить город");
        dataBase.addProposal(proposal);
        Proposal proposal1 = new Proposal(user, "Обустроить парки");
        dataBase.addProposal(proposal1);
        Candidate candidate = new Candidate(user, ConsentToNomination.AGREE, new Program(new ArrayList<>()));
        dataBase.addProposalToProgram(candidate, proposal.getId());
        dataBase.addProposalToProgram(candidate, proposal1.getId());
        dataBase.removeProposalFromProgram(candidate, proposal.getId());
        List<Proposal> expected = new ArrayList<>();
        expected.add(proposal1);
        assertEquals(expected, dataBase.getProposalByUserId(candidate));
    }

    @Test
    public void testGetProposalById() throws ServerException {
        DataBase dataBase = new DataBase();
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        dataBase.registerUser(user);
        dataBase.nominateYourself(user);
        dataBase.consentNomination(user);
        Proposal proposal = new Proposal(user, "Озеленить город");
        dataBase.addProposal(proposal);
        assertEquals(proposal, dataBase.getProposalById(proposal.getId()));
    }

    @Test
    public void testCancelNomination() throws ServerException {
        DataBase dataBase = new DataBase();
        User user = new User("Валерия", "Дьяченко", "Витальевна",
                "Сибирская", "7", null, "Valeriya", "123456");
        dataBase.registerUser(user);
        User user1 = new User("Анна", "Катаман", "Александровна",
                "Майорова", "27", "99", "Anna", "1234567");
        dataBase.registerUser(user1);
        dataBase.nominateCandidate(user, user1);
        Candidate candidate = new Candidate(user, ConsentToNomination.DISAGREE, new Program(new ArrayList<>()));
        assertEquals(CommonResponse.YOU_NO_CANDIDATE, dataBase.cancelNomination(candidate));
    }
}
