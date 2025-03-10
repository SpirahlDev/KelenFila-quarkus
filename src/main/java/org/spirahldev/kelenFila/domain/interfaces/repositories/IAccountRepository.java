package org.spirahldev.kelenFila.domain.interfaces.repositories;

import org.spirahldev.kelenFila.domain.model.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IAccountRepository extends PanacheRepository<Account> {
    public Account createAccount(Account account);
    public Account findAccountByLogin(String Login);
}
