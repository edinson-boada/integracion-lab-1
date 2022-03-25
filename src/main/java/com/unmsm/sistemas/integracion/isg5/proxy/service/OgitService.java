package com.unmsm.sistemas.integracion.isg5.proxy.service;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesMesRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OgitService {
    OgitIncidentesResponse getIncidentes(OgitIncidentesRequest ogitIncidentesRequest);
    List<OgitIncidentesResponse> getIncidentesMes(OgitIncidentesMesRequest ogitIncidentesMesRequest);
}
