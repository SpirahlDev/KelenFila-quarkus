package org.spirahldev.kelenFila.adapters.exceptions;

import org.spirahldev.kelenFila.common.helpers.AppResponse;
import org.spirahldev.kelenFila.domain.exceptions.BusinessException;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException exception) {
        Log.error(exception);

        AppResponse<?> response=new AppResponse<>(exception.getStatusCode());

        if(exception.getMessage()!=null){
            response.setStatusMessage(exception.getMessage());
        }

        if(exception.getErrors().length>0){
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(AppResponse.error(exception.getStatusCode(), exception.getErrors()))
                .build();
        }

        Response.Status status=exception.getHttpStatusCode()!=null ? exception.getHttpStatusCode() : Response.Status.BAD_REQUEST;

        return Response.status(status)
                .entity(response)
                .build();
    }
    
}
