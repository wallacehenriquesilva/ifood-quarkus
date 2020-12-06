package com.wallace.resources;


import com.wallace.entities.mappers.RestauranteMapper;
import com.wallace.resources.handlers.models.responses.MultipleErrorResponse;
import com.wallace.resources.models.request.RestauranteRequest;
import com.wallace.resources.models.response.RestauranteResponse;
import com.wallace.services.RestauranteService;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/v1/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme( //Configura a segurança da API
        securitySchemeName = "ifood-oauth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token"))
)
@SecurityRequirement(name = "ifood-oauth", scopes = {}) //Diz que a operação é segura, ai, começa a usar os tokens
@RolesAllowed("proprietario") //Regras/grupos permitidos
@Tag(name = "Restaurante")
public class RestauranteResource {

    @Inject
    RestauranteService restauranteService;
    @Inject
    RestauranteMapper restauranteMapper;

    @GET
    public Response getAll() {
        return Response.ok(restauranteMapper.toRestauranteResponseList(restauranteService.getAll())).build();
    }

    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "400", description = "Restaurante não encontrado.",
            content = @Content(schema = @Schema(allOf = MultipleErrorResponse.class)))
    @APIResponse(responseCode = "200", description = "Restaurante encontrado com sucesso.",
            content = @Content(schema = @Schema(allOf = RestauranteResponse.class)))
    public Response getOne(@PathParam("id") Long id) {
        return Response.ok(restauranteMapper.toRestauranteResponse(restauranteService.getOne(id))).build();
    }


    //    @Transactional -> Usar o @Transactional aqui, vai nos fazer perder performance

    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @Valid @RequestBody RestauranteRequest restauranteRequest) {
        return Response.created(restauranteService.create(uriInfo, restauranteRequest)).build();
    }


    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,
                           @RequestBody RestauranteRequest restauranteRequest) {
        restauranteService.update(id, restauranteRequest);
        return Response.noContent().build();
    }


    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("id") Long id) {
        restauranteService.delete(id);
        return Response.noContent().build();
    }
}
