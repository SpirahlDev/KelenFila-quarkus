# Documentation de QueryParamsHandler

## Sommaire

- [Documentation de QueryParamsHandler](#documentation-de-queryparamshandler)
  - [Sommaire](#sommaire)
  - [Introduction](#introduction)
  - [Fonctionnalités](#fonctionnalités)
  - [Utilisation Basique](#utilisation-basique)
    - [Configuration et Dépendances](#configuration-et-dépendances)
    - [Exemple de Base](#exemple-de-base)
  - [Configuration Avancée](#configuration-avancée)
    - [Constructeurs](#constructeurs)
      - [Constructeur Simplifié](#constructeur-simplifié)
      - [Constructeur Complet](#constructeur-complet)
    - [Méthodes de Configuration](#méthodes-de-configuration)
    - [Traitement des Paramètres](#traitement-des-paramètres)
    - [Récupération des Résultats](#récupération-des-résultats)
  - [Structure de Pagination](#structure-de-pagination)
    - [Aperçu de PaginationInfo](#aperçu-de-paginationinfo)
    - [Structure JSON générée](#structure-json-générée)
  - [Paramètres de Requête Supportés](#paramètres-de-requête-supportés)
    - [Recherche](#recherche)
      - [Exemple de Recherche](#exemple-de-recherche)
    - [Filtrage Simple](#filtrage-simple)
      - [Exemple de Filtrage Simple](#exemple-de-filtrage-simple)
    - [Filtrage Avancé](#filtrage-avancé)
      - [Exemple de Filtrage Avancé](#exemple-de-filtrage-avancé)
    - [Opérateurs de Filtre Supportés](#opérateurs-de-filtre-supportés)
    - [Types de Données Supportés](#types-de-données-supportés)
    - [Filtrage OR](#filtrage-or)
      - [Exemple de Filtrage OR](#exemple-de-filtrage-or)
    - [Filtrage par Date](#filtrage-par-date)
      - [Exemple de Filtrage par Date](#exemple-de-filtrage-par-date)
    - [Tri](#tri)
      - [Méthode getSort](#méthode-getsort)
      - [Exemple d'Application du Tri](#exemple-dapplication-du-tri)
      - [Exemple de Tri](#exemple-de-tri)
    - [Pagination](#pagination)
      - [Exemple de Pagination](#exemple-de-pagination)
  - [Structure de la Réponse](#structure-de-la-réponse)
    - [Format de Réponse Standard](#format-de-réponse-standard)
    - [Exemple d'Utilisation avec PaginationInfo](#exemple-dutilisation-avec-paginationinfo)
    - [Avantages de PaginationInfo](#avantages-de-paginationinfo)
    - [Méthodes Utiles de PaginationInfo](#méthodes-utiles-de-paginationinfo)
  - [Sécurité](#sécurité)
    - [Validation des Noms de Champs](#validation-des-noms-de-champs)
    - [Limites de Pagination](#limites-de-pagination)
  - [Mise en Cache](#mise-en-cache)
  - [Gestion des Erreurs](#gestion-des-erreurs)
  - [Cas d'Utilisation Avancés](#cas-dutilisation-avancés)
    - [Filtrage sur Relations (Jointures)](#filtrage-sur-relations-jointures)
      - [Exemple de Requête avec Jointures](#exemple-de-requête-avec-jointures)
    - [Configuration de Recherche Personnalisée](#configuration-de-recherche-personnalisée)
    - [Projection](#projection)
  - [Considérations de Performance](#considérations-de-performance)
  - [Intégration avec le Repository Pattern](#intégration-avec-le-repository-pattern)
    - [Utilisation avec PanacheRepository](#utilisation-avec-panacherepository)
  - [Exemple Complet](#exemple-complet)
    - [Exemples de Requêtes Combinées](#exemples-de-requêtes-combinées)
      - [Recherche + Filtrage + Tri + Pagination](#recherche--filtrage--tri--pagination)
      - [Filtrage Avancé + Filtrage OR + Tri](#filtrage-avancé--filtrage-or--tri)
  - [Classe PaginationInfo - Référence](#classe-paginationinfo---référence)

## Introduction

`QueryParamsHandler` est une classe utilitaire pour Quarkus qui facilite la gestion des paramètres de requête API, la recherche, le filtrage, le tri et la pagination des résultats de requêtes Panache. Cette classe est conçue pour standardiser et simplifier l'implémentation des fonctionnalités courantes d'API REST, tout en offrant une grande flexibilité et une sécurité renforcée.

## Fonctionnalités

- **Recherche textuelle** sur des champs configurables
- **Filtrage complexe** avec support de multiples opérateurs de comparaison
- **Filtrage par expressions OR** en plus des expressions AND par défaut
- **Filtrage par plage de dates** sur n'importe quel champ de date
- **Tri configurable** avec validation des champs de tri autorisés
- **Pagination avancée** avec métadonnées et liens de navigation
- **Mise en cache des résultats** pour améliorer les performances
- **Conversion automatique de types** pour les valeurs de filtres
- **Sécurité contre les injections SQL** par validation des noms de champs
- **Gestion d'erreurs robuste** avec messages explicites
- **Documentation OpenAPI** intégrée

## Utilisation Basique

### Configuration et Dépendances

Pour utiliser cette classe, assurez-vous d'avoir les dépendances Quarkus suivantes :

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm-panache</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-cache</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-openapi</artifactId>
</dependency>
```

### Exemple de Base

```java
@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @GET
    public Response getArticles(@Context UriInfo uriInfo) {
        // Définir les filtres autorisés
        Set<String> allowedFilters = Set.of("category_id", "status", "author_id");
        
        // Créer une requête de base
        PanacheQuery<Article> query = Article.findAll();
        
        // Créer et configurer le gestionnaire
        QueryParamsHandler<Article> handler = new QueryParamsHandler<>(
            query, uriInfo, allowedFilters
        ).withDefaultLimit(15).handle();
        
        // Obtenir les résultats paginés
        List<Article> results = handler.paginate();
        
        // Créer la réponse avec pagination
        Map<String, Object> response = new HashMap<>();
        response.put("data", results);
        response.put("pagination", handler.getPaginationInfo());
        
        return Response.ok(response).build();
    }
}
```

## Configuration Avancée

### Constructeurs

La classe offre deux constructeurs :

#### Constructeur Simplifié
```java
public QueryParamsHandler(
    PanacheQuery<T> query,
    UriInfo uriInfo,
    Set<String> allowedFilters
)
```

#### Constructeur Complet
```java
public QueryParamsHandler(
    PanacheQuery<T> query,
    UriInfo uriInfo,
    Set<String> allowedFilters,
    Set<String> allowedSortFields,
    Set<String> searchFields
)
```

### Méthodes de Configuration

```java
// Définit la limite par défaut pour la pagination (10 par défaut)
public QueryParamsHandler<T> withDefaultLimit(int defaultLimit)

// Définit la limite maximale pour la pagination (100 par défaut)
public QueryParamsHandler<T> withMaxLimit(int maxLimit)

// Active la mise en cache des résultats
public QueryParamsHandler<T> withCache(String cacheKey)
```

### Traitement des Paramètres

```java
// Applique tous les paramètres de requête configurés
public QueryParamsHandler<T> handle()
```

### Récupération des Résultats

```java
// Récupère les résultats paginés
public List<T> paginate()

// Récupère le nombre total d'enregistrements
public long count()

// Récupère les informations de pagination (total, page courante, etc.)
public PaginationInfo getPaginationInfo()
```

## Structure de Pagination

La pagination est gérée par une classe dédiée `PaginationInfo` qui encapsule toutes les informations relatives à la pagination. Pour une documentation complète de cette classe, consultez le [document de documentation de PaginationInfo](pagination-info-docs).

### Aperçu de PaginationInfo

```java
// Récupération des informations de pagination
PaginationInfo pagination = handler.getPaginationInfo();

// Vérification s'il y a une page suivante
boolean hasMore = pagination.hasNextPage();

// Obtention des métadonnées de pagination pour la réponse
Map<String, Object> paginationData = pagination.toMap();
```

### Structure JSON générée

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

Cette structure standardisée facilite l'utilisation par les clients frontend et assure la cohérence à travers toute l'API.

## Paramètres de Requête Supportés

### Recherche

```
GET /articles?search=keyword
```

Effectue une recherche sur les champs configurés. Par défaut, la recherche s'effectue sur `title` et `description` avec un opérateur `LIKE`.

#### Exemple de Recherche

**Requête HTTP:**
```
GET /articles?search=quarkus
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
WHERE (
    a.title LIKE '%quarkus%' OR 
    a.description LIKE '%quarkus%'
)
ORDER BY a.created_at DESC
LIMIT 10 OFFSET 0
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 1,
      "title": "Getting Started with Quarkus",
      "description": "Learn how to build supersonic Java applications",
      "created_at": "2023-11-15T10:30:00"
    },
    {
      "id": 5,
      "title": "RESTEasy in Quarkus",
      "description": "Building REST APIs with Quarkus",
      "created_at": "2023-10-20T14:45:00"
    }
  ],
  "pagination": {
    "total": 2,
    "count": 2,
    "per_page": 10,
    "current_page": 1,
    "last_page": 1
  }
}
```

### Filtrage Simple

```
GET /articles?filter={"category_id":1,"status":"published"}
```

#### Exemple de Filtrage Simple

**Requête HTTP:**
```
GET /articles?filter={"category_id":3,"status":"published"}
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
WHERE (a.category_id = 3 AND a.status = 'published')
ORDER BY a.created_at DESC
LIMIT 10 OFFSET 0
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 12,
      "title": "Microservices with Quarkus",
      "category_id": 3,
      "status": "published",
      "created_at": "2023-11-05T09:15:00"
    },
    {
      "id": 15,
      "title": "Reactive Programming in Java",
      "category_id": 3,
      "status": "published",
      "created_at": "2023-10-28T16:20:00"
    }
  ],
  "pagination": {
    "total": 2,
    "count": 2,
    "per_page": 10,
    "current_page": 1,
    "last_page": 1
  }
}
```

### Filtrage Avancé

```
GET /articles?filter=[{"field":"price","operator":"gt","value":100,"dataType":"double"},{"field":"status","operator":"eq","value":"active","dataType":"string"}]
```

#### Exemple de Filtrage Avancé

**Requête HTTP:**
```
GET /articles?filter=[{"field":"views","operator":"gt","value":1000,"dataType":"integer"},{"field":"published_date","operator":"gte","value":"2023-01-01","dataType":"date"}]
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
WHERE (a.views > 1000 AND a.published_date >= '2023-01-01')
ORDER BY a.created_at DESC
LIMIT 10 OFFSET 0
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 3,
      "title": "Kubernetes Deployment Strategies",
      "views": 2450,
      "published_date": "2023-02-15",
      "created_at": "2023-02-10T11:30:00"
    },
    {
      "id": 8,
      "title": "Cloud Native Applications",
      "views": 1850,
      "published_date": "2023-03-20",
      "created_at": "2023-03-15T09:45:00"
    }
  ],
  "pagination": {
    "total": 2,
    "count": 2,
    "per_page": 10,
    "current_page": 1,
    "last_page": 1
  }
}
```

### Opérateurs de Filtre Supportés

- `eq` : Égal à (=)
- `ne` : Différent de (!=)
- `gt` : Supérieur à (>)
- `gte` : Supérieur ou égal à (>=)
- `lt` : Inférieur à (<)
- `lte` : Inférieur ou égal à (<=)
- `like` : Contient (%value%)
- `in` : Dans une liste
- `isNull` : Est null
- `isNotNull` : N'est pas null

### Types de Données Supportés

- `string` : Chaîne de caractères
- `int` / `integer` : Entier
- `long` : Entier long
- `double` / `float` : Nombre à virgule flottante
- `boolean` / `bool` : Booléen
- `date` : Date (format ISO)
- `datetime` : Date et heure
- `list` / `array` : Liste de valeurs

### Filtrage OR

```
GET /articles?orFilter=[{"field":"category_id","operator":"eq","value":1},{"field":"category_id","operator":"eq","value":2}]
```

Applique des conditions OR entre les filtres spécifiés.

#### Exemple de Filtrage OR

**Requête HTTP:**
```
GET /articles?orFilter=[{"field":"category_id","operator":"eq","value":1},{"field":"category_id","operator":"eq","value":2}]
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
WHERE (a.category_id = 1 OR a.category_id = 2)
ORDER BY a.created_at DESC
LIMIT 10 OFFSET 0
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 1,
      "title": "Getting Started with Quarkus",
      "category_id": 1,
      "created_at": "2023-11-15T10:30:00"
    },
    {
      "id": 2,
      "title": "Introduction to Spring Boot",
      "category_id": 2,
      "created_at": "2023-11-10T14:20:00"
    },
    {
      "id": 4,
      "title": "Hibernate ORM with Quarkus",
      "category_id": 1,
      "created_at": "2023-10-25T08:45:00"
    }
  ],
  "pagination": {
    "total": 3,
    "count": 3,
    "per_page": 10,
    "current_page": 1,
    "last_page": 1
  }
}
```

### Filtrage par Date

```
GET /articles?from=2023-01-01&to=2023-12-31
```

Filtre sur le champ `created_at` par défaut.

```
GET /articles?from=2023-01-01&to=2023-12-31&dateField=published_at
```

Filtre sur un champ de date spécifique.

#### Exemple de Filtrage par Date

**Requête HTTP:**
```
GET /articles?from=2023-10-01&to=2023-10-31&dateField=published_at
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
WHERE (date(a.published_at) >= '2023-10-01' AND date(a.published_at) <= '2023-10-31')
ORDER BY a.created_at DESC
LIMIT 10 OFFSET 0
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 5,
      "title": "RESTEasy in Quarkus",
      "published_at": "2023-10-20",
      "created_at": "2023-10-18T14:45:00"
    },
    {
      "id": 7,
      "title": "GraphQL with Quarkus",
      "published_at": "2023-10-10",
      "created_at": "2023-10-05T11:30:00"
    }
  ],
  "pagination": {
    "total": 2,
    "count": 2,
    "per_page": 10,
    "current_page": 1,
    "last_page": 1
  }
}
```

### Tri

```
GET /articles?sort=created_at&order=desc
```

Trie les résultats par le champ spécifié dans l'ordre indiqué (asc ou desc).

> **Important** : Dans Quarkus Panache, le tri doit être appliqué lors de la création de la requête, et non après. La classe `QueryParamsHandler` fournit une méthode utilitaire `getSort` pour gérer cela.

#### Méthode getSort

```java
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
```

#### Exemple d'Application du Tri

```java
// Dans votre resource ou service
MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
String sortField = queryParams.getFirst("sort");
String sortOrder = queryParams.getFirst("order");

// Obtenir l'objet Sort
Sort sort = QueryParamsHandler.getSort(sortField, sortOrder, allowedSortFields);

// Appliquer le tri lors de la création de la requête
PanacheQuery<Article> query = Article.find(null, sort);
// Ou avec une condition
// PanacheQuery<Article> query = Article.find("status = ?1", sort, "published");

// Passer la requête au handler
QueryParamsHandler<Article> handler = new QueryParamsHandler<>(query, uriInfo, allowedFilters).handle();
```

#### Exemple de Tri

**Requête HTTP:**
```
GET /articles?sort=views&order=desc
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
ORDER BY a.views DESC
LIMIT 10 OFFSET 0
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 3,
      "title": "Kubernetes Deployment Strategies",
      "views": 2450,
      "created_at": "2023-02-10T11:30:00"
    },
    {
      "id": 8,
      "title": "Cloud Native Applications",
      "views": 1850,
      "created_at": "2023-03-15T09:45:00"
    },
    {
      "id": 12,
      "title": "Microservices with Quarkus",
      "views": 950,
      "created_at": "2023-11-05T09:15:00"
    }
  ],
  "pagination": {
    "total": 3,
    "count": 3,
    "per_page": 10,
    "current_page": 1,
    "last_page": 1
  }
}
```

### Pagination

```
GET /articles?page=2&limit=15
```

Spécifie la page à récupérer et le nombre d'éléments par page.

```
GET /articles?all=true
```

Récupère tous les résultats sans pagination.

#### Exemple de Pagination

**Requête HTTP:**
```
GET /articles?page=2&limit=2
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
ORDER BY a.created_at DESC
LIMIT 2 OFFSET 2
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 3,
      "title": "Kubernetes Deployment Strategies",
      "created_at": "2023-02-10T11:30:00"
    },
    {
      "id": 4,
      "title": "Hibernate ORM with Quarkus",
      "created_at": "2023-10-25T08:45:00"
    }
  ],
  "pagination": {
    "total": 8,
    "count": 2,
    "per_page": 2,
    "current_page": 2,
    "last_page": 4,
    "links": {
      "first": "/articles?page=1&limit=2",
      "last": "/articles?page=4&limit=2",
      "prev": "/articles?page=1&limit=2",
      "next": "/articles?page=3&limit=2"
    }
  }
}
```

## Structure de la Réponse

La réponse générée par la classe `QueryParamsHandler` est structurée de manière standardisée, avec les résultats de la requête et les informations de pagination.

### Format de Réponse Standard

```json
{
  "data": [
    // Résultats paginés
  ],
  "pagination": {
    "total": 100,        // Nombre total d'éléments
    "count": 15,         // Nombre d'éléments sur la page courante
    "per_page": 15,      // Nombre d'éléments par page
    "current_page": 2,   // Numéro de la page courante
    "last_page": 7,      // Nombre total de pages
    "from": 16,          // Index du premier élément sur cette page
    "to": 30,            // Index du dernier élément sur cette page
    "links": {
      "first": "/articles?sort=created_at&order=desc&page=1&limit=15",
      "last": "/articles?sort=created_at&order=desc&page=7&limit=15",
      "prev": "/articles?sort=created_at&order=desc&page=1&limit=15",
      "next": "/articles?sort=created_at&order=desc&page=3&limit=15"
    }
  }
}
```

### Exemple d'Utilisation avec PaginationInfo

```java
@GET
@Path("/articles")
public Response getArticles(@Context UriInfo uriInfo) {
    Set<String> allowedFilters = Set.of("category_id", "status");
    
    QueryParamsHandler<Article> handler = new QueryParamsHandler<>(
        Article.findAll(), uriInfo, allowedFilters
    ).withDefaultLimit(15).handle();
    
    List<Article> results = handler.paginate();
    PaginationInfo pagination = handler.getPaginationInfo();
    
    // Construction de la réponse
    Map<String, Object> response = new HashMap<>();
    response.put("data", results);
    response.put("pagination", pagination.toMap());
    
    return Response.ok(response).build();
}
```

### Avantages de PaginationInfo

La classe `PaginationInfo` offre plusieurs avantages par rapport à l'utilisation d'une simple Map :

1. **Type Safety** : Le compilateur vérifie les types, évitant les erreurs d'exécution.
2. **API Intuitive** : Des méthodes comme `hasNextPage()` ou `isLastPage()` facilitent la navigation.
3. **Calculs Automatiques** : Les valeurs comme `from`, `to` et `offset` sont calculées automatiquement.
4. **Réutilisabilité** : La classe peut être utilisée dans différents contextes (API, UI, exports).
5. **Extensibilité** : Vous pouvez facilement ajouter de nouvelles fonctionnalités à la classe.

### Méthodes Utiles de PaginationInfo

- `isFirstPage()` : Vérifie si on est sur la première page
- `isLastPage()` : Vérifie si on est sur la dernière page
- `hasNextPage()` : Vérifie s'il existe une page suivante
- `hasPreviousPage()` : Vérifie s'il existe une page précédente
- `getOffset()` : Calcule l'offset pour les requêtes SQL (LIMIT x OFFSET y)
- `getFrom()` : Index du premier élément sur la page courante (base 1)
- `getTo()` : Index du dernier élément sur la page courante

## Sécurité

### Validation des Noms de Champs

Tous les noms de champs (filtres, tri, recherche) sont validés à l'aide d'une expression régulière pour prévenir les injections SQL. Seuls les caractères alphanumériques et les underscores sont autorisés, ainsi que les points pour les relations (ex: `author.name`).

### Limites de Pagination

La pagination est limitée à une valeur maximale configurable (par défaut 100) pour éviter les attaques par déni de service.

## Mise en Cache

L'activation de la mise en cache permet d'améliorer les performances pour les requêtes fréquentes :

```java
QueryParamsHandler<Article> handler = new QueryParamsHandler<>(query, uriInfo, allowedFilters)
    .withCache("articles-list")
    .handle();
```

Nécessite la configuration de Quarkus Cache :

```properties
# application.properties
quarkus.cache.caffeine.query-results.initial-capacity=50
quarkus.cache.caffeine.query-results.maximum-size=200
quarkus.cache.caffeine.query-results.expire-after-write=60S
```

## Gestion des Erreurs

La classe génère des exceptions `BadRequestException` avec des messages explicites en cas d'erreurs dans les paramètres de requête. Les erreurs sont également journalisées pour faciliter le débogage.

## Cas d'Utilisation Avancés

### Filtrage sur Relations (Jointures)

Pour filtrer sur des champs de tables liées :

```java
Set<String> allowedFilters = Set.of("id", "title", "author.name", "category.name");
```

#### Exemple de Requête avec Jointures

**Requête HTTP:**
```
GET /articles?filter=[{"field":"author.name","operator":"eq","value":"John Doe","dataType":"string"}]
```

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
JOIN Author au ON a.author_id = au.id
WHERE au.name = 'John Doe'
ORDER BY a.created_at DESC
LIMIT 10 OFFSET 0
```

**Résultat:**
```json
{
  "data": [
    {
      "id": 1,
      "title": "Getting Started with Quarkus",
      "author": {
        "id": 5,
        "name": "John Doe"
      },
      "created_at": "2023-11-15T10:30:00"
    },
    {
      "id": 12,
      "title": "Microservices with Quarkus",
      "author": {
        "id": 5,
        "name": "John Doe"
      },
      "created_at": "2023-11-05T09:15:00"
    }
  ],
  "pagination": {
    "total": 2,
    "count": 2,
    "per_page": 10,
    "current_page": 1,
    "last_page": 1
  }
}
```

### Configuration de Recherche Personnalisée

```java
Set<String> searchFields = Set.of("title", "content", "author.name", "tags");
QueryParamsHandler<Article> handler = new QueryParamsHandler<>(
    query, uriInfo, allowedFilters, allowedSortFields, searchFields
).handle();
```

### Projection

Pour limiter les champs retournés, utilisez la méthode `project` de Panache avant de créer le gestionnaire :

```java
PanacheQuery<Article> query = Article.findAll().project(Article.class, "id", "title", "createdAt");
```

## Considérations de Performance

- Activez la mise en cache pour les requêtes fréquentes
- Limitez les champs de recherche et de filtrage au minimum nécessaire
- Utilisez des index sur les colonnes fréquemment utilisées pour le filtrage et le tri
- Évitez d'utiliser des opérateurs LIKE sur de grandes tables sans index appropriés

## Intégration avec le Repository Pattern

La classe `QueryParamsHandler` est conçue pour s'intégrer facilement avec le pattern Repository de Quarkus Panache.

### Utilisation avec PanacheRepository

Définissez d'abord votre repository avec support pour le tri :

```java
@ApplicationScoped
public class ArticleRepository implements PanacheRepository<Article> {
    // Méthodes spécifiques qui retournent PanacheQuery<Article>
    
    // Méthode pour trouver tous les articles avec tri
    public PanacheQuery<Article> findAll(Sort sort) {
        return find(null, sort);
    }
    
    // Méthode pour trouver les articles publiés avec tri
    public PanacheQuery<Article> findPublished(Sort sort) {
        return find("status", sort, "published");
    }
    
    // Méthode pour trouver les articles par catégorie avec tri
    public PanacheQuery<Article> findByCategory(Long categoryId, Sort sort) {
        return find("category.id = ?1", sort, categoryId);
    }
    
    // Méthode pour trouver les articles mis en avant avec tri
    public PanacheQuery<Article> findFeaturedArticles(Sort sort) {
        return find("featured = true", sort);
    }
}
```

Puis utilisez ce repository avec `QueryParamsHandler` et la méthode `getSort` :

```java
@Path("/articles")
public class ArticleResource {
    @Inject
    ArticleRepository articleRepository;
    
    @GET
    @Path("/featured")
    public Response getFeaturedArticles(@Context UriInfo uriInfo) {
        // Extraire les paramètres de tri
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String sortField = queryParams.getFirst("sort");
        String sortOrder = queryParams.getFirst("order");
        
        // Définir les filtres autorisés
        Set<String> allowedFilters = Set.of("category_id", "tag");
        Set<String> allowedSortFields = Set.of("id", "title", "createdAt", "views");
        
        // Créer l'objet Sort
        Sort sort = QueryParamsHandler.getSort(sortField, sortOrder, allowedSortFields);
        
        // Obtenir une requête pré-filtrée avec tri appliqué
        PanacheQuery<Article> baseQuery = articleRepository.findFeaturedArticles(sort);
        
        // Passer cette requête à QueryParamsHandler
        QueryParamsHandler<Article> handler = new QueryParamsHandler<>(
            baseQuery, 
            uriInfo,
            allowedFilters
        ).handle();
        
        // Paginer et retourner les résultats
        List<Article> results = handler.paginate();
        PaginationInfo pagination = handler.getPaginationInfo();
        
        return Response.ok(Map.of(
            "data", results,
            "pagination", pagination.toMap()
        )).build();
    }
}
```

Cette approche tient compte de la limitation de Panache concernant le tri, qui doit être appliqué lors de la création de la requête plutôt qu'après. Les avantages restent les mêmes :

1. **Séparation des préoccupations** : Le repository encapsule la logique d'accès aux données, tandis que `QueryParamsHandler` gère les paramètres de requête API.

2. **Réutilisation** : Vous pouvez définir des requêtes courantes dans le repository et les utiliser dans différents endpoints.

3. **Flexibilité** : Vous pouvez combiner des filtres prédéfinis du repository avec des filtres dynamiques de l'API.

4. **Testabilité** : Les repositories peuvent être mockés pour faciliter les tests unitaires.

## Exemple Complet

```java
@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @Inject
    ArticleRepository articleRepository;

    @GET
    @Operation(summary = "Recherche d'articles avec filtres avancés")
    public Response searchArticles(
        @Context UriInfo uriInfo,
        @Parameter(description = "Terme de recherche") @QueryParam("search") String search,
        @Parameter(description = "Page courante") @QueryParam("page") @DefaultValue("1") int page,
        @Parameter(description = "Nombre d'éléments par page") @QueryParam("limit") @DefaultValue("10") int limit
    ) {
        // Définir les paramètres autorisés
        Set<String> allowedFilters = Set.of("category_id", "status", "published", "author_id");
        Set<String> allowedSortFields = Set.of("id", "title", "createdAt", "updatedAt", "views");
        Set<String> searchFields = Set.of("title", "description", "content", "tags");
        
        // Obtenir les paramètres de tri
        String sortField = uriInfo.getQueryParameters().getFirst("sort");
        String sortOrder = uriInfo.getQueryParameters().getFirst("order");
        
        // Créer l'objet Sort
        Sort sort = QueryParamsHandler.getSort(sortField, sortOrder, allowedSortFields);
        
        // Obtenir une requête de base depuis le repository avec tri appliqué
        PanacheQuery<Article> baseQuery = articleRepository.findAll(sort);
        
        // Pour des requêtes conditionnelles avec tri
        // PanacheQuery<Article> baseQuery = articleRepository.find("status = ?1", sort, "published");
        
        // Créer et configurer le gestionnaire
        QueryParamsHandler<Article> handler = new QueryParamsHandler<>(
            baseQuery, 
            uriInfo, 
            allowedFilters,
            allowedSortFields,
            searchFields
        )
        .withDefaultLimit(15)
        .withMaxLimit(100)
        .withCache("articles-cache")
        .handle();
        
        // Récupérer les résultats
        List<Article> results = handler.paginate();
        PaginationInfo pagination = handler.getPaginationInfo();
        
        // Préparer la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("data", results);
        response.put("pagination", pagination.toMap());
        
        return Response.ok(response).build();
    }
}
```

### Exemples de Requêtes Combinées

#### Recherche + Filtrage + Tri + Pagination

**Requête HTTP:**
```
GET /articles?search=cloud&filter={"status":"published"}&sort=views&order=desc&page=1&limit=5
```

Cette requête recherche tous les articles publiés contenant le terme "cloud", triés par nombre de vues décroissant, et affiche les 5 premiers résultats.

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
WHERE (a.title LIKE '%cloud%' OR a.description LIKE '%cloud%' OR a.content LIKE '%cloud%' OR a.tags LIKE '%cloud%')
  AND a.status = 'published'
ORDER BY a.views DESC
LIMIT 5 OFFSET 0
```

#### Filtrage Avancé + Filtrage OR + Tri

**Requête HTTP:**
```
GET /articles?filter=[{"field":"created_at","operator":"gte","value":"2023-01-01","dataType":"date"}]&orFilter=[{"field":"category_id","operator":"eq","value":1},{"field":"category_id","operator":"eq","value":3}]&sort=created_at&order=desc
```

Cette requête recherche tous les articles créés depuis le 1er janvier 2023 qui appartiennent soit à la catégorie 1, soit à la catégorie 3, triés par date de création décroissante.

**SQL généré (équivalent):**
```sql
SELECT a.* 
FROM Article a
WHERE (a.created_at >= '2023-01-01')
  AND (a.category_id = 1 OR a.category_id = 3)
ORDER BY a.created_at DESC
LIMIT 10 OFFSET 0
```
```

## Classe PaginationInfo - Référence

Cette section a été déplacée vers un document dédié. Veuillez consulter la [documentation complète de PaginationInfo](pagination-info-docs) pour tous les détails sur cette classe.