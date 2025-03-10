package org.spirahldev.kelenFila.app.IOmodel.output;

import java.time.LocalDateTime;

import org.spirahldev.kelenFila.domain.model.BidCollectionEntity;

public record BidCollectionOutput(
    Long id,
    Integer collectionNumber,
    String name,
    LocalDateTime createdAt,
    String description,
    Long auctionId
) {
    // Méthode pour mapper depuis l'entité
    public static BidCollectionOutput fromEntity(BidCollectionEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new BidCollectionOutput(
            entity.getId(),
            entity.getCollectionNumber(),
            entity.getName(),
            entity.getCreatedAt(),
            entity.getDescription(),
            entity.getAuction().getId()
        );
    }
    
}
