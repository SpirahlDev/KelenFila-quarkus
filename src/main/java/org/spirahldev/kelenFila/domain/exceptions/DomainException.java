package org.spirahldev.kelenFila.domain.exceptions;

import org.spirahldev.kelenFila.domain.constants.DomainStatusCode;

// domain/exceptions/DomainException.java
public class DomainException extends RuntimeException {
    private final String code;
    private final String message;
    private final transient Object[] parameters;

    // Constructeur simple
    public DomainException(String message) {
        super(message);
        this.code = "DOMAIN_ERROR";
        this.message = message;
        this.parameters = new Object[]{};
    }

    public DomainException(DomainStatusCode domainStatusCode){
        this.code = domainStatusCode.getCode();
        this.message = domainStatusCode.getMessage();
        this.parameters = new Object[]{};
    }

    // Constructeur avec code d'erreur
    public DomainException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.parameters = new Object[]{};
    }

    // Constructeur complet
    public DomainException(String code, String message, Object... parameters) {
        super(message);
        this.code = code;
        this.message = message;
        this.parameters = parameters;
    }

    // Getters
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object[] getParameters() {
        return parameters;
    }
}