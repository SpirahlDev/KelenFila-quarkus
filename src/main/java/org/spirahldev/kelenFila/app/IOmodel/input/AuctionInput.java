package org.spirahldev.kelenFila.app.IOmodel.input;

import java.time.LocalDateTime;

import org.spirahldev.kelenFila.domain.model.Auction;

public record AuctionInput(
    String title,
    String description,
    String coverImage,
    LocalDateTime date
) {
    // Méthode pour mapper depuis le record d'entrée vers l'entité
    public Auction toAuction() {
        Auction auction = new Auction();

        auction.setTitle(this.title);
        auction.setDescription(this.description);
        auction.setCoverImage(this.coverImage);
        auction.setDate(this.date);

        return auction; 
    }
}
