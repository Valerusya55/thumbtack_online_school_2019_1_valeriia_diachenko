package net.thumbtack.school.elections.mappers;


import net.thumbtack.school.elections.model.Session;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SessionMapper {
    @Insert("INSERT INTO session (token, idUser) VALUES ( #{session.token}, #{session.user.id} )")
    @Options(useGeneratedKeys = true, keyProperty = "session.id")
    void addToken(@Param("session")Session session);

    @Select("SELECT id,token,idUser FROM session WHERE id = #{id}")
    Session getById(int id);

    @Select("SELECT user.id,name,surname,patronymic,street,numberHouse,numberFlat,login,password,nomination " +
            "FROM session JOIN user ON user.id = idUser WHERE session.token = #{token}")
    User getUserByToken(String token);

}
