package net.thumbtack.school.elections.database;

import net.thumbtack.school.elections.commonresponse.CommonResponse;
import net.thumbtack.school.elections.enumformodel.ConsentToNomination;
import net.thumbtack.school.elections.enumformodel.Nomination;
import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBase {
    private static DataBase dataBase;
    private static Society society = new Society(1, new CopyOnWriteArrayList<>());
    private AtomicInteger idUser = new AtomicInteger(2);
    private AtomicInteger idProposal = new AtomicInteger(1);
    private Map<String, User> userByLoginMap = new ConcurrentHashMap<>();
    private Map<Integer, User> userByIdMap = new ConcurrentHashMap<>();
    private Map<String, User> userByTokenMap = new ConcurrentHashMap<>();
    private Map<Integer, Proposal> idProposals = new ConcurrentHashMap<>();
    private MultiValuedMap<Proposal, EvaluationProposal> evaluationProposalsMap = new ArrayListValuedHashMap<>();
    private Map<Proposal, Integer> proposalSumEvaluation = new ConcurrentHashMap<>();
    private Map<Candidate, Program> candidateProgramMap = new ConcurrentHashMap<>();
    private Map<User, Candidate> userCandidate = new ConcurrentHashMap<>();
    private Map<Candidate, Integer> numberOfVoices = new ConcurrentHashMap<>();
    private List<User> voted = new CopyOnWriteArrayList<>();
    private int voiceAgainstAll = 0;

    public synchronized static DataBase getDataBase() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public static DataBase createDataBase() {
        return dataBase = new DataBase();
    }

    public void clearDataBase(){
        society = new Society(1, new CopyOnWriteArrayList<>());
        idUser = new AtomicInteger(2);
        idProposal = new AtomicInteger(1);
        userByLoginMap.clear();
        userByIdMap.clear();
        userByTokenMap.clear();
        idProposals.clear();
        evaluationProposalsMap.clear();
        proposalSumEvaluation.clear();
        candidateProgramMap.clear();
        userCandidate.clear();
        numberOfVoices.clear();
        voted.clear();
        voiceAgainstAll = 0;
    }

    public static void readFile(DataBase database) {
        dataBase.userByLoginMap = database.userByLoginMap;
        dataBase.userByIdMap = database.userByIdMap;
        dataBase.userByTokenMap = database.userByTokenMap;
        dataBase.idProposals = database.idProposals;
        dataBase.evaluationProposalsMap = database.evaluationProposalsMap;
        dataBase.proposalSumEvaluation = database.proposalSumEvaluation;
        dataBase.candidateProgramMap = database.candidateProgramMap;
        dataBase.userCandidate = database.userCandidate;
        dataBase.numberOfVoices = database.numberOfVoices;
        dataBase.voted = database.voted;
    }

    public void voteForCandidate(User voter, Candidate candidate) {
        if (!voted.contains(voter)) {
            numberOfVoices.putIfAbsent(candidate, numberOfVoices.get(candidate) + 1);
            voted.add(voter);
        }
    }

    public void voteAgainstAll(User voter) {
        if (!voted.contains(voter)) {
            voiceAgainstAll++;
            voted.add(voter);
        }
    }

    public Candidate countingOfVotes() throws ServerException {
        int max = 0;
        Candidate winner = null;
        for (int vote : numberOfVoices.values()) {
            if (vote > max) {
                max = vote;
            }
        }
        if (max < voiceAgainstAll) {
            throw new ServerException(ServerErrorCode.ELECTION_NOT_HELD);
        }
        for (Map.Entry<Candidate, Integer> map : numberOfVoices.entrySet()) {
            if (map.getValue() == max) {
                winner = map.getKey();
            }
        }
        return winner;
    }

    public Candidate getCandidateByUserToken(String token) throws ServerException {
        User user = userByTokenMap.get(token);
        if(user ==null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
        return userCandidate.get(user);
    }

    public Candidate getCandidateByUserId(Integer id) throws ServerException {
        User user = userByIdMap.get(id);
        if(user ==null){
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
        return userCandidate.get(user);
    }

    public User getUserByUserToken(String token) {
        return userByTokenMap.get(token);
    }

    public int getNewIdUser() {
        return idUser.incrementAndGet();
    }

    public int getNewIdProposal() {
        return idProposal.incrementAndGet();
    }

    public Proposal getProposalById(int idProposal) {
        return idProposals.get(idProposal);
    }

    public void addProposal(Proposal proposal) throws ServerException {
        if (proposal == null) {
            throw new ServerException(ServerErrorCode.THE_DATA_IS_INCORRECT);
        } else {
            proposal.getAuthor().addProposal(proposal);
            proposal.setId(getNewIdProposal());
            idProposals.put(proposal.getId(), proposal);
            evaluationProposalsMap.put(proposal, new EvaluationProposal(proposal.getAuthor(), 5));
        }
    }

    public List<Proposal> getProposalByUserId(Candidate candidate) {
        return candidateProgramMap.get(candidate).getProgram();
    }

    public void nominateYourself(User nominated) {
        nominated.setNomination(Nomination.NOMINATED);
        userCandidate.put(nominated, new Candidate(nominated, ConsentToNomination.AGREE,
                new Program(new ArrayList<>(nominated.getUserProposals()))));
    }

    public void nominateCandidate(User nominated, User suggest) {
        suggest.setNomination(Nomination.NOMINATED);
        userCandidate.put(nominated, new Candidate(nominated, ConsentToNomination.DISAGREE,
                new Program(new ArrayList<>(nominated.getUserProposals()))));
    }

    public CommonResponse consentNomination(User nominated) {
        if (userCandidate.containsKey(nominated)) {
            Candidate candidate = new Candidate(nominated, ConsentToNomination.AGREE,
                    new Program(new ArrayList<>(nominated.getUserProposals())));
            userCandidate.put(nominated, candidate);
        }
        return CommonResponse.YOU_CANDIDATE;
    }

    public CommonResponse cancelNomination(Candidate candidate) {
        candidate.setConsentToNomination(ConsentToNomination.DISAGREE);
        userCandidate.remove(candidate.getUser());
        return CommonResponse.YOU_NO_CANDIDATE;
    }

    public void addProposalToProgram(Candidate candidate, int proposalId) {
        candidate.getProgram().addProposal(idProposals.get(proposalId));
        candidateProgramMap.put(candidate, candidate.getProgram());
    }

    public void removeProposalFromProgram(Candidate candidate, int proposalId) {
        candidateProgramMap.get(candidate).getProgram().remove(idProposals.get(proposalId));
        candidate.getProgram().deleteProposal(idProposals.get(proposalId));
        if (candidate.getProgram().getProgram().isEmpty()) {
            candidateProgramMap.remove(candidate);
        }
        candidateProgramMap.put(candidate, candidate.getProgram());
    }

    public List<Candidate> getAllCandidatesWithProgram() {
        return new ArrayList<>(candidateProgramMap.keySet());
    }

    public List<Proposal> getAllProposalByUser(List<Integer> userList) {
        List<Proposal> proposalList = new ArrayList<>();
        for (int id : userList) {
            proposalList.addAll(userByIdMap.get(id).getUserProposals());
        }
        return proposalList;
    }

    public List<Proposal> getAllSortedProposals() {
        Map<Proposal, Double> proposalMap = new HashMap<>();
        for (Proposal proposal : idProposals.values()) {
            proposalMap.put(proposal, proposal.getAverageRating());
        }
        proposalMap.entrySet().stream().sorted(Map.Entry.comparingByValue());
        return new ArrayList<>(proposalMap.keySet());
    }

    public void rateProposal(EvaluationProposal evaluationProposals, Proposal proposal) {
        if (!proposal.getRatings().contains(evaluationProposals)) {
            proposal.getRatings().add(evaluationProposals);
        }
    }

    public void cancelRateProposal(Proposal proposal, User voter) {
        proposal.getRatings().removeIf(evaluationProposals -> evaluationProposals.getUser().equals(voter));
    }

    public void logOutUser(String token) {
        userByTokenMap.remove(token);
    }

    public void deleteUser(String token) {
        for (Proposal proposal : userByTokenMap.get(token).getUserProposals()) {
            society.getProposals().add(proposal);
        }
        userByLoginMap.remove(userByTokenMap.get(token).getLogin());
        userByIdMap.remove(userByTokenMap.get(token).getId());
        userCandidate.remove(userByTokenMap.get(token));
        userByTokenMap.remove(token);
    }

    public void addToken(String token, User user) {
        userByTokenMap.put(token, user);
    }

    public User getUserById(int idUser) {
        return userByIdMap.get(idUser);
    }

    public User getUserByToken(String token) {
        return userByTokenMap.get(token);
    }

    public void registerUser(User user) {
        user.setId(getNewIdUser());
        userByLoginMap.putIfAbsent(user.getLogin(), user);
        userByIdMap.putIfAbsent(user.getId(), user);
    }

    public User getUserByLogin(String login, String password) throws ServerException {
        if (userByLoginMap.get(login) != null && !userByLoginMap.get(login).getPassword().equals(password)) {
            throw new ServerException(ServerErrorCode.PASSWORD_IS_INCORRECT);
        }
        return userByLoginMap.get(login);
    }
}
