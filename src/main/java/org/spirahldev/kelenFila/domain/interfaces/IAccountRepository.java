package org.spirahldev.kelenFila.domain.interfaces;

import org.spirahldev.kelenFila.domain.model.Account;

public interface IAccountRepository {
    public Account createAccount(Account account);
    public Account findAccountByLogin(String Login);
}
