package com.unmsm.sistemas.integracion.isg5.proxy.service;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesRequest;
import org.springframework.stereotype.Service;

@Service
public interface OgitService {
    OgitIncidentesResponse getIncidentes(OgitIncidentesRequest ogitIncidentesRequest);
}
