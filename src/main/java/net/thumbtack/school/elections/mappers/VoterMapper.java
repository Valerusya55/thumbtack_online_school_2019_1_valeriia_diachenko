package net.thumbtack.school.elections.mappers;

import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.model.EvaluationProposal;
import net.thumbtack.school.elections.model.Proposal;
import net.thumbtack.school.elections.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface VoterMapper {

    @Insert("INSERT INTO evaluationProposal (idUser, idProposal, rating) VALUES " +
            "( #{evaluationProposals.user.id}, #{proposal.id}, #{evaluationProposals.rating})")
    void rateProposal(@Param("evaluationProposals") EvaluationProposal evaluationProposals, @Param("proposal") Proposal proposal);

    @Delete("DELETE FROM evaluationProposal WHERE idUser = #{user.id} AND idProposal = #{proposal.id}")
    void cancelRateProposal(@Param("proposal") Proposal proposal, @Param("user") User user);

    @Insert("INSERT INTO proposal (proposal, idUser) VALUES ( #{proposal.proposal}, #{proposal.author.id})")
    @Options(useGeneratedKeys = true, keyProperty = "proposal.id")
    void addProposal(@Param("proposal") Proposal proposal) throws ServerException;

    @Insert("INSERT INTO evaluationProposal (idUser, idProposal, rating) VALUES (#{proposal.author.id}, #{proposal.id}, 5)")
    void addEvaluationProposal(@Param("proposal") Proposal proposal) throws ServerException;

    @Select("SELECT * FROM evaluationProposal WHERE idProposal = #{idProposal}")
    @Result(property = "user", column = "idUser", javaType = User.class,
            one = @One(select = "net.thumbtack.school.elections.mappers.UserMapper.getUserById"))
    EvaluationProposal getEvaluationProposalByIdProposal(@Param("idProposal") int idProposal);

    @Select("SELECT * FROM proposal WHERE id = #{idProposal}")
    @Results({
            @Result(property = "author", column = "idUser", javaType = User.class,
                    one = @One(select = "net.thumbtack.school.elections.mappers.UserMapper.getUserById")),
            @Result(property = "ratings", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.elections.mappers.VoterMapper.getEvaluationProposalByIdProposal",
                            fetchType = FetchType.LAZY)),
            @Result(property = "id", column = "id")
    })
    Proposal getProposalById(@Param("idProposal") int idProposal);
}
