package org.spirahldev.kelenFila.common.constants;

public enum AppStatusCode {

    // Groupe de codes de succès
    SUCCESS_OPERATION(7000, "Opération réussie"),
    
    // Groupe de codes d'érreurs provénant du client 
    OPERATION_FAILED(8000, "Opération échouée."),
    VALIDATION_ERROR(8001, "Erreur de validation."),
    RESSOURCE_ALREADY_EXIST(8002, "La ressource existe déja."),
    USER_ALREADY_EXIST(8003, "Cet utilisateur existe déja."),
    USER_NOT_FOUND(8004, "Utilisateur introuvable."),
    INVALID_CREDENTIALS(8005, "Login ou mot de passe incorrecte."),
    INVALID_PASSWORD(8100, "Le mot de passe n'est pas valable."),
    
    // Groupe de codes d'érreurs provénant du serveur
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
