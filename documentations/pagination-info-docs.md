# Documentation de PaginationInfo

## Sommaire

1. [Introduction](#introduction)
2. [Structure de la Classe](#structure-de-la-classe)
3. [Constructeurs](#constructeurs)
4. [Méthodes Principales](#méthodes-principales)
5. [Classe PaginationLinks](#classe-paginationlinks)
6. [Exemples d'Utilisation](#exemples-dutilisation)
   - [Création Simple](#création-simple)
   - [Utilisation dans un Contrôleur](#utilisation-dans-un-contrôleur)
   - [Utilisation Avancée](#utilisation-avancée)
7. [Intégration avec QueryParamsHandler](#intégration-avec-queryparamshandler)
8. [Avantages](#avantages)
9. [Bonnes Pratiques](#bonnes-pratiques)

## Introduction

La classe `PaginationInfo` est une structure de données fortement typée qui encapsule toutes les informations relatives à la pagination d'une collection de résultats. Elle est conçue pour standardiser et simplifier la gestion de la pagination dans les APIs REST, en fournissant une API riche et intuitive.

## Structure de la Classe

```java
/**
 * Classe représentant les informations de pagination pour les réponses API
 */
public class PaginationInfo {
    // Attributs principaux
    private final long total;        // Nombre total d'éléments
    private final int count;         // Nombre d'éléments sur la page courante
    private final int perPage;       // Nombre d'éléments par page
    private final int currentPage;   // Numéro de la page courante
    private final int lastPage;      // Numéro de la dernière page
    private final PaginationLinks links; // Liens de navigation
    
    // Méthodes...
}
```

## Constructeurs

La classe offre deux constructeurs principaux :

```java
/**
 * Constructeur complet permettant de spécifier toutes les valeurs
 */
public PaginationInfo(long total, int count, int perPage, 
                     int currentPage, int lastPage, PaginationLinks links)

/**
 * Constructeur simplifié qui calcule automatiquement les valeurs dérivées
 */
public PaginationInfo(long total, int currentPage, int perPage, String baseUrl)
```

Le second constructeur est particulièrement utile car il calcule automatiquement plusieurs valeurs :
- `count` : le nombre d'éléments sur la page courante
- `lastPage` : le numéro de la dernière page
- `links` : les liens de navigation (premier, dernier, précédent, suivant)

## Méthodes Principales

```java
/**
 * Vérifie si la page courante est la première page
 */
public boolean isFirstPage()

/**
 * Vérifie si la page courante est la dernière page
 */
public boolean isLastPage()

/**
 * Vérifie s'il y a une page suivante
 */
public boolean hasNextPage()

/**
 * Vérifie s'il y a une page précédente
 */
public boolean hasPreviousPage()

/**
 * Retourne le numéro de la page suivante, ou la page courante s'il n'y en a pas
 */
public int getNextPage()

/**
 * Retourne le numéro de la page précédente, ou 1 s'il n'y en a pas
 */
public int getPreviousPage()

/**
 * Calcule l'offset pour les requêtes SQL (OFFSET clause)
 */
public int getOffset()

/**
 * Retourne l'index du premier élément sur la page courante (base 1)
 */
public long getFrom()

/**
 * Retourne l'index du dernier élément sur la page courante
 */
public long getTo()

/**
 * Convertit l'objet en Map pour la sérialisation JSON
 */
public Map<String, Object> toMap()
```

## Classe PaginationLinks

La classe interne `PaginationLinks` gère les liens de navigation entre les pages :

```java
public static class PaginationLinks {
    private final String first;  // Lien vers la première page
    private final String last;   // Lien vers la dernière page
    private final String prev;   // Lien vers la page précédente (null si première page)
    private final String next;   // Lien vers la page suivante (null si dernière page)
    
    /**
     * Constructeur qui génère automatiquement les liens
     */
    public PaginationLinks(String baseUrl, int currentPage, int lastPage, int perPage)
    
    /**
     * Convertit l'objet en Map pour la sérialisation JSON
     */
    public Map<String, String> toMap()
    
    // Getters avec Optional pour les liens qui peuvent être null
    public String getFirst()
    public String getLast()
    public Optional<String> getPrev()
    public Optional<String> getNext()
}
```

## Exemples d'Utilisation

### Création Simple

```java
// Création de base
PaginationInfo pagination = new PaginationInfo(
    100,    // total d'éléments
    2,      // page courante
    15,     // éléments par page
    "/api/articles"  // URL de base
);
```

### Utilisation dans un Contrôleur

```java
@GET
@Path("/articles")
public Response getArticles(@QueryParam("page") @DefaultValue("1") int page,
                           @QueryParam("limit") @DefaultValue("10") int limit) {
    // Exécuter la requête avec pagination
    long total = Article.count();
    List<Article> articles = Article.findAll()
                              .page(page - 1, limit)
                              .list();
    
    // Créer l'objet de pagination
    PaginationInfo pagination = new PaginationInfo(
        total, page, limit, uriInfo.getPath()
    );
    
    // Vérifier s'il y a plus de pages
    boolean hasMorePages = pagination.hasNextPage();
    
    // Construire la réponse
    return Response.ok(Map.of(
        "data", articles,
        "pagination", pagination.toMap(),
        "has_more", hasMorePages
    )).build();
}
```

### Utilisation Avancée

Vous pourriez étendre la classe avec un Builder pattern pour plus de flexibilité :

```java
// Exemple de Builder pattern qui pourrait être implémenté
PaginationInfo pagination = new PaginationInfo.Builder()
    .total(100)
    .currentPage(2)
    .perPage(15)
    .baseUrl("/api/articles")
    .additionalParams(Map.of("category", "3", "sort", "title"))
    .build();
```

## Intégration avec QueryParamsHandler

La classe `PaginationInfo` s'intègre parfaitement avec `QueryParamsHandler` :

```java
@GET
public Response getArticles(@Context UriInfo uriInfo) {
    // Configuration du handler
    QueryParamsHandler<Article> handler = new QueryParamsHandler<>(
        Article.findAll(sort), 
        uriInfo, 
        allowedFilters
    ).handle();
    
    // Récupération des résultats et de la pagination
    List<Article> results = handler.paginate();
    PaginationInfo pagination = handler.getPaginationInfo();
    
    // Création de la réponse
    Map<String, Object> response = new HashMap<>();
    response.put("data", results);
    response.put("pagination", pagination.toMap()); // Conversion en Map pour JSON
    
    return Response.ok(response).build();
}
```

## Structure JSON

Voici la structure JSON générée par la méthode `toMap()` :

```json
{
  "total": 100,        // Nombre total d'éléments
  "count": 15,         // Nombre d'éléments sur la page courante
  "per_page": 15,      // Nombre d'éléments par page
  "current_page": 2,   // Numéro de la page courante
  "last_page": 7,      // Nombre total de pages
  "from": 16,          // Index du premier élément sur cette page
  "to": 30,            // Index du dernier élément sur cette page
  "links": {
    "first": "/articles?page=1&limit=15",
    "last": "/articles?page=7&limit=15",
    "prev": "/articles?page=1&limit=15",
    "next": "/articles?page=3&limit=15"
  }
}
```

## Avantages

La classe `PaginationInfo` offre plusieurs avantages par rapport à l'utilisation d'une simple Map :

1. **Type Safety** : Le compilateur vérifie les types, évitant les erreurs d'exécution.
2. **API Intuitive** : Des méthodes comme `hasNextPage()` ou `isLastPage()` facilitent la navigation.
3. **Calculs Automatiques** : Les valeurs comme `from`, `to` et `offset` sont calculées automatiquement.
4. **Réutilisabilité** : La classe peut être utilisée dans différents contextes (API, UI, exports).
5. **Extensibilité** : Vous pouvez facilement ajouter de nouvelles fonctionnalités à la classe.

## Bonnes Pratiques

1. **Préférez le constructeur simplifié** lorsque possible, car il effectue automatiquement tous les calculs pertinents.

2. **Utilisez la méthode `toMap()`** pour la sérialisation JSON afin d'assurer un format cohérent.

3. **Utilisez les méthodes d'aide** comme `hasNextPage()` plutôt que de recalculer ces valeurs.

4. **Considérez l'ajout du Builder pattern** pour des cas d'utilisation complexes.

5. **Assurez l'immutabilité** : tous les champs sont déclarés `final` pour garantir l'immutabilité.

6. **Personnalisez selon vos besoins** : Vous pouvez étendre cette classe pour ajouter des fonctionnalités spécifiques à votre application.
