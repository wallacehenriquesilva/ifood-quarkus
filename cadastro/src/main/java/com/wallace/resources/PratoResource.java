package com.wallace.resources;


import com.wallace.resources.models.request.PratoRequest;
import com.wallace.services.PratoService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/v1/restaurantes/{idRestaurant}/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "ifood-oauth", scopes = {}) //Diz que a operação é segura, ai, começa a usar os tokens
@RolesAllowed("proprietario") //Regras/grupos permitidos
@Tag(name = "Prato")     //Para dividir no swagger
public class PratoResource {

    //TODO -> TERMINAR O SWAGGER COLOCANDO OS RESPONSES

    @Inject
    PratoService pratoService;

    @GET
    public Response getAll(@PathParam("idRestaurant") Long restaurantId) {
        return Response.ok(pratoService.getAll(restaurantId)).build();
    }

    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "200", description = "Prato encontrado com sucesso.")
    @APIResponse(responseCode = "400", description = "Prato não encontrado.")
    public Response getOne(
            @PathParam("idRestaurant") Long restaurantId,
            @PathParam("id") Long id) {
        return Response.ok(pratoService.getOne(id)).build();
    }


    //    @Transactional -> Usar o @Transactional aqui, vai nos fazer perder performance
    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @PathParam("idRestaurant") Long restaurantId,
            @Valid @RequestBody PratoRequest pratoRequest) {
        return Response.created(pratoService.create(uriInfo, restaurantId, pratoRequest)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("idRestaurant") Long restaurantId,
                           @PathParam("id") Long id,
                           @RequestBody PratoRequest pratoRequest) {
        pratoService.update(id, pratoRequest);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("idRestaurant") Long restaurantId,
                           @PathParam("id") Long id) {
        pratoService.delete(id);
        return Response.noContent().build();
    }
}
