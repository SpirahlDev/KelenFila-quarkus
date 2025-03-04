package org.spirahldev.kelenFila.domain.exceptions;

import org.spirahldev.kelenFila.common.constants.AppStatusCode;

import jakarta.ws.rs.core.Response;

public class BusinessException extends RuntimeException {
    private final AppStatusCode statusCode;
    private final String resource;
    private Response.Status httpStatusCode;
    private Object[] errors;


    public BusinessException(AppStatusCode statusCode, String message, String resource) {
        super(message);
        this.statusCode = statusCode;
        this.resource = resource;
    }

    public BusinessException(AppStatusCode statusCode, Response.Status httpStatusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
        resource=null;
    }

    public BusinessException(AppStatusCode statusCode, String message, String resource, Response.Status httpStatusCode) {
        super(message);
        this.statusCode = statusCode;
        this.resource = resource;
        this.httpStatusCode = httpStatusCode;
    }

    public AppStatusCode getStatusCode() {
        return statusCode;
    }

    public String getResource() {
        return resource;
    }

    public Response.Status getHttpStatusCode() {
        return httpStatusCode;
    }
    public BusinessException setHttpStatusCode(Response.Status httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public Object[] getErrors() {
        return errors;
    }

    public BusinessException setErrors(Object[] errors) {
        this.errors = errors;
        return this;
    }
}
