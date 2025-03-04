package org.spirahldev.kelenFila.adapters.persistence.repositories;

import org.spirahldev.kelenFila.domain.interfaces.IAccountRepository;
import org.spirahldev.kelenFila.domain.model.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepositoryImpl implements PanacheRepository<Account>,IAccountRepository{

    @Override
    public Account createAccount(Account account) {
        persist(account);
        return account;
    } 

    @Override
    public Account findAccountByLogin(String login){
        Account account = find("login = ?1 and suspendedAt is null", login).firstResult();        
        return account;
    }
    
}
