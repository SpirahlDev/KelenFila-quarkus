package org.spirahldev.kelenFila.adapters.exceptions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;
import org.spirahldev.kelenFila.common.helpers.ValidationError;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Gestionnaire d'exceptions pour les erreurs de validation des champs
 * Intercepte les ConstraintViolationException et les transforme en réponse HTTP
 */
@Provider
public class FieldValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    /**
     * Convertit une exception de validation en réponse HTTP
     * @param exception L'exception de violation de contraintes à traiter
     * @return Response Une réponse HTTP contenant les détails des erreurs
     */
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Récupération de l'ensemble des violations
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        
        // Transformation des violations en liste d'erreurs de validation
        List<ValidationError> errors = violations.stream()
            .map(violation -> {
                // Extraction du nom du champ depuis le chemin de la propriété
                String[] pathParts = violation.getPropertyPath().toString().split("\\.");
                // Récupération du dernier élément du chemin comme nom du champ
                String fieldName = pathParts.length > 0 ? pathParts[pathParts.length - 1] : "";
                
                // Création d'une nouvelle erreur de validation
                return new ValidationError(
                    fieldName,           // Nom du champ en erreur
                    violation.getMessage() // Message d'erreur associé
                );
            })
            .collect(Collectors.toList());
        
        // Construction de la réponse HTTP
        return Response
            .status(Response.Status.BAD_REQUEST)          // Code HTTP 400
            .entity(AppResponse.error(                    // Corps de la réponse
                AppStatusCode.VALIDATION_ERROR,           // Code d'erreur application
                errors                                    // Liste des erreurs
            ))
            .build();
    }
}
