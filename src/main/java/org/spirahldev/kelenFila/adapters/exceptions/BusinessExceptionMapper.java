package org.spirahldev.kelenFila.adapters.exceptions;

import org.spirahldev.kelenFila.common.helpers.AppResponse;
import org.spirahldev.kelenFila.domain.exceptions.BusinessException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException exception) {
        AppResponse<?> response=new AppResponse<>(exception.getStatusCode());

        if(exception.getMessage()!=null){
            response.setStatusMessage(exception.getMessage());
        }

        Response.Status status=exception.getHttpStatusCode()!=null ? exception.getHttpStatusCode() : Response.Status.BAD_REQUEST;

        return Response.status(status)
                .entity(response)
                .build();
    }
    
}
