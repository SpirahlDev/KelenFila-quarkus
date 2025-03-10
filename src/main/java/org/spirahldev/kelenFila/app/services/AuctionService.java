package org.spirahldev.kelenFila.app.services;

import java.util.List;

import org.spirahldev.kelenFila.app.IOmodel.input.AuctionInput;
import org.spirahldev.kelenFila.app.interfaces.IAuctionService;
import org.spirahldev.kelenFila.domain.interfaces.repositories.IAuctionRepository;
import org.spirahldev.kelenFila.domain.interfaces.services.IAuctionDomainService;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Auction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuctionService implements IAuctionService{

    @Inject
    IAuctionRepository auctionRepo; /**@AuctionRepositoryImpl */

    @Inject IAuctionDomainService auctionDomainService;

    @Override
    @Transactional
    public Auction createAuction(AuctionInput auctionData,Account account) {
        Auction auction=auctionData.toAuction();
        auction.setOwnerAccount(account);
        auction.setAuctionCode(auctionDomainService.generateAuctionCode(account.getPerson()));

        return auctionRepo.storeAuction(auction);
    }

    @Override
    public List<Auction> getAuctions() {
        return auctionRepo.getAuctions();
    }

    @Override
    public Boolean deleteAuction(Auction auction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAuction'");
    }
    
}
