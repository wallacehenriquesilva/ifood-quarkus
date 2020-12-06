package com.wallace.entities.mappers;


import com.wallace.entities.RestauranteEntity;
import com.wallace.resources.models.request.RestauranteRequest;
import com.wallace.resources.models.response.RestauranteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {


    //Exemplo de formatação
//    @Mapping(target = "data", dateFormat = "dd/MM/yyyy HH:mm:ss") Formatação automatica de data para String
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "localizacao.id", ignore = true)
    RestauranteEntity toRestauranteEntity(RestauranteRequest restauranteRequest);

    /*
     * Com o mapping target conseguimos fazer o mapper para um objeto atual, assim ele não tem retorno, ele na verdadee
     * atualiza o campo que recebeu.
     * ***Só é possível utilizar um @MappingTarget***
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "localizacao.id", ignore = true)
    void toRestauranteEntity(RestauranteRequest restauranteRequest, @MappingTarget RestauranteEntity restauranteEntity);


    @Mapping(target = "dataCriacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
    RestauranteResponse toRestauranteResponse(RestauranteEntity restauranteEntity);

    List<RestauranteResponse> toRestauranteResponseList(List<RestauranteEntity> restauranteEntity);

}
