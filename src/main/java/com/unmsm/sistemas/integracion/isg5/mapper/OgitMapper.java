package com.unmsm.sistemas.integracion.isg5.mapper;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesMesRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitLoginRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidentes;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OgitMapper {

    OgitIncidentesResponse toIncidentesResponse(OgitIncidentes ogitIncidentes);
    OgitLoginRequest toLoginRequest(OgitIncidentesRequest ogitIncidentesData);
    OgitLoginRequest toLoginRequest(OgitIncidentesMesRequest ogitIncidentesMesRequest);
}
