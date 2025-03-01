package org.spirahldev.kelenFila.adapters.persistence.repositories;

import org.spirahldev.kelenFila.domain.interfaces.IAccountRepository;
import org.spirahldev.kelenFila.domain.model.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
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
        return find("login = ?1 and isActive=true", login).firstResult();
    }
    
}
