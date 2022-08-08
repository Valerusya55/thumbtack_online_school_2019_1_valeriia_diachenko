package net.thumbtack.school.elections.rest.mappers;


import net.thumbtack.school.elections.exeption.ServerErrorCode;
import net.thumbtack.school.elections.exeption.ServerException;
import net.thumbtack.school.elections.utils.ServiceUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            message.append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\n");
        }

        return ServiceUtils.failureResponse(Response.Status.BAD_REQUEST,
                new ServerException(ServerErrorCode.VALIDATION_ERROR));
    }
}
