package org.spirahldev.kelenFila.adapters.exceptions;

import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;


import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException>{

    @Override
    public Response toResponse(NullPointerException exception) {
        Log.error(exception);
        
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
