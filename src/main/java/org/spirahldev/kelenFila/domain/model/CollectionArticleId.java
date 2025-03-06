package org.spirahldev.kelenFila.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class CollectionArticleId implements Serializable {
    private Long bidCollection;
    private Long article;
    
    public CollectionArticleId() {
    }
    
    public CollectionArticleId(Long bidCollection, Long article) {
        this.bidCollection = bidCollection;
        this.article = article;
    }
    
    // Getters and Setters
    public Long getBidCollection() {
        return bidCollection;
    }

    public void setBidCollection(Long bidCollection) {
        this.bidCollection = bidCollection;
    }

    public Long getArticle() {
        return article;
    }

    public void setArticle(Long article) {
        this.article = article;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CollectionArticleId that = (CollectionArticleId) o;
        
        return Objects.equals(bidCollection, that.bidCollection) &&
               Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bidCollection, article);
    }
}