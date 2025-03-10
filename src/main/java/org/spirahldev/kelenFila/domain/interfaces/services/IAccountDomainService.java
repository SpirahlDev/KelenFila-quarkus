package org.spirahldev.kelenFila.domain.interfaces.services;

import java.util.ArrayList;

import org.spirahldev.kelenFila.domain.constants.DomainStatusCode;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Person;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IAccountDomainService {
    public boolean canUpdateProfile(Account account);

    public void validateAccountCreation(Person person);

    public ArrayList<DomainStatusCode> getPasswordValidation(String password);
}
