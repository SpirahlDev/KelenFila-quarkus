package org.spirahldev.kelenFila.domain.services;

import org.spirahldev.kelenFila.common.utils.Utilities;
import org.spirahldev.kelenFila.domain.interfaces.services.IAuctionDomainService;
import org.spirahldev.kelenFila.domain.model.Person;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuctionDomainService implements IAuctionDomainService {

    @Override
    public String generateAuctionCode(Person person) {
        StringBuilder codeBuilder=new StringBuilder();

        codeBuilder.append("AUC")
        .append("-"+Utilities.generateSecureRandomLetters(3))
        .append("-"+person.getFirstname().substring(0, 2))
        .append("-"+Utilities.generateRandomCode(3))
        .append("-"+Utilities.generateRandomCode(3))
        .append("-"+person.getLastname().substring(0,1));
        

        return codeBuilder.toString();
    }
    
}
