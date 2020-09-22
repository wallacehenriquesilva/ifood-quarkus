package com.wallace.entities.mappers;


import com.wallace.entities.LocalizacaoEntity;
import com.wallace.resources.models.request.LocalizacaoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface LocalizacaoMapper {

    @Mapping(target = "id", ignore = true)
    LocalizacaoEntity toLocalizacaoEntity(LocalizacaoRequest localizacaoRequest);
}
