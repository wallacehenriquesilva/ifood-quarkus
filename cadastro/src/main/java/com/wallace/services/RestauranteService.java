package com.wallace.services;

import com.wallace.entities.RestauranteEntity;
import com.wallace.entities.mappers.LocalizacaoMapper;
import com.wallace.entities.mappers.RestauranteMapper;
import com.wallace.exceptions.RestaurantNotFoundException;
import com.wallace.resources.RestauranteResource;
import com.wallace.resources.models.request.RestauranteRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RestauranteService {

    private final RestauranteMapper restauranteMapper;
    private final LocalizacaoMapper localizacaoMapper;

    public RestauranteService(RestauranteMapper restauranteMapper, LocalizacaoMapper localizacaoMapper) {
        this.restauranteMapper = restauranteMapper;
        this.localizacaoMapper = localizacaoMapper;
    }

    public List<RestauranteEntity> getAll() {
        return RestauranteEntity.listAll();
    }

    public RestauranteEntity getOne(Long id) {
        return RestauranteEntity.findByIdOptional(id)
                .map(restaurant -> (RestauranteEntity) restaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado"));
    }

    @Transactional
    public URI create(UriInfo uriInfo, RestauranteRequest restauranteRequest) {
        final RestauranteEntity restauranteEntity = restauranteMapper.toRestauranteEntity(restauranteRequest);

        restauranteEntity.persist();

        return uriInfo.getAbsolutePathBuilder() //Monta a URI de retorno do create
                .path(RestauranteResource.class, "getOne")
                .build(restauranteEntity.id);
    }


    @Transactional
    public void delete(Long id) {
        Optional.ofNullable(id)
                .map(this::getOne)
                .ifPresent(RestauranteEntity::delete);
    }

    @Transactional
    public void update(Long id, RestauranteRequest restauranteRequest) {
        Optional.ofNullable(id)
                .map(this::getOne)
                .map(restaurant -> {
                    restauranteMapper.toRestauranteEntity(restauranteRequest, restaurant);
                    return restaurant;
                })
                .ifPresent(restaurant -> restaurant.persist());

    }
}
