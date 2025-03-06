package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
public class Article {
    
    public enum ArticleState {
        EXCELENT, GOOD, MID, BAD
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "item_identifier", nullable = false, length = 100)
    private String itemIdentifier;
    
    @Column(name = "article_name", nullable = false, length = 100)
    private String articleName;
    
    @Column(name = "article_description", nullable = false, columnDefinition = "TEXT")
    private String articleDescription;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "article_state", nullable = false)
    private ArticleState articleState;
    
    @Column(name = "article_state_description", length = 200)
    private String articleStateDescription;
    
    @Column(name = "article_base_price", nullable = false)
    private Double articleBasePrice;
    
    @Column(nullable = false, length = 400)
    private String image1;
    
    @Column(nullable = false, length = 400)
    private String image2;
    
    @Column(nullable = false, length = 400)
    private String image3;
    
    @Column(nullable = false, length = 400)
    private String image4;
    
    @Column(length = 400)
    private String image5;
    
    @Column(length = 400)
    private String image6;
    
    @Column(name = "awarded_at")
    private LocalDateTime awardedAt;
    
    @Column(name = "awarded_price")
    private Double awardedPrice;
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @OneToMany(mappedBy = "article")
    private List<CollectionArticleEntity> collectionArticles;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public void setItemIdentifier(String itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public ArticleState getArticleState() {
        return articleState;
    }

    public void setArticleState(ArticleState articleState) {
        this.articleState = articleState;
    }

    public String getArticleStateDescription() {
        return articleStateDescription;
    }

    public void setArticleStateDescription(String articleStateDescription) {
        this.articleStateDescription = articleStateDescription;
    }

    public Double getArticleBasePrice() {
        return articleBasePrice;
    }

    public void setArticleBasePrice(Double articleBasePrice) {
        this.articleBasePrice = articleBasePrice;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public LocalDateTime getAwardedAt() {
        return awardedAt;
    }

    public void setAwardedAt(LocalDateTime awardedAt) {
        this.awardedAt = awardedAt;
    }

    public Double getAwardedPrice() {
        return awardedPrice;
    }

    public void setAwardedPrice(Double awardedPrice) {
        this.awardedPrice = awardedPrice;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<CollectionArticleEntity> getCollectionArticles() {
        return collectionArticles;
    }

    public void setCollectionArticles(List<CollectionArticleEntity> collectionArticles) {
        this.collectionArticles = collectionArticles;
    }
}