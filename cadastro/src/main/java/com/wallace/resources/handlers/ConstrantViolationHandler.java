package com.wallace.resources.handlers;

import com.wallace.resources.handlers.models.responses.ErrorResponse;
import com.wallace.resources.handlers.models.responses.MultipleErrorResponse;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ConstrantViolationHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(
                        MultipleErrorResponse.of(exception.getConstraintViolations()
                                .stream()
                                .map(constraintViolation -> ErrorResponse.of(
                                        constraintViolation.getMessage(),
                                        constraintViolation.getPropertyPath().toString()))
                                .collect(Collectors.toList()))
                )
                .build();
    }
}
