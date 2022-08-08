package net.thumbtack.school.elections.utils;

import com.google.gson.Gson;
import net.thumbtack.school.elections.exeption.ErrorResponse;
import net.thumbtack.school.elections.exeption.ServerException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ServiceUtils {
    private static final Gson GSON = new Gson();

    public static Response failureResponse(Status status, ServerException ex) {
        return Response.status(status).entity(GSON.toJson(new ErrorResponse(ex)))
                .type(MediaType.APPLICATION_JSON).build();
    }

    public static Response failureResponse(ServerException ex) {
        return failureResponse(Status.BAD_REQUEST, ex);
    }

}
