package org.spirahldev.kelenFila.domain.interfaces.services;

import org.spirahldev.kelenFila.domain.model.Person;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IAuctionDomainService {

    public String generateAuctionCode(Person person);
}
