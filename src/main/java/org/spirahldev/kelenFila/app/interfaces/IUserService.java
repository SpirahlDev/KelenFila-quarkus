package org.spirahldev.kelenFila.app.interfaces;

import org.spirahldev.kelenFila.app.IOmodel.input.AccountDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.PersonDataInput;
import org.spirahldev.kelenFila.domain.model.Account;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IUserService {
    public Account createClient(PersonDataInput personDataInput,AccountDataInput accountData);
    public Account createAuctionneer(PersonDataInput personDataInput,AccountDataInput accountData);
}
