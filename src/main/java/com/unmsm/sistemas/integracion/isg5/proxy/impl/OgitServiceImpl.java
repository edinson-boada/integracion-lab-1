package com.unmsm.sistemas.integracion.isg5.proxy.impl;

import com.unmsm.sistemas.integracion.isg5.mapper.OgitMapper;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitSelSeguridadRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidentes;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitLogin;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitSelSeguridadAPI;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitLoginAPI;
import com.unmsm.sistemas.integracion.isg5.proxy.service.OgitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OgitServiceImpl implements OgitService {

    private final OgitLoginAPI ogitLoginAPI;
    private final OgitSelSeguridadAPI ogitSelSeguridadAPI;
    private final OgitMapper ogitMapper;

    @Override
    public OgitIncidentesResponse getIncidentes(OgitIncidentesRequest ogitIncidentesRequest) {
        try {
            OgitLogin ogitLogin = ogitLoginAPI.login(ogitMapper.toLoginRequest(ogitIncidentesRequest)).execute().body();
            OgitIncidentes ogitIncidentes = ogitSelSeguridadAPI.getSelSeguridad(
                    ogitLogin.getSesion().getToken(),
                    OgitSelSeguridadRequest.builder().fecha(ogitIncidentesRequest.getFecha()).build())
                    .execute()
                    .body();
            return ogitMapper.toIncidentesResponse(ogitIncidentes);
        } catch (IOException ioException) {
            ioException.getMessage();
        }
        return null;
    }
}
