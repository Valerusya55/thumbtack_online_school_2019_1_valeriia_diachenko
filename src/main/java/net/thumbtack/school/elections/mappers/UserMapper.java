package net.thumbtack.school.elections.mappers;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    @Insert("INSERT INTO user (name, surname, patronymic, street, numberHouse, numberFlat, login, password, nomination) " +
            "VALUES " + "( #{user.name}, #{user.surname}, #{user.patronymic}, #{user.street}," +
            " #{user.numberHouse} ,#{user.numberFlat} ,#{user.login} ,#{user.password} ,\"NOT_NOMINATED\")")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    void registerUser(@Param("user") User user);

    @Insert("INSERT INTO session (name, surname, patronymic, street, numberHouse, numberFlat, login, password, nomination) " +
            "VALUES " + "( #{user.name}, #{user.surname}, #{user.patronymic}, #{user.street}," +
            " #{user.numberHouse} ,#{user.numberFlat} ,#{user.login} ,#{user.password} ,\"NOT_NOMINATED\")")
    void loginUser(@Param("user")User user);

    @Delete("DELETE FROM session WHERE token = #{token}")
    void logoutUser(String token);

    @Update("UPDATE candidate set consentToNomination = \"AGREE\" WHERE idUser = #{user.id}")
    void consentNomination(@Param("user") User user);

    @Update("UPDATE user set nomination = \"NOMINATED\" WHERE id = #{nominated.id}")
    void nominateCandidate(@Param("nominated")User nominated, User suggest);

    @Update("UPDATE user,candidate SET nomination = \"NOMINATED\",consentToNomination = \"AGREE\"" +
            " WHERE user.id = #{nominated.id} AND candidate.idUser = #{nominated.id}")
    void nominateYourself(@Param("nominated")User nominated);

    @Select("SELECT * FROM user WHERE id = #{idUser}")
    User getUserById(int idUser) throws ServerException;

    @Select("SELECT * FROM user WHERE login = #{login} AND password = #{password}")
    User getUserByLogin(String login, String password) throws ServerException;

    @Select("SELECT * FROM user WHERE id in (SELECT idUser FROM session WHERE token = #{token})")
    User getUserByUserToken(String token);

    @Insert("INSERT INTO candidate (consentToNomination, idUser) VALUES (\"AGREE\", #{user.id})")
    void addCandidate(@Param("user")User user);

    @Select({"<script>",
            "SELECT * FROM proposal WHERE",
            "<foreach item='item' collection='list' separator=','>",
            "idUser = item.id",
            "</foreach>",
            "</script>"})
    @Results()
    List<Proposal> getAllProposalByUser(@Param("userList")List<Integer> userList);


    List<Proposal> getAllSortedProposals();
}
