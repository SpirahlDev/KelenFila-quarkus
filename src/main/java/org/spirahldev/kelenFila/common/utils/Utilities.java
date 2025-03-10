package org.spirahldev.kelenFila.common.utils;

import java.security.SecureRandom;
import java.util.Set;
import java.util.regex.Pattern;

import org.jboss.logging.Logger;
import org.spirahldev.kelenFila.common.helpers.QueryParamsHandler;

public class Utilities {
    private static final Logger LOG = Logger.getLogger(QueryParamsHandler.class);
    private static final Pattern VALID_FIELD_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*$");
    
    
    public static String generateSecureRandomLetters(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        
        return sb.toString();
    }

    public static String generateRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        String digits = "0123456789";
        
        for (int i = 0; i < length; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }
        
        return sb.toString();
    }

 
    public static boolean checkSortField(String sortField, Set<String> allowedSortFields) {
        // Vérifier si le champ n'est pas null ou vide
        if (sortField == null || sortField.trim().isEmpty()) {
            LOG.warn("Champ de tri null ou vide");
            return false;
        }
        
        // Vérifier si le champ correspond au pattern valide
        if (!VALID_FIELD_PATTERN.matcher(sortField).matches()) {
            LOG.warn("Format de champ de tri invalide: " + sortField);
            return false;
        }
        
        // Vérifier si le champ est autorisé (si des restrictions sont définies)
        if (!allowedSortFields.isEmpty() && !allowedSortFields.contains(sortField)) {
            LOG.warn("Champ de tri non autorisé: " + sortField);
            return false;
        }
        
        return true;
    }
}
