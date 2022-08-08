package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Session;
import net.thumbtack.school.elections.model.User;

public interface SessionDao {
    void addToken(Session session);
    User getUserByToken(String token) throws ServerException;
}
