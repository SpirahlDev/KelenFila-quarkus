package org.spirahldev.kelenFila.app.services;

import org.hibernate.exception.ConstraintViolationException;
import org.spirahldev.kelenFila.adapters.persistence.repositories.PersonRepositoryImpl;
import org.spirahldev.kelenFila.app.IOmodel.input.AccountDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.PersonDataInput;
import org.spirahldev.kelenFila.app.interfaces.IUserService;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.domain.enums.ProfileCode;
import org.spirahldev.kelenFila.domain.exceptions.BusinessException;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Person;
import org.spirahldev.kelenFila.domain.model.ProfileEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserService implements IUserService{

    @Inject
    AccountService accountService;
    @Inject
    PersonRepositoryImpl personRepositoryImpl;

    @Override
    @Transactional
    public Account createClient(PersonDataInput personDataInput,AccountDataInput accountData) {
        try {
            ProfileEntity profile=ProfileEntity.findByProfileCode(ProfileCode.CLIENT);
    
            Person person=Person.from(personDataInput);
            person=personRepositoryImpl.save(person);
            
    
            Account account=accountService.createAccount(profile,person,accountData);
            return account;
            
        } catch (ConstraintViolationException e) {
            throw new BusinessException(AppStatusCode.USER_ALREADY_EXIST, null, null,
                Response.Status.CONFLICT
            );
        }
    }

    @Override
    @Transactional
    public Account createAuctionneer(PersonDataInput personDataInput,AccountDataInput accountData){

        try{
            ProfileEntity profile=ProfileEntity.findByProfileCode(ProfileCode.AUCTIONNEER);
            
            Person person=Person.from(personDataInput);
            
            person=personRepositoryImpl.save(person);

            return accountService.createAccount(profile,person,accountData);
            
        }catch (ConstraintViolationException e) {
            throw new BusinessException(AppStatusCode.USER_ALREADY_EXIST, null, null,
                Response.Status.CONFLICT
            );
        }

    }

    


 
}
