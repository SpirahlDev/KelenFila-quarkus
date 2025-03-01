package org.spirahldev.kelenFila.adapters.exceptions;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;

import jakarta.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonMappingException;

import io.quarkus.logging.Log;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    
    
    @Override
    public Response toResponse(Throwable exception) {
        Log.error(exception);
        Throwable rootCause = getRootCause(exception);

        
        if (exception instanceof JsonMappingException) {
            if (rootCause.getMessage().contains("HV000116")) {
                return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new AppResponse<>(
                        AppStatusCode.VALIDATION_ERROR
                    ))
                    .build();
            }


            
            // Pour les autres erreurs de mapping JSON
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new AppResponse<>(
                    AppStatusCode.VALIDATION_ERROR,null
                ))
                .build();
        }

        // Erreur générique
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new AppResponse<>(
                AppStatusCode.INTERNAL_SERVER_ERROR,null
            ))
            .build();
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}