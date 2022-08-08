package net.thumbtack.school.elections.resources;
import net.thumbtack.school.elections.rest.request.voter.*;
import net.thumbtack.school.elections.service.VoterService;
import net.thumbtack.school.elections.settings.Mode;

import javax.ws.rs.*;
import javax.validation.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api")
public class VoterResources {
    private VoterService voterService = new VoterService(Mode.RAM);

    @POST
    @Path("/proposal/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addProposal(@Valid AddProposalDtoRequest request, @HeaderParam("token") String token) {
        return voterService.addProposal(request, token);
    }

    @GET
    @Path("/proposal/{id}")
    @Produces("application/json")
    public Response getProposalById(@PathParam("id") int id, @HeaderParam("token") String token) {
        return voterService.getProposalById(id, token);
    }

    @PUT
    @Path("/proposal/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response rateProposal(@Valid RateProposalDtoRequest request, @HeaderParam("token") String token) {
        if (request.getMark() != null) {
            return voterService.rateProposal(request, token);
        } else {
            return voterService.cancelRateProposal(request, token);
        }
    }
}