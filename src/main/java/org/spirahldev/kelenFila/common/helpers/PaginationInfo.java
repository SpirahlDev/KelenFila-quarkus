package org.spirahldev.kelenFila.common.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Classe représentant les informations de pagination pour les réponses API
 */
public class PaginationInfo {
    private final long total;
    private final int count;
    private final int perPage;
    private final int currentPage;
    private final int lastPage;
    private final PaginationLinks links;

    /**
     * Constructeur complet
     */
    public PaginationInfo(long total, int count, int perPage, int currentPage, int lastPage, PaginationLinks links) {
        this.total = total;
        this.count = count;
        this.perPage = perPage;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.links = links;
    }

    /**
     * Constructeur simplifié qui calcule automatiquement les valeurs dérivées
     */
    public PaginationInfo(long total, int currentPage, int perPage, String baseUrl) {
        this.total = total;
        this.perPage = perPage;
        this.currentPage = Math.max(1, currentPage);
        this.lastPage = calculateLastPage(total, perPage);
        
        // Calculer le nombre d'éléments sur la page courante
        this.count = calculateCount(total, currentPage, perPage);
        
        // Créer les liens de pagination
        this.links = new PaginationLinks(baseUrl, currentPage, lastPage, perPage);
    }

    /**
     * Calcule le nombre d'éléments sur la page courante
     */
    private int calculateCount(long total, int currentPage, int perPage) {
        if (currentPage == lastPage) {
            return (int) (total - (currentPage - 1) * perPage);
        }
        return Math.min(perPage, (int) total);
    }

    /**
     * Calcule le numéro de la dernière page
     */
    private int calculateLastPage(long total, int perPage) {
        return (int) Math.ceil((double) total / perPage);
    }

    /**
     * Convertit l'objet en Map pour la sérialisation
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("count", count);
        map.put("per_page", perPage);
        map.put("current_page", currentPage);
        map.put("last_page", lastPage);
        map.put("links", links.toMap());
        return map;
    }

    /**
     * Vérifie si la page courante est la première page
     */
    public boolean isFirstPage() {
        return currentPage == 1;
    }

    /**
     * Vérifie si la page courante est la dernière page
     */
    public boolean isLastPage() {
        return currentPage >= lastPage;
    }

    /**
     * Vérifie s'il y a une page suivante
     */
    public boolean hasNextPage() {
        return currentPage < lastPage;
    }

    /**
     * Vérifie s'il y a une page précédente
     */
    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    /**
     * Calcule le numéro de la page suivante
     */
    public int getNextPage() {
        return hasNextPage() ? currentPage + 1 : currentPage;
    }

    /**
     * Calcule le numéro de la page précédente
     */
    public int getPreviousPage() {
        return hasPreviousPage() ? currentPage - 1 : 1;
    }

    /**
     * Calcule l'offset pour les requêtes SQL (OFFSET clause)
     */
    public int getOffset() {
        return (currentPage - 1) * perPage;
    }

    /**
     * Récupère le numéro du premier élément sur la page courante
     */
    public long getFrom() {
        if (total == 0) {
            return 0;
        }
        return ((currentPage - 1) * perPage) + 1;
    }

    /**
     * Récupère le numéro du dernier élément sur la page courante
     */
    public long getTo() {
        if (total == 0) {
            return 0;
        }
        return Math.min(getFrom() + count - 1, total);
    }

    // Getters
    public long getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public PaginationLinks getLinks() {
        return links;
    }

    /**
     * Classe interne représentant les liens de navigation entre pages
     */
    public static class PaginationLinks {
        private final String first;
        private final String last;
        private final String prev;
        private final String next;

        public PaginationLinks(String baseUrl, int currentPage, int lastPage, int perPage) {
            this.first = buildPageUrl(baseUrl, 1, perPage);
            this.last = buildPageUrl(baseUrl, lastPage, perPage);
            this.prev = currentPage > 1 ? buildPageUrl(baseUrl, currentPage - 1, perPage) : null;
            this.next = currentPage < lastPage ? buildPageUrl(baseUrl, currentPage + 1, perPage) : null;
        }

        private String buildPageUrl(String baseUrl, int page, int limit) {
            String connector = baseUrl.contains("?") ? "&" : "?";
            return baseUrl + connector + "page=" + page + "&limit=" + limit;
        }

        public Map<String, String> toMap() {
            Map<String, String> map = new HashMap<>();
            map.put("first", first);
            map.put("last", last);
            
            if (prev != null) {
                map.put("prev", prev);
            }
            
            if (next != null) {
                map.put("next", next);
            }
            
            return map;
        }

        // Getters
        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        public Optional<String> getPrev() {
            return Optional.ofNullable(prev);
        }

        public Optional<String> getNext() {
            return Optional.ofNullable(next);
        }
    }
}