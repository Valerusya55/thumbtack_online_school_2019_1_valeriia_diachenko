package net.thumbtack.school.elections.daoimpldatabase;

import net.thumbtack.school.elections.dao.UserDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;

import java.util.List;

public class UserDaoImplDataBase implements UserDao {
    @Override
    public void registerUser(User user) throws ServerException {
        DataBase.getDataBase().registerUser(user);
    }

    @Override
    public void logoutUser(String token) {
        DataBase.getDataBase().logOutUser(token);
    }

    @Override
    public void consentNomination(User user) {
        DataBase.getDataBase().consentNomination(user);
    }

    @Override
    public void nominateCandidate(User nominated, User suggest) {
        DataBase.getDataBase().nominateCandidate(nominated, suggest);
    }

    @Override
    public void nominateYourself(User nominated) {
        DataBase.getDataBase().nominateYourself(nominated);
    }

    @Override
    public List<Proposal> getAllProposalByUser(List<Integer> userList) {
        return DataBase.getDataBase().getAllProposalByUser(userList);
    }

    @Override
    public User getUserById(int idUser) throws ServerException {
        return DataBase.getDataBase().getUserById(idUser);
    }

    @Override
    public User getUserByLogin(String login, String password) throws ServerException {
        return DataBase.getDataBase().getUserByLogin(login, password);
    }

    @Override
    public User getUserByUserToken(String token) {
        return DataBase.getDataBase().getUserByUserToken(token);
    }

    @Override
    public List<Proposal> getAllSortedProposals() {
        return DataBase.getDataBase().getAllSortedProposals();
    }
}