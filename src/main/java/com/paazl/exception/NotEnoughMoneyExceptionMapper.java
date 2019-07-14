package com.paazl.exception;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class NotEnoughMoneyExceptionMapper implements ExceptionMapper<NotEnoughMoneyException> {

    @Override
    public Response toResponse(NotEnoughMoneyException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
