package com.wallace.resources;


import com.wallace.resources.models.request.PratoRequest;
import com.wallace.services.PratoService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/v1/restaurantes/{idRestaurant}/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {

    //TODO -> TERMINAR O SWAGGER COLOCANDO OS RESPONSES

    private final PratoService pratoService;

    public PratoResource(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @Tag(name = "Prato")     //Para dividir no swagger
    @GET
    public Response getAll(@PathParam("idRestaurant") Long restaurantId) {
        return Response.ok(pratoService.getAll(restaurantId)).build();
    }

    @Tag(name = "Prato")
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
    @Tag(name = "Prato")
    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @PathParam("idRestaurant") Long restaurantId,
            @Valid @RequestBody PratoRequest pratoRequest) {
        return Response.created(pratoService.create(uriInfo, restaurantId, pratoRequest)).build();
    }

    @Tag(name = "Prato")
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("idRestaurant") Long restaurantId,
                           @PathParam("id") Long id,
                           @RequestBody PratoRequest pratoRequest) {
        pratoService.update(id, pratoRequest);
        return Response.noContent().build();
    }

    @Tag(name = "Prato")
    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("idRestaurant") Long restaurantId,
                           @PathParam("id") Long id) {
        pratoService.delete(id);
        return Response.noContent().build();
    }
}
