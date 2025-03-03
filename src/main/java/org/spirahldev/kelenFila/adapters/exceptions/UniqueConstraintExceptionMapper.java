package org.spirahldev.kelenFila.adapters.exceptions;


import org.hibernate.exception.ConstraintViolationException;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;

import io.quarkus.logging.Log;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class UniqueConstraintExceptionMapper implements ExceptionMapper<PersistenceException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueConstraintExceptionMapper.class);

    @Override
    public Response toResponse(PersistenceException exception) {
        AppResponse<?> response=new AppResponse<>();


        if (exception.getCause() instanceof ConstraintViolationException) {
            response.setStatusCode(AppStatusCode.RESOURCE_ALREADY_EXIST);

            return Response.status(Response.Status.CONFLICT)
                    .entity(response)
                    .build();
        }

        response.setStatusMessage("Une erreur s'est produite lors de la création de la ressource");
        response.setStatusCode(AppStatusCode.INTERNAL_SERVER_ERROR);

        LOGGER.info("Erreur lors de la création de la ressource", exception.getMessage());
        Log.error("Erreur lors de la création de la ressource", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();

    }


    // private String extractFieldName(ConstraintViolationException exception) {
    //     SQLException sqlException = exception.getSQLException();
    //     String message = sqlException.getMessage();

    //     // Extrait le nom de la colonne à partir du message d'erreur SQL
    //     if (message.contains("key")) {
    //         int startIndex = message.indexOf("key") + 4; // Skip "key "
    //         int endIndex = message.indexOf(")", startIndex);
    //         return message.substring(startIndex, endIndex);
    //     }

    //     return "Inconnu";
    // }
    
}
