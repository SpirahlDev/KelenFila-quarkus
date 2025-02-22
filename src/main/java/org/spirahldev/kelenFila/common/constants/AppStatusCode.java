package org.spirahldev.kelenFila.common.constants;

public enum AppStatusCode {

    SUCCESS_OPERATION(7000, "Opération réussie"),
    OPERATION_FAILED(8000, "Opération échouée"),
    VALIDATION_ERROR(8001, "Erreur de validation"),
    INTERNAL_SERVER_ERROR(9000, "Une erreur critique s'est produite");

    private final int code;
    private final String message;

    AppStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static AppStatusCode fromCode(int code) {
        for (AppStatusCode status : AppStatusCode.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Code de statut inconnu: " + code);
    }
}
