package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VoteMapper {

    @Insert("INSERT INTO voting (idUser, idCandidate VALUES #{user.id}, #{candidate.user.id}")
    void voteForCandidate(@Param("user")User user,@Param("candidate")Candidate candidate);

    @Insert("INSERT INTO voting (idUser, idCandidate) VALUES (#{user.id}, NULL)")
    void voteAgainstAll(@Param("user")User user);

    @Select("SELECT idCandidate FROM voting GROUP BY idCandidate ORDER BY COUNT(*) DESC LIMIT 1")
    public Candidate countingOfVotes() throws ServerException;
}
