package org.spirahldev.kelenFila.domain.constants;

public enum DomainStatusCode {

    PASSWORD_MISSING_LOWERCASE("PASSWORD_MISSING_LOWERCASE", "Le mot de passe ne contient pas de minuscule"),
    PASSWORD_MISSING_UPPERCASE("PASSWORD_MISSING_UPPERCASE", "Le mot de passe ne contient pas de majuscule"),
    PASSWORD_MISSING_SPECIAL_CHAR("PASSWORD_MISSING_SPECIAL_CHAR", "Le mot de passe ne contient pas de caractère spécial"),
    PASSWORD_MISSING_DIGIT("PASSWORD_MISSING_DIGIT", "Le mot de passe ne contient pas de chiffre"),
    MISSING_BIRTHDATE("MISSING_BIRTHDATE", "Date d'anniversaire requise"),
    UNDERAGE_PERSON("UNDERAGE_PERSON", "La personne n'a pas l'âge requis."),
    PASSWORD_TOO_SHORT("PASSWORD_TOO_SHORT", "Le mot de passe est trop court");

    private final String code;
    private final String message;

    DomainStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static DomainStatusCode fromCode(String code) {
        for (DomainStatusCode status : DomainStatusCode.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Code de statut inconnu: " + code);
    }
}
