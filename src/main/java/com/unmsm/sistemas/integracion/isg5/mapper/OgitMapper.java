package com.unmsm.sistemas.integracion.isg5.mapper;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsMonthRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitLoginRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidents;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OgitMapper {
    OgitIncidentsResponse toIncidentsResponse(OgitIncidents ogitIncidents);
    OgitLoginRequest toLoginRequest(OgitIncidentsRequest ogitIncidentsData);
    OgitLoginRequest toLoginRequest(OgitIncidentsMonthRequest ogitIncidentsMonthRequest);
}
