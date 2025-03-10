package org.spirahldev.kelenFila.app.interfaces;

import java.util.List;

import org.spirahldev.kelenFila.app.IOmodel.input.AuctionInput;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Auction;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IAuctionService {
    public Auction createAuction(AuctionInput auctionData,Account account);
    public List<Auction> getAuctions();
    public Boolean deleteAuction(Auction auction);
}
