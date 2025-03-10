package org.spirahldev.kelenFila.adapters.persistence.repositories;

import java.util.List;

import org.spirahldev.kelenFila.domain.interfaces.repositories.IAuctionRepository;
import org.spirahldev.kelenFila.domain.model.Auction;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuctionRepositoryImpl implements IAuctionRepository{


    @Override
    public Auction storeAuction(Auction auction) {
        persist(auction);
        return auction;
    }

    @Override
    public List<Auction> getAuctions() {
        return find("deletedAt is null").list();
    }

    @Override
    public Auction getAuctionFromId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuctionFromId'");
    }

    @Override
    public PanacheQuery<Auction> getAllQuery(Sort sort) {
        if (sort != null) {
            return findAll(sort);
        } else {
            return findAll();
        }
    }
    
    @Override
    // Version sans tri
    public PanacheQuery<Auction> getAllQuery() {
        return findAll();
    }
    
   
    
}
