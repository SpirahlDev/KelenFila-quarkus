package org.spirahldev.kelenFila.app.services;

import java.util.ArrayList;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;

import org.spirahldev.kelenFila.adapters.persistence.repositories.AccountRepositoryImpl;
import org.spirahldev.kelenFila.app.IOmodel.input.AccountDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.UserLoginInput;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.config.AppConfig;
import org.spirahldev.kelenFila.domain.constants.DomainStatusCode;
import org.spirahldev.kelenFila.domain.enums.PersonType;
import org.spirahldev.kelenFila.domain.exceptions.BusinessException;
import org.spirahldev.kelenFila.domain.exceptions.DomainException;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Person;
import org.spirahldev.kelenFila.domain.model.ProfileEntity;
import org.spirahldev.kelenFila.domain.services.AccountDomainService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepositoryImpl accountRepository;

    @Inject
    AccountDomainService accountDomainService;

    @Inject
    AppConfig appConfig;

    public Account createAccount(ProfileEntity profile,Person person,AccountDataInput accountData) {
    
        /**
         * Vérification de la validité de la demande de création de compte
         */
        try {
            accountDomainService.validateAccountCreation(person);
        } catch (DomainException e) {
            if(e.getCode().equals(DomainStatusCode.UNDERAGE_PERSON.getCode())){
                throw new BusinessException(AppStatusCode.VALIDATION_ERROR, "Vous n'avez pas de l'âge requis pour l'ouverture d'un compte.", null, Response.Status.BAD_REQUEST);
            }
        }

        /**
         * Vérification de la validité du mot de passe
         */
        ArrayList<DomainStatusCode> passwordErr=accountDomainService.getPasswordValidation(accountData.password());
        
        if(passwordErr.size()>0){ // On verifie si on a au moins un manquement dans le mot de passe
            String[] errorList=new String[passwordErr.size()];
            for(int i=0;i<passwordErr.size();i++){
                errorList[i]=passwordErr.get(i).getMessage();
            }
            throw new BusinessException(AppStatusCode.INVALID_PASSWORD, null, null)
                .setErrors(errorList);
        }

        Account account = new Account();
        account.setLogin(accountData.login());
        account.setPassword(encryptPassword(accountData.password()));
        account.setPerson(person);
        account.setPersonType(PersonType.NATURAL);
        account.setProfile(profile); 

        return accountRepository.createAccount(account);
    }

    public String encryptPassword(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BcryptUtil.matches(password, hashedPassword);
    }

    
    public String authenticate(UserLoginInput loginData){
        Account account=getAccountFromLogin(loginData.login());
        
        if (!checkPassword(loginData.password(),account.getPassword()) || account==null) {
            throw new BusinessException(AppStatusCode.INVALID_CREDENTIALS, Response.Status.NOT_FOUND);
        }


        return Jwt.issuer(appConfig.getIssuer())
                .subject(account.getLogin())
                .groups(account.getProfile().getProfileCode()) // Ajoute les rôles de l'utilisateur
                .expiresAt(System.currentTimeMillis() / 1000 + 3600) // Expiration 1h
                .sign();
    }
      
    public Account getAccountFromLogin(String login){
        return accountRepository.findAccountByLogin(login);
    }
}
