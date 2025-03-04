package org.spirahldev.kelenFila.app.interfaces;

import org.spirahldev.kelenFila.app.IOmodel.input.AccountDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.UserLoginInput;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Person;
import org.spirahldev.kelenFila.domain.model.ProfileEntity;

public interface IAccountService {
    public Account createAccount(ProfileEntity profile,Person person,AccountDataInput accountData) ;

    public String authenticate(UserLoginInput loginData);
      
    public Account getAccountFromLogin(String login);

    public Account getAccountFromId(Long account_id);
}
