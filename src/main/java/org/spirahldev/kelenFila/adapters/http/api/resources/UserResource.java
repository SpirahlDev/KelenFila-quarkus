package org.spirahldev.kelenFila.adapters.http.api.resources;

import jakarta.validation.Validator;


import org.spirahldev.kelenFila.app.IOmodel.input.AccountDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.NaturalPersonRegisterInput;
import org.spirahldev.kelenFila.app.IOmodel.input.PersonDataInput;
import org.spirahldev.kelenFila.app.IOmodel.input.UserLoginInput;
import org.spirahldev.kelenFila.app.interfaces.IAccountService;
import org.spirahldev.kelenFila.app.interfaces.IUserService;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;
import org.spirahldev.kelenFila.domain.exceptions.BusinessException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

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

        try {
            PersonDataInput personData = requestBody.getPersonData();
            AccountDataInput accountData = requestBody.getAccountData();
    
            userService.createClient(personData, accountData);
    
            return Response
                .status(Response.Status.CREATED)
                .entity(new AppResponse<>(AppStatusCode.SUCCESS_OPERATION))
                .build();
            
        } catch (BusinessException e) {
            /**
             * If the user already exist, userService throws a BusinessException with the status code USER_ALREADY_EXIST
             * We catch it here and return a 409 CONFLICT response
             */
            if(e.getStatusCode().equals(AppStatusCode.USER_ALREADY_EXIST)){
                return Response.status(Response.Status.CONFLICT)
                    .entity(new AppResponse<>(AppStatusCode.USER_ALREADY_EXIST))
                    .build();
            }

            if(e.getStatusCode().equals(AppStatusCode.INVALID_PASSWORD)){
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(AppResponse.error(AppStatusCode.INVALID_PASSWORD, e.getErrors()))
                    .build();
            }

        }

        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new AppResponse<>(AppStatusCode.INTERNAL_SERVER_ERROR))
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

    


}
