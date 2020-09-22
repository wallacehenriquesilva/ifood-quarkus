package com.wallace.resources.handlers;

import com.wallace.exceptions.RestaurantNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestaurantNotFoundHandler implements ExceptionMapper<RestaurantNotFoundException> {

    @Override
    public Response toResponse(RestaurantNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .build();
    }
}
