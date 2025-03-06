package org.spirahldev.kelenFila.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "collection_article")
@IdClass(CollectionArticleId.class)
public class CollectionArticleEntity extends PanacheEntityBase {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "bid_collection_id", nullable = false)
    private BidCollectionEntity bidCollection;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    
    // Getters and Setters
    public BidCollectionEntity getBidCollection() {
        return bidCollection;
    }

    public void setBidCollection(BidCollectionEntity bidCollection) {
        this.bidCollection = bidCollection;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}