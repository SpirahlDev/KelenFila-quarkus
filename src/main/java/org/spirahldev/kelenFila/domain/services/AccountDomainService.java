package org.spirahldev.kelenFila.domain.services;

import java.time.LocalDate;
import java.util.ArrayList;

import org.spirahldev.kelenFila.common.constants.GlobalConstants;
import org.spirahldev.kelenFila.domain.constants.DomainStatusCode;
import org.spirahldev.kelenFila.domain.exceptions.DomainException;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Person;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountDomainService {

    public boolean canUpdateProfile(Account account) {
        return account.getSuspensionDate()!=null && account.getVerificationDate()!=null;
    }


    public void validateAccountCreation(Person person) {
        LocalDate now = LocalDate.now();
        
        if (person.getBirthDate() == null) {
            throw new DomainException(DomainStatusCode.MISSING_BIRTHDATE);
        }
        
        if (person.getBirthDate().isAfter(now.minusYears(GlobalConstants.MINIMAL_CLIENT_AGE))) {
            throw new DomainException(DomainStatusCode.UNDERAGE_PERSON);
        }
    }

    public ArrayList<DomainStatusCode> getPasswordValidation(String password){

        ArrayList<DomainStatusCode> passwordErr=new ArrayList<>();

        if(password.length()<8){
            passwordErr.add(DomainStatusCode.PASSWORD_TOO_SHORT);
        }

        System.out.println(password);
        System.out.println(password.matches("^.*(?=[0-9]).*$"));

        if(!password.matches("^.*(?=[0-9]).*$")){
            passwordErr.add(DomainStatusCode.PASSWORD_MISSING_DIGIT);
        }

        if(!password.matches("^.*(?=[a-z]).*$")){
            passwordErr.add(DomainStatusCode.PASSWORD_MISSING_LOWERCASE);
        }

        if(!password.matches("^.*(?=[A-Z]).*$")){
            passwordErr.add(DomainStatusCode.PASSWORD_MISSING_UPPERCASE);
        }

        if(!password.matches("^.*(?=[@#$%^&+=]).*$")){
            passwordErr.add(DomainStatusCode.PASSWORD_MISSING_SPECIAL_CHAR);
        }

        
        return passwordErr;
    }
}