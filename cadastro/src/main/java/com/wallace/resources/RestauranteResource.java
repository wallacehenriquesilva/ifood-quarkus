package com.wallace.resources;


import com.wallace.resources.models.request.RestauranteRequest;
import com.wallace.services.RestauranteService;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
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

@Path("/v1/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@RolesAllowed("proprietario") //Regras/grupos permitidos
//@SecurityScheme( //Configura a segurança da API
//        securitySchemeName = "ifood-oauth",
//        type = SecuritySchemeType.OAUTH2,
//        flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token"))
//)
//@SecurityRequirement(name = "ifood-oauth") //Diz que a operação é segura, ai, começa a usar os tokens
public class RestauranteResource {

    private final RestauranteService restauranteService;

    public RestauranteResource(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Tag(name = "Restaurante")
    @GET
    public Response getAll() {
        return Response.ok(restauranteService.getAll()).build();
    }

    @Tag(name = "Restaurante")
    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "200", description = "Restaurante encontrado com sucesso.")
    @APIResponse(responseCode = "400", description = "Restaurante não encontrado.")
//    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    public Response getOne(@PathParam("id") Long id) {
        return Response.ok(restauranteService.getOne(id)).build();
    }


    //    @Transactional -> Usar o @Transactional aqui, vai nos fazer perder performance
    @Tag(name = "Restaurante")
    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @Valid @RequestBody RestauranteRequest restauranteRequest) {
        return Response.created(restauranteService.create(uriInfo, restauranteRequest)).build();
    }

    @Tag(name = "Restaurante")
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,
                           @RequestBody RestauranteRequest restauranteRequest) {
        restauranteService.update(id, restauranteRequest);
        return Response.noContent().build();
    }

    @Tag(name = "Restaurante")
    @DELETE
    @Path("/{id}")
    public Response update(@PathParam("id") Long id) {
        restauranteService.delete(id);
        return Response.noContent().build();
    }
}
