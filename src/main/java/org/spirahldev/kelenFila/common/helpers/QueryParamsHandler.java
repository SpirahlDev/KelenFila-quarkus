package org.spirahldev.kelenFila.common.helpers;


/**
 * Gestionnaire avancé de paramètres de requête API pour Quarkus
 * Permet la recherche, le filtrage complexe, le tri et la pagination
 * des résultats de requêtes Panache.
 *
 * @param <T> Type d'entité sur laquelle s'applique la requête
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Gestionnaire avancé de paramètres de requête API pour Quarkus
 * Permet la recherche, le filtrage complexe, le tri et la pagination
 * des résultats de requêtes Panache.
 *
 * @param <T> Type d'entité sur laquelle s'applique la requête
 */
@RequestScoped
public class QueryParamsHandler<T> {

    private static final Logger LOG = Logger.getLogger(QueryParamsHandler.class);
    
    // Expression régulière pour valider les noms de champs (sécurité)
    private static final Pattern VALID_FIELD_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*$");
    
    private PanacheQuery<T> query;
    private UriInfo uriInfo;
    private final Set<String> allowedFilters;
    private final Set<String> allowedSortFields;
    private final Set<String> searchFields;
    private int defaultLimit = 10;
    private int maxLimit = 100;
    private StringBuilder whereClause = new StringBuilder();
    private Map<String, Object> parameters = new HashMap<>();
    private int paramCounter = 0;
    private boolean useCache = false;
    private String cacheKey;
    private Sort sort;
    private Class<T> entityClass;

    private PanacheRepository<T> repository;
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructeur complet avec classe d'entité
     */
    public QueryParamsHandler(
            PanacheQuery<T> query,
            UriInfo uriInfo,
            Set<String> allowedFilters,
            Set<String> allowedSortFields,
            Set<String> searchFields,
            Class<T> entityClass
    ){
        this.query = query;
        this.uriInfo = uriInfo;
        this.allowedFilters = validateFieldNames(allowedFilters != null ? allowedFilters : new HashSet<>());
        this.allowedSortFields = validateFieldNames(allowedSortFields != null ? allowedSortFields : new HashSet<>());
        this.searchFields = validateFieldNames(searchFields != null ? searchFields : Set.of("title", "description"));
        this.entityClass = entityClass;
        
        // Récupérer et configurer le tri immédiatement
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String sortField = queryParams.getFirst("sort");
        String sortOrder = queryParams.getFirst("order");
        
        if (sortField != null && !sortField.trim().isEmpty() && !allowedSortFields.isEmpty()) {
            this.sort = getSort(sortField, sortOrder, this.allowedSortFields);
        }

        System.out.println(objectMapper);
    }

    
    public QueryParamsHandler(
            PanacheRepository<T> repository,
            UriInfo uriInfo,
            Set<String> allowedFilters,
            Set<String> allowedSortFields,
            Set<String> searchFields,
            Class<T> entityClass
    ){
        this.repository=repository;
        this.uriInfo = uriInfo;
        this.allowedFilters = validateFieldNames(allowedFilters != null ? allowedFilters : new HashSet<>());
        this.allowedSortFields = validateFieldNames(allowedSortFields != null ? allowedSortFields : new HashSet<>());
        this.searchFields = validateFieldNames(searchFields != null ? searchFields : Set.of("title", "description"));
        this.entityClass = entityClass;
        
        // Récupérer et configurer le tri immédiatement
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String sortField = queryParams.getFirst("sort");
        String sortOrder = queryParams.getFirst("order");
        
        if (sortField != null && !sortField.trim().isEmpty() && !allowedSortFields.isEmpty()) {
            this.sort = getSort(sortField, sortOrder, this.allowedSortFields);
        }

        System.out.println(objectMapper);
    }
    
    /**
     * Constructeur simplifié
     */
    public QueryParamsHandler(
            PanacheQuery<T> query,
            UriInfo uriInfo,
            Set<String> allowedFilters,
            Class<T> entityClass) {
        this(query, uriInfo, allowedFilters, new HashSet<>(), Set.of("title", "description"), entityClass);
    }

    public QueryParamsHandler(
            PanacheRepository<T> repository,
            UriInfo uriInfo,
            Set<String> allowedFilters,
            Class<T> entityClass) {
        
        this(repository, uriInfo, allowedFilters, new HashSet<>(), Set.of("title", "description"), entityClass);
            
    }

    /**
     * Vérifie que les noms de champs sont sécurisés
     */
    private Set<String> validateFieldNames(Set<String> fieldNames) {
        return fieldNames.stream()
                .filter(field -> {
                    boolean isValid = VALID_FIELD_PATTERN.matcher(field).matches();
                    if (!isValid) {
                        LOG.warn("Nom de champ invalide ignoré: " + field);
                    }
                    return isValid;
                })
                .collect(Collectors.toSet());
    }

    /**
     * Définit la limite par défaut pour la pagination
     */
    public QueryParamsHandler<T> withDefaultLimit(int defaultLimit) {
        this.defaultLimit = defaultLimit;
        return this;
    }

    /**
     * Définit la limite maximale pour la pagination
     */
    public QueryParamsHandler<T> withMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
        return this;
    }

    /**
     * Active la mise en cache des résultats
     */
    public QueryParamsHandler<T> withCache(String cacheKey) {
        this.useCache = true;
        this.cacheKey = cacheKey;
        return this;
    }

    public QueryParamsHandler<T> withRepository(PanacheRepository<T> repository){
        this.repository=repository;
        return this;
    }

    /**
     * Méthode utilitaire statique pour obtenir un objet Sort
     */
    public static Sort getSort(String sortField, String sortOrder, Set<String> allowedSortFields) {
        if (sortField == null || sortField.trim().isEmpty()) {
            sortField = "createdAt";
        }

        // Vérifier si le champ de tri est autorisé
        if (!allowedSortFields.isEmpty() && !allowedSortFields.contains(sortField)) {
            LOG.warn("Tentative de tri (sort) sur un champ non autorisé: " + sortField);
            sortField = "createdAt"; // Revenir au champ par défaut
        }

        if (!VALID_FIELD_PATTERN.matcher(sortField).matches()) {
            LOG.warn("Nom de champ de tri invalide: " + sortField);
            sortField = "createdAt"; // Revenir au champ par défaut
        }

        // Créer directement l'objet Sort avec la bonne direction
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return Sort.ascending(sortField);
        } else {
            return Sort.descending(sortField);
        }
    }

    /**
     * Applique tous les paramètres de requête à la requête
     */
    public QueryParamsHandler<T> handle() {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        try {
            // Recherche
            if (queryParams.containsKey("search") && !searchFields.isEmpty()) {
                applySearch(queryParams.getFirst("search"));
            }

            // Filtres
            if (queryParams.containsKey("filter")) {
                applyFilters(queryParams.getFirst("filter"));
            }

            // Filtrage par plage de dates
            if (queryParams.containsKey("from") || queryParams.containsKey("to")) {
                String dateField = queryParams.getFirst("dateField");
                applyDateRange(
                    dateField != null ? dateField : "createdAt",
                    queryParams.getFirst("from"),
                    queryParams.getFirst("to")
                );
            }

            // Filtres OR
            if (queryParams.containsKey("orFilter")) {
                applyOrFilters(queryParams.getFirst("orFilter"));
            }

        } catch (Exception e) {
            LOG.error("Erreur lors du traitement des paramètres de requête", e);
            throw new BadRequestException("Erreur lors du traitement des paramètres de requête: " + e.getMessage(), e);
        }

        return this;
    }

    /**
     * Applique une recherche sur les champs configurés
     */
    protected void applySearch(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty() || searchFields.isEmpty()) {
            return;
        }

        // Ajouter AND si la clause WHERE existe déjà
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }

        whereClause.append("(");
        boolean isFirst = true;

        for (String field : searchFields) {
            if (!isFirst) {
                whereClause.append(" OR ");
            }
            isFirst = false;

            String paramName = "searchParam" + (++paramCounter);
            whereClause.append(field).append(" LIKE :").append(paramName);
            parameters.put(paramName, "%" + searchTerm + "%");
        }

        whereClause.append(")");
    }

    /**
     * Applique des filtres complexes selon les paramètres de requête
     */
    protected void applyFilters(String filtersJson) throws JsonProcessingException {
        if (allowedFilters.isEmpty() || filtersJson == null || filtersJson.trim().isEmpty()) {
            return;
        }

        List<FilterCondition> filters = parseFilters(filtersJson);

        if (filters.isEmpty()) return;

        // Ajouter AND si la clause WHERE existe déjà
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }

        whereClause.append("(");
        boolean isFirst = true;

        for (FilterCondition filter : filters) {
            String field = filter.getField();
            
            // Vérifier si le champ est autorisé
            if (!allowedFilters.contains(field)) {
                LOG.warn("Tentative d'utilisation d'un champ de filtre non autorisé: " + field);
                continue;
            }

            if (!isFirst) {
                whereClause.append(" AND ");
            }
            isFirst = false;

            String paramName = "filterParam" + (++paramCounter);
            Object value = convertValueToTargetType(filter.getValue(), filter.getDataType());

            switch (filter.getOperator()) {
                case "eq":
                    whereClause.append(field).append(" = :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "ne":
                    whereClause.append(field).append(" != :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "gt":
                    whereClause.append(field).append(" > :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "gte":
                    whereClause.append(field).append(" >= :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "lt":
                    whereClause.append(field).append(" < :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "lte":
                    whereClause.append(field).append(" <= :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "like":
                    whereClause.append(field).append(" LIKE :").append(paramName);
                    parameters.put(paramName, "%" + value + "%");
                    break;
                case "in":
                    if (value instanceof List) {
                        whereClause.append(field).append(" IN :").append(paramName);
                        parameters.put(paramName, value);
                    } else {
                        throw new BadRequestException("L'opérateur 'in' nécessite une liste de valeurs");
                    }
                    break;
                case "isNull":
                    whereClause.append(field).append(" IS NULL");
                    break;
                case "isNotNull":
                    whereClause.append(field).append(" IS NOT NULL");
                    break;
                default:
                    throw new BadRequestException("Opérateur non supporté: " + filter.getOperator());
            }
        }

        whereClause.append(")");
    }

    /**
     * Applique des filtres avec l'opérateur OR
     */
    protected void applyOrFilters(String filtersJson) throws JsonProcessingException {
        if (allowedFilters.isEmpty() || filtersJson == null || filtersJson.trim().isEmpty()) {
            return;
        }

        List<FilterCondition> filters = parseFilters(filtersJson);

        if (filters.isEmpty()) return;

        // Filtrer pour ne garder que les champs autorisés
        filters = filters.stream()
                .filter(filter -> allowedFilters.contains(filter.getField()))
                .collect(java.util.stream.Collectors.toList());

        if (filters.isEmpty()) return;

        // Ajouter AND si la clause WHERE existe déjà
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }

        whereClause.append("(");
        boolean isFirst = true;

        for (FilterCondition filter : filters) {
            if (!isFirst) {
                whereClause.append(" OR ");
            }
            isFirst = false;

            String field = filter.getField();
            String paramName = "orFilterParam" + (++paramCounter);
            Object value = convertValueToTargetType(filter.getValue(), filter.getDataType());

            switch (filter.getOperator()) {
                case "eq":
                    whereClause.append(field).append(" = :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "ne":
                    whereClause.append(field).append(" != :").append(paramName);
                    parameters.put(paramName, value);
                    break;
                case "like":
                    whereClause.append(field).append(" LIKE :").append(paramName);
                    parameters.put(paramName, "%" + value + "%");
                    break;
                // Autres opérateurs supportés...
                default:
                    whereClause.append(field).append(" = :").append(paramName);
                    parameters.put(paramName, value);
                    break;
            }
        }

        whereClause.append(")");
    }

    /**
     * Applique un filtrage par plage de dates sur le champ spécifié
    */
    protected void applyDateRange(String dateField, String from, String to) {
        
        if (!VALID_FIELD_PATTERN.matcher(dateField).matches()) {
            LOG.warn("Nom de champ de date invalide: " + dateField);
            return;
        }

        try {
            if (from != null && !from.trim().isEmpty()) {
                LocalDate fromDate = parseDate(from);
                
                // Ajouter AND si la clause WHERE existe déjà
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                
                String paramName = "fromDate" + (++paramCounter);
                whereClause.append("date(").append(dateField).append(") >= :").append(paramName);
                parameters.put(paramName, fromDate);
            }

            if (to != null && !to.trim().isEmpty()) {
                LocalDate toDate = parseDate(to);
                
                // Ajouter AND si la clause WHERE existe déjà
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                
                String paramName = "toDate" + (++paramCounter);
                whereClause.append("date(").append(dateField).append(") <= :").append(paramName);
                parameters.put(paramName, toDate);
            }
        } catch (DateTimeParseException e) {
            LOG.error("Erreur de format de date", e);
            throw new BadRequestException("Format de date invalide. Utilisez YYYY-MM-DD", e);
        }
    }

    /**
     * Pagine les résultats de la requête avec la nouvelle construction de requête
     */
    @CacheResult(cacheName = "query-results")
    public List<T> paginate() {
        if (useCache) {
            return paginateInternal();
        } else {
            return paginateInternal();
        }
    }

    /**
     * Méthode interne pour la pagination avec construction de requête JPQL
     */
    private List<T> paginateInternal() {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        // Calcul de la pagination
        int limit = Math.min(
            parseIntParam(queryParams.getFirst("limit"), defaultLimit),
            maxLimit
        );
        int page = Math.max(1, parseIntParam(queryParams.getFirst("page"), 1));
        
        // Si 'all' est présent, retourner tous les résultats
        boolean returnAll = queryParams.containsKey("all");
        
        // Reconstruire complètement la requête si nécessaire
        if (whereClause.length() > 0) {
            // Utiliser l'API find directement avec notre clause WHERE
            String jpql = whereClause.toString();
            
            PanacheQuery<T> newQuery;
            
            try {
                // Construire la requête avec les bons paramètres
                Parameters queryParams1 = new Parameters();
                parameters.forEach(queryParams1::and);
                
                if (sort != null) {
                    // Avec tri
                    newQuery = repository.find(jpql, sort, queryParams1);
                } else {
                    // Sans tri
                    newQuery = repository.find(jpql, queryParams1);
                }
            } catch (Exception e) {
                LOG.error("Erreur lors de la construction de la requête: " + e.getMessage(), e);
                throw new BadRequestException("Erreur lors de la construction de la requête", e);
            }
            
            // Appliquer la pagination
            if (returnAll) {
                return newQuery.list();
            } else {
                return newQuery.page(Page.of(page - 1, limit)).list();
            }
        } else {
            // Utiliser la requête originale
            if (returnAll) {
                return query.list();
            } else {
                return query.page(Page.of(page - 1, limit)).list();
            }
        }
    }

    /**
     * Récupère le nombre total d'enregistrements pour la requête actuelle
     */
    public long count() {
        // Reconstruire la requête count si nécessaire
        if (whereClause.length() > 0) {
            String jpql = whereClause.toString();
            
            try {
                // Construire la requête avec les bons paramètres
                Parameters queryParams = new Parameters();
                parameters.forEach(queryParams::and);
                
                return repository.count(jpql, queryParams);
            } catch (Exception e) {
                LOG.error("Erreur lors de la construction de la requête count: " + e.getMessage(), e);
                throw new BadRequestException("Erreur lors de la construction de la requête count", e);
            }
        } else {
            // Utiliser la requête originale
            return query.count();
        }
    }
    
    /**
     * Obtient l'information de pagination pour la réponse API
     */
    public PaginationInfo getPaginationInfo() {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        
        int page = Math.max(1, parseIntParam(queryParams.getFirst("page"), 1));
        int limit = Math.min(
            parseIntParam(queryParams.getFirst("limit"), defaultLimit),
            maxLimit
        );
        long total = count();
        
        String baseUrl = buildBaseUrl();
        
        return new PaginationInfo(total, page, limit, baseUrl);
    }

    /**
     * Parse une chaîne en entier avec valeur par défaut
     */
    private int parseIntParam(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parse une chaîne de date avec différents formats possibles
     */
    private LocalDate parseDate(String dateStr) throws DateTimeParseException {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(dateStr.replace("/", "-"));
            } catch (DateTimeParseException e2) {
                // Format avec jour en premier
                String[] parts = dateStr.split("[-/]");
                if (parts.length == 3) {
                    return LocalDate.of(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[0])
                    );
                }
                throw e2;
            }
        }
    }

    /**
     * Parse le JSON de filtres en liste de conditions
     */
    private List<FilterCondition> parseFilters(String json) throws JsonProcessingException {
        if (json == null || json.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            // Essayer d'abord comme un tableau de FilterCondition
            return objectMapper.readValue(json, new TypeReference<List<FilterCondition>>() {});
        } catch (JsonProcessingException e) {
            try {
                // Essayer comme un objet simple
                Map<String, Object> simpleFilters = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
                
                // Convertir en liste de FilterCondition avec opérateur par défaut "eq"
                return simpleFilters.entrySet().stream()
                        .map(entry -> new FilterCondition(entry.getKey(), "eq", entry.getValue(), 
                            guessDataType(entry.getValue())))
                        .collect(java.util.stream.Collectors.toList());
            } catch (JsonProcessingException e2) {
                LOG.error("Erreur de parsing JSON", e);
                throw new BadRequestException("Format de filtre invalide", e);
            }
        }
    }
    
    /**
     * Essaie de deviner le type de données d'une valeur
     */
    private String guessDataType(Object value) {
        if (value == null) return "string";
        
        if (value instanceof Number) {
            if (value instanceof Integer || value instanceof Long) {
                return "integer";
            } else {
                return "double";
            }
        } else if (value instanceof Boolean) {
            return "boolean";
        } else if (value instanceof String) {
            // Essayer de détecter une date
            String str = (String) value;
            if (str.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return "date";
            }
        }
        
        return "string";
    }

    /**
     * Convertit une valeur au type cible
     */
    private Object convertValueToTargetType(Object value, String dataType) {
        if (value == null) return null;
        
        try {
            if (dataType == null) {
                dataType = "string";
            }
            
            switch (dataType.toLowerCase()) {
                case "int":
                case "integer":
                    if (value instanceof String) {
                        return Integer.parseInt((String) value);
                    } else if (value instanceof Number) {
                        return ((Number) value).intValue();
                    }
                    break;
                case "long":
                    if (value instanceof String) {
                        return Long.parseLong((String) value);
                    } else if (value instanceof Number) {
                        return ((Number) value).longValue();
                    }
                    break;
                case "double":
                case "float":
                    if (value instanceof String) {
                        return Double.parseDouble((String) value);
                    } else if (value instanceof Number) {
                        return ((Number) value).doubleValue();
                    }
                    break;
                case "boolean":
                case "bool":
                    if (value instanceof String) {
                        return Boolean.parseBoolean((String) value);
                    }
                    break;
                case "date":
                    if (value instanceof String) {
                        return parseDate((String) value);
                    }
                    break;
                case "datetime":
                    if (value instanceof String) {
                        return java.time.LocalDateTime.parse((String) value);
                    }
                    break;
                case "list":
                case "array":
                    if (value instanceof String) {
                        return objectMapper.readValue((String) value, List.class);
                    }
                    break;
            }
            return value;
        } catch (Exception e) {
            LOG.error("Erreur de conversion de type", e);
            throw new BadRequestException("Erreur de conversion de type pour " + value + " vers " + dataType, e);
        }
    }

    /**
     * Construit l'URL de base pour la pagination
     */
    private String buildBaseUrl() {
        StringBuilder url = new StringBuilder(uriInfo.getPath());
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        boolean first = true;
        
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            if (!"page".equals(key) && !"limit".equals(key)) {
                for (String value : entry.getValue()) {
                    if (first) {
                        url.append("?");
                        first = false;
                    } else {
                        url.append("&");
                    }
                    url.append(key).append("=").append(value);
                }
            }
        }
        
        return url.toString();
    }

    /**
     * Classe interne pour représenter une condition de filtre
     */
    public static class FilterCondition {
        private String field;
        private String operator;
        private Object value;
        private String dataType;

        public FilterCondition() {
        }

        public FilterCondition(String field, String operator, Object value, String dataType) {
            this.field = field;
            this.operator = operator;
            this.value = value;
            this.dataType = dataType;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
    }
}