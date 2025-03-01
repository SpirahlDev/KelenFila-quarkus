package org.spirahldev.kelenFila.adapters.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class JsonExceptionMapper implements ExceptionMapper<InvalidFormatException>{

    @Override
    public Response toResponse(InvalidFormatException exception) {

        return Response.status(Response.Status.CONFLICT)
        .entity("oui oui")
        .build();
    }
    
}
