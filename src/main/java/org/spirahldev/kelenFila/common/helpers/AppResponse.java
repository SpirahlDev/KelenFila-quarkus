package org.spirahldev.kelenFila.common.helpers;

// Imports nécessaires
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import jakarta.validation.ConstraintViolation;

/**
 * Classe générique pour encapsuler les réponses de l'API
 * @param <T> Type de données à retourner
 */
public class AppResponse<T> {
    // Attributs pour stocker le statut et les données de la réponse
    private int status_code;         // Code de statut de la réponse
    private String status_message;   // Message associé au statut
    private T data;                 // Données de la réponse

    /** Constructeur par défaut */
    public AppResponse() {
    }


    /**
     * Constructeur avec statut et données
     * @param statusCode Code de statut prédéfini
     * @param data Données à retourner
     */
    public AppResponse(AppStatusCode statusCode, T data) {
        this.status_code = statusCode.getCode();
        this.status_message = statusCode.getMessage();
        this.data = data;
    }

    public AppResponse(AppStatusCode statusCode) {
        this.status_code = statusCode.getCode();
        if(this.status_message == null){
            this.status_message = statusCode.getMessage();
        }
    }

    // Getters et Setters
    public int getStatusCode() {
        return status_code;
    }

    public void setStatusCode(AppStatusCode status_code) {
        this.status_code = status_code.getCode();
    }

    public String getStatusMessage() {
        return status_message;
    }

    public void setStatusMessage(String status_message) {
        this.status_message = status_message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * Définit la réponse comme un succès
     * @param data Données à retourner
     */
    public void success(T data) {
        this.status_code = AppStatusCode.SUCCESS_OPERATION.getCode();
        this.status_message = AppStatusCode.SUCCESS_OPERATION.getMessage();
        this.data = data;
    }

    /**
     * Crée directement une réponse d'erreur
     * @param statusCode Code de statut d'erreur
     * @param errors Détails de l'erreur
     * @return Map contenant les détails de l'erreur
     */
    public static Map<String,Object> error(AppStatusCode statusCode, Object errors) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("errors", errors);
        responseMap.put("statusCode", statusCode.getCode());
        responseMap.put("statusMessage", statusCode.getMessage());
        return responseMap;
    }

    public static Map<String,Object> sendTokken(String token) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("token", token);
        responseMap.put("statusCode", AppStatusCode.SUCCESS_OPERATION);
        return responseMap;
    }

    

    /**
     * Crée une réponse pour les erreurs de validation
     * @param violations Ensemble des violations de contraintes
     * @return AppResponse contenant la liste des erreurs de validation
     */
    public static AppResponse<List<ValidationError>> validationError(Set<ConstraintViolation<?>> violations) {
        List<ValidationError> errors = violations.stream()
            .map(violation -> new ValidationError(
                violation.getPropertyPath().toString(),
                violation.getMessage()
            ))
            .toList();
        
        return new AppResponse<>(AppStatusCode.VALIDATION_ERROR, errors);
    }
}
