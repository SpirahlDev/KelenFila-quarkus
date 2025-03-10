package org.spirahldev.kelenFila.adapters.http.api.resources;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spirahldev.kelenFila.app.IOmodel.input.AuctionInput;
import org.spirahldev.kelenFila.app.IOmodel.output.AuctionDataOutput;
import org.spirahldev.kelenFila.app.interfaces.IAccountService;
import org.spirahldev.kelenFila.app.interfaces.IAuctionService;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.common.helpers.AppResponse;
import org.spirahldev.kelenFila.common.helpers.PaginationInfo;
import org.spirahldev.kelenFila.common.helpers.QueryParamsHandler;
import org.spirahldev.kelenFila.domain.interfaces.repositories.IAuctionRepository;
import org.spirahldev.kelenFila.domain.model.Account;
import org.spirahldev.kelenFila.domain.model.Auction;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;

@Path("auction")
@ApplicationScoped
public class AuctionResource {

    /**
     * @AuctionService
    */
    @Inject
    IAuctionService auctionService;

    @Inject
    IAccountService accountService;

    @Inject
    IAuctionRepository auctionRepository; /**@AuctionRepositoryImpl */


    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    @RolesAllowed("AUCTIONNEER")
    public Response createAuction(AuctionInput auctionInput,@Context SecurityContext securityContext){

        Long accountId=Long.valueOf(securityContext.getUserPrincipal().getName());
        Account account=accountService.getAccountFromId(accountId);

        Auction auction=auctionService.createAuction(auctionInput,account);

        AuctionDataOutput auctionOutput=AuctionDataOutput.fromEntity(auction);
        
        return Response.ok()
            .entity(new AppResponse<AuctionDataOutput>(AppStatusCode.SUCCESS_OPERATION,auctionOutput))
            .build();
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response getAuctions(@Context UriInfo uriInfo) {
        Set<String> allowedFilters = Set.of("auctionCode","date");

        Set<String> allowedSearchFields = Set.of("auctionCode","title","description");

        Set<String> allowedSortFields = Set.of("createdAt");
        
        // Créer le handler avec la classe d'entité
        QueryParamsHandler<Auction> handler = new QueryParamsHandler<>(
            auctionRepository.findAll(), 
            uriInfo, 
            allowedFilters,
            allowedSortFields,
            allowedSearchFields,
            Auction.class
        ).withRepository(auctionRepository)
        .handle();
        
        List<Auction> auctionList = handler.paginate();
        PaginationInfo paginationInfo = handler.getPaginationInfo();
        
        return Response.ok(Map.of(
            "data", auctionList,
            "pagination", paginationInfo.toMap()
        )).build();
    }
}
