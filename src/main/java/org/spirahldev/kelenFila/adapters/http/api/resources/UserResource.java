package org.spirahldev.kelenFila.adapters.http.api.resources;

import jakarta.validation.Validator;


import org.spirahldev.kelenFila.app.IOmodel.input.AccountDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.NaturalPersonRegisterInput;
import org.spirahldev.kelenFila.app.IOmodel.input.PersonDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.UserLoginInput;
import org.spirahldev.kelenFila.app.IOmodel.output.GetOneDataOutput;
import org.spirahldev.kelenFila.app.interfaces.IAccountService;
import org.spirahldev.kelenFila.app.interfaces.IUserService;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;
import org.spirahldev.kelenFila.domain.exceptions.BusinessException;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.CountryEntity;

import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("user")
@ApplicationScoped
public class UserResource {
    @Inject
    Validator validator;

    @Inject
    IUserService userService;

    @Inject
    IAccountService accountService;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("client")
    public Response clientRegister(@Valid NaturalPersonRegisterInput requestBody){
        /**
         * Pour cet endpoint j'ai fais un test : Directement gérer l'exception ici et retourner la réponse http convenable,
         * au lieu de laisser le BusinessExceptionMapper le faire.
         * @param requestBody
         * @return
         */

        PersonDataInput personData = requestBody.getPersonData();
        AccountDataInput accountData = requestBody.getAccountData();

        userService.createClient(personData, accountData);

        return Response
            .status(Response.Status.CREATED)
            .entity(new AppResponse<>(AppStatusCode.SUCCESS_OPERATION))
            .build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("auctioneer")
    public Response autctioneerRegister(@Valid NaturalPersonRegisterInput requestBody){
        PersonDataInput personData = requestBody.getPersonData();
        AccountDataInput accountData = requestBody.getAccountData();

        userService.createAuctionneer(personData, accountData);

        return Response
        .status(Response.Status.CREATED)
        .entity(new AppResponse<>(AppStatusCode.SUCCESS_OPERATION))
        .build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("login")
    public Response login(@Valid UserLoginInput loginRequest){
        return Response.ok().
        entity(AppResponse.sendTokken(accountService.authenticate(loginRequest)))
        .build();
    }

    @GET
    @Produces("application/json")
    @Path("me")
    @Authenticated
    public Response getOne(@Context SecurityContext securityContext){

        Long accountId=Long.valueOf(securityContext.getUserPrincipal().getName());
        Account account=accountService.getAccountFromId(accountId);
        
        return Response.ok().
        entity(new AppResponse<>(
            AppStatusCode.SUCCESS_OPERATION,
            GetOneDataOutput.fromAccount(account))
        ).build();
    }

    @GET
    @Produces("application/json")
    @Path("test")
    @Authenticated
    public Response test(){
        CountryEntity country=CountryEntity.findById(1);

        if(country==null){
            throw new BusinessException(AppStatusCode.VALIDATION_ERROR,Response.Status.BAD_REQUEST);
        }
        return Response.ok().
        entity(new AppResponse<>(AppStatusCode.SUCCESS_OPERATION,country))
        .build();
    }

    


}
