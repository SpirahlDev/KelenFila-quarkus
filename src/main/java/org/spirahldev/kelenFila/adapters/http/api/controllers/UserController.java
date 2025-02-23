package org.spirahldev.kelenFila.adapters.http.api.controllers;

import java.util.List;
import java.util.Set;
import jakarta.validation.Validator;

import org.apache.camel.Consume;
import org.spirahldev.kelenFila.app.IOmodel.input.ClientRegisterInput;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("user")
@ApplicationScoped
public class UserController {
    @Inject
    Validator validator;


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("client")
    public Response clientRegister(@Valid ClientRegisterInput requestBody){
        

        return Response
            .status(Response.Status.CREATED)
            .entity(new AppResponse<>(AppStatusCode.SUCCESS_OPERATION, requestBody))
            .build();
    }

}
