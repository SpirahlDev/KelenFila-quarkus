package org.spirahldev.kelenFila.app.IOmodel.input;

import java.time.LocalDateTime;

import org.spirahldev.kelenFila.domain.model.BidCollectionEntity;

public record BidCollectionInput(
    String name,
    LocalDateTime createdAt,
    LocalDateTime deletedAt,
    String description,
    Long auctionId
) { 
    // Méthode pour convertir vers l'entité
    public BidCollectionEntity toEntity() {
        BidCollectionEntity entity = new BidCollectionEntity();

        entity.setName(this.name);
        entity.setCreatedAt(this.createdAt);
        entity.setDeletedAt(this.deletedAt);
        entity.setDescription(this.description);
        return entity;
    }
}
