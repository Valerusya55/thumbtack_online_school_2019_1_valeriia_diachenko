package net.thumbtack.school.elections.daoimpldatabase;

import net.thumbtack.school.elections.dao.SessionDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Session;
import net.thumbtack.school.elections.model.User;

public class SessionDaoImplDataBase implements SessionDao {
    @Override
    public void addToken(Session session) {
        DataBase.getDataBase().addToken(session.getToken(),session.getUser());
    }

    @Override
    public User getUserByToken(String token) throws ServerException {
        return DataBase.getDataBase().getUserByToken(token);
    }
}
