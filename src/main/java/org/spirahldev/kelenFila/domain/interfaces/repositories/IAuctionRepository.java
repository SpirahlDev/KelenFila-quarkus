package org.spirahldev.kelenFila.domain.interfaces.repositories;

import java.util.List;

import org.spirahldev.kelenFila.domain.model.Auction;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IAuctionRepository extends PanacheRepository<Auction> {
    public Auction storeAuction(Auction auction);
    public List<Auction> getAuctions();
    public Auction getAuctionFromId(Long id);

    // public PanacheQuery<Auction> getAllQuery(){
    //     return find
    // }

    public PanacheQuery<Auction> getAllQuery(Sort sort) ;
    
    // Version sans tri
    public PanacheQuery<Auction> getAllQuery() ;
}
