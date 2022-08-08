package net.thumbtack.school.elections.resources;
import net.thumbtack.school.elections.rest.request.user.*;
import net.thumbtack.school.elections.rest.response.user.LogoutDtoResponse;
import net.thumbtack.school.elections.service.UserService;
import net.thumbtack.school.elections.settings.Mode;

import javax.ws.rs.*;
import javax.validation.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api")
public class UserResources {

    private UserService userService = new UserService(Mode.RAM);

    @POST
    @Path("/user")
    @Consumes("application/json")
    @Produces("application/json")
    public Response registerUser(@Valid RegisterUserDtoRequest request) {
        return userService.registerUser(request);
    }

    @POST
    @Path("/session/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response loginUser(@Valid LoginDtoRequest request) {
        return userService.loginUser(request);
    }

    @DELETE
    @Path("/session/")
    @Consumes("application/json")
    @Produces("application/json")
    public LogoutDtoResponse logOutUser(@HeaderParam("token") String token) {
        return userService.logOutUser(token);
    }

    @DELETE
    @Path("/user")
    @Produces("application/json")
    public Response deleteUser(@HeaderParam("token") String token) {
        return userService.deleteUser(token);
    }

    @PUT
    @Path("/nomination/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response consentNomination(@HeaderParam("token") String token) {
        return userService.consentNomination(token);
    }

    @PUT
    @Path("/nominate/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response nominateCandidate(@PathParam("id") int id, @HeaderParam("token") String token) {
        return userService.nominateCandidate(id, token);
    }

    @PUT
    @Path("/nominate/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response nominateYourself(@HeaderParam("token") String token) {
        return userService.nominateYourself(token);
    }

    @GET
    @Path("/proposals/")
    @Produces("application/json")
    public Response getAllProposalByUser(@Valid GetAllProposalByUserDtoRequest request, @HeaderParam("token") String token) {
        if (request == null) {
            return userService.getAllSortedProposals(token);
        } else {
            return userService.getAllProposalByUser(request, token);
        }
    }
}
