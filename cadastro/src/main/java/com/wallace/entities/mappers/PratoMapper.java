package com.wallace.entities.mappers;


import com.wallace.entities.PratoEntity;
import com.wallace.resources.models.request.PratoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurante", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    PratoEntity toPratoEntity(PratoRequest pratoRequest);
}
