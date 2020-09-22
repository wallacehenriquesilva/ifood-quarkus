package com.wallace.services;

import com.wallace.entities.PratoEntity;
import com.wallace.entities.RestauranteEntity;
import com.wallace.entities.mappers.PratoMapper;
import com.wallace.exceptions.RestaurantNotFoundException;
import com.wallace.resources.PratoResource;
import com.wallace.resources.models.request.PratoRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PratoService {

    private final PratoMapper pratoMapper;
    private final RestauranteService restauranteService;

    public PratoService(PratoMapper pratoMapper, RestauranteService restauranteService) {
        this.pratoMapper = pratoMapper;
        this.restauranteService = restauranteService;
    }

    public List<PratoEntity> getAll(Long restaurantId) {
        final RestauranteEntity restauranteEntity = restauranteService.getOne(restaurantId);

        return PratoEntity.stream("restaurante", restauranteEntity)
                .map(prato -> (PratoEntity) prato)
                .collect(Collectors.toList());
    }

    public PratoEntity getOne(Long pratoId) {
        return PratoEntity.findByIdOptional(pratoId)
                .map(prato -> (PratoEntity) prato)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado"));
    }

    @Transactional
    public URI create(UriInfo uriInfo, Long restaurantId, PratoRequest pratoRequest) {
        final RestauranteEntity restauranteEntity = restauranteService.getOne(restaurantId);

        final PratoEntity pratoEntity = pratoMapper.toPratoEntity(pratoRequest);

        pratoEntity.restaurante = restauranteEntity;

        pratoEntity.persist();

        return uriInfo.getAbsolutePathBuilder()
                .path(PratoResource.class, "getOne")
                .build(pratoEntity.id);
    }


    @Transactional
    public void delete(Long id) {
        Optional.ofNullable(id)
                .map(this::getOne)
                .ifPresent(PratoEntity::delete);
    }

    @Transactional
    public void update(Long id, PratoRequest pratoRequest) {
        Optional.ofNullable(id)
                .map(this::getOne)
                .map(prato -> {
                    prato.nome = pratoRequest.getNome();
                    prato.preco = pratoRequest.getPreco();
                    prato.descricao = pratoRequest.getDescricao();

                    return prato;
                })
                .ifPresent(prato -> prato.persist());

    }
}
