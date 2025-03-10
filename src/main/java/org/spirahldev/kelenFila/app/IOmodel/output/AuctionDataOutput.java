package org.spirahldev.kelenFila.app.IOmodel.output;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.spirahldev.kelenFila.domain.model.Auction;

public record AuctionDataOutput(
    Long id,
    String auctionCode,
    Long ownerAccountId,
    String title,
    String description,
    String coverImage,
    LocalDateTime date,
    LocalTime timeTook,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
    // Méthode pour mapper depuis l'entité
    public static AuctionDataOutput fromEntity(Auction entity) {
        if (entity == null) {
            return null;
        }
        System.out.println(entity.getCreatedAt());
        return new AuctionDataOutput(
            entity.getId(),
            entity.getAuctionCode(),
            entity.getOwnerAccount().getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getCoverImage(),
            entity.getDate(),
            entity.getAuctionTimeTook(),
            entity.getCreatedAt(),
            entity.getDeletedAt()
        );
    }
}    