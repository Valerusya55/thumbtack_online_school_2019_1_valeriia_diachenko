package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Program;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface CandidateMapper {

    @Update("UPDATE user,candidate SET nomination = \"NOT_NOMINATED\",consentToNomination = \"DISAGREE\"" +
            " WHERE user.id = #{candidate.user.id} AND candidate.idUser = #{candidate.user.id}")
    void cancelNomination(@Param("candidate")Candidate candidate);

    @Delete("DELETE FROM proposal WHERE id = #{proposalId}")
    void removeProposalFromProgram(@Param("candidate") Candidate candidate,@Param("proposalId") int proposalId);

    @Select("SELECT * FROM candidate WHERE idUser IN (SELECT idUser FROM session WHERE token = #{token})")
    Candidate getCandidateByUserToken(String token);

    @Select("SELECT * FROM candidate")
    List<Candidate> getAllCandidatesWithProgram();

    @Select("SELECT * FROM proposal WHERE idUser = #{candidate.user.id}")
    List<Proposal> getProposalByUserId(@Param("candidate")Candidate candidate);

    @Insert("INSERT INTO program (idUser) VALUES (#{candidate.user.id})")
    void addProposalToProgram(@Param("candidate")Candidate candidate, int proposalId);

    @Select("SELECT * FROM candidate WHERE idUser = #{id}")
    Candidate getCandidateByUserId(Integer id);
}
