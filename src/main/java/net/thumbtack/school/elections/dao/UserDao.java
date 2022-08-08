package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    void registerUser(User user) throws ServerException;
    void logoutUser(String token);
    void consentNomination(User user);
    void nominateCandidate(User nominated, User suggest);
    void nominateYourself(User nominated);
    List<Proposal> getAllProposalByUser(List<Integer> userList);
    User getUserById(int idUser) throws ServerException;
    User getUserByLogin(String login, String password) throws ServerException;
    User getUserByUserToken(String token);
    List<Proposal> getAllSortedProposals();
}
